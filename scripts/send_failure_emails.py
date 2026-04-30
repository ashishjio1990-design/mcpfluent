#!/usr/bin/env python3
"""Send one failure email per failed test case, with steps, recording link, and build version."""

import json
import os
import smtplib
import ssl
import sys
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
from pathlib import Path

MAIL_SERVER   = os.environ.get("MAIL_SERVER", "")
MAIL_PORT     = int(os.environ.get("MAIL_PORT", "587"))
MAIL_USERNAME = os.environ.get("MAIL_USERNAME", "")
MAIL_PASSWORD = os.environ.get("MAIL_PASSWORD", "")
MAIL_TO       = os.environ.get("MAIL_TO", "")
APK_VERSION   = os.environ.get("APK_VERSION", "unknown")
APK_BUILD     = os.environ.get("APK_BUILD", "unknown")
GITHUB_REPO   = os.environ.get("GITHUB_REPOSITORY", "")
GITHUB_RUN_ID = os.environ.get("GITHUB_RUN_ID", "")
RUN_NUMBER    = os.environ.get("GITHUB_RUN_NUMBER", "")

workspace    = os.environ.get("GITHUB_WORKSPACE", ".")
failure_dir  = Path(workspace) / "evidence" / "failures"

if not failure_dir.exists():
    print("No failures directory — skipping failure emails.")
    sys.exit(0)

failures = sorted(failure_dir.glob("*.json"))
if not failures:
    print("No failure reports found — all tests passed.")
    sys.exit(0)

run_url = f"https://github.com/{GITHUB_REPO}/actions/runs/{GITHUB_RUN_ID}"

def send_email(subject, html_body):
    msg = MIMEMultipart("alternative")
    msg["Subject"] = subject
    msg["From"]    = f"Fluent CI <{MAIL_USERNAME}>"
    msg["To"]      = MAIL_TO
    msg.attach(MIMEText(html_body, "html"))
    recipients = [r.strip() for r in MAIL_TO.split(",")]

    if MAIL_PORT == 465:
        context = ssl.create_default_context()
        with smtplib.SMTP_SSL(MAIL_SERVER, MAIL_PORT, context=context) as server:
            server.login(MAIL_USERNAME, MAIL_PASSWORD)
            server.sendmail(MAIL_USERNAME, recipients, msg.as_string())
    else:
        with smtplib.SMTP(MAIL_SERVER, MAIL_PORT) as server:
            server.starttls()
            server.login(MAIL_USERNAME, MAIL_PASSWORD)
            server.sendmail(MAIL_USERNAME, recipients, msg.as_string())

def html_escape(text):
    return (text or "").replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")

print(f"Found {len(failures)} failure(s). Sending emails...")

for f in failures:
    try:
        data = json.loads(f.read_text(encoding="utf-8"))
    except Exception as e:
        print(f"  Could not parse {f.name}: {e}")
        continue

    display_name = data.get("displayName", data.get("testName", "Unknown"))
    class_name   = data.get("className", "")
    exc_type     = data.get("exceptionType", "Unknown")
    message      = data.get("message", "No message")
    stack_trace  = data.get("stackTrace", "")
    safe_name    = data.get("testName", "recording")

    # Extract only com.fluent frames for "failed steps" section
    fluent_frames = [
        line for line in stack_trace.splitlines()
        if "com.fluent" in line
    ]
    failed_steps_html = "\n".join(html_escape(line) for line in fluent_frames) if fluent_frames else html_escape(stack_trace[:2000])

    subject = (
        f"❌ [TEST FAILED] {display_name} | "
        f"v{APK_VERSION} (build {APK_BUILD}) | Run #{RUN_NUMBER}"
    )

    html = f"""<!DOCTYPE html>
<html>
<body style="font-family:Arial,sans-serif;max-width:860px;margin:0 auto;color:#333">

  <div style="background:#cc0000;color:white;padding:16px 20px;border-radius:6px 6px 0 0">
    <h2 style="margin:0">❌ Test Case Failed</h2>
  </div>

  <div style="border:1px solid #ddd;border-top:none;padding:20px;border-radius:0 0 6px 6px">

    <table style="border-collapse:collapse;width:100%;margin-bottom:20px">
      <tr style="background:#f9f9f9">
        <td style="padding:8px 12px;font-weight:bold;width:160px;border:1px solid #eee">Test Case</td>
        <td style="padding:8px 12px;border:1px solid #eee">{html_escape(display_name)}</td>
      </tr>
      <tr>
        <td style="padding:8px 12px;font-weight:bold;border:1px solid #eee">Class</td>
        <td style="padding:8px 12px;border:1px solid #eee">{html_escape(class_name)}</td>
      </tr>
      <tr style="background:#f9f9f9">
        <td style="padding:8px 12px;font-weight:bold;border:1px solid #eee">Build Version</td>
        <td style="padding:8px 12px;border:1px solid #eee">v{html_escape(APK_VERSION)} (build {html_escape(APK_BUILD)})</td>
      </tr>
      <tr>
        <td style="padding:8px 12px;font-weight:bold;border:1px solid #eee">Run</td>
        <td style="padding:8px 12px;border:1px solid #eee">#{html_escape(RUN_NUMBER)}</td>
      </tr>
      <tr style="background:#f9f9f9">
        <td style="padding:8px 12px;font-weight:bold;border:1px solid #eee">Exception</td>
        <td style="padding:8px 12px;color:#cc0000;border:1px solid #eee">{html_escape(exc_type)}</td>
      </tr>
      <tr>
        <td style="padding:8px 12px;font-weight:bold;border:1px solid #eee">Error Message</td>
        <td style="padding:8px 12px;border:1px solid #eee">{html_escape(message)}</td>
      </tr>
    </table>

    <h3 style="color:#333;border-bottom:2px solid #eee;padding-bottom:6px">📋 Failed Steps</h3>
    <pre style="background:#1e1e1e;color:#d4d4d4;padding:14px;border-radius:4px;overflow-x:auto;font-size:12px;line-height:1.5">{failed_steps_html}</pre>

    <h3 style="color:#333;border-bottom:2px solid #eee;padding-bottom:6px">🎥 Screen Recording</h3>
    <p>Download the <strong>evidence</strong> artifact from the Actions run and open:</p>
    <code style="background:#f5f5f5;padding:4px 8px;border-radius:3px">{html_escape(safe_name)}.mp4</code>

    <br><br>
    <a href="{run_url}"
       style="display:inline-block;background:#0366d6;color:white;padding:10px 20px;
              border-radius:4px;text-decoration:none;font-weight:bold">
      🔗 View Run #{html_escape(RUN_NUMBER)} on GitHub Actions
    </a>

  </div>
</body>
</html>"""

    try:
        send_email(subject, html)
        print(f"  ✓ Email sent: {display_name}")
    except Exception as e:
        print(f"  ✗ Failed to send email for '{display_name}': {e}")
