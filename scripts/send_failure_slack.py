#!/usr/bin/env python3
"""Send one Slack failure message per failed test case, with steps, recording hint, and build version."""

import json
import os
import sys
import urllib.request
from pathlib import Path

SLACK_WEBHOOK_URL = os.environ.get("SLACK_WEBHOOK_URL", "")
APK_VERSION       = os.environ.get("APK_VERSION", "unknown")
APK_BUILD         = os.environ.get("APK_BUILD", "unknown")
GITHUB_REPO       = os.environ.get("GITHUB_REPOSITORY", "")
GITHUB_RUN_ID     = os.environ.get("GITHUB_RUN_ID", "")
RUN_NUMBER        = os.environ.get("GITHUB_RUN_NUMBER", "")

workspace   = os.environ.get("GITHUB_WORKSPACE", ".")
failure_dir = Path(workspace) / "evidence" / "failures"

if not SLACK_WEBHOOK_URL:
    print("SLACK_WEBHOOK_URL not set — skipping Slack failure notifications.")
    sys.exit(0)

if not failure_dir.exists():
    print("No failures directory — skipping Slack failure notifications.")
    sys.exit(0)

failures = sorted(failure_dir.glob("*.json"))
if not failures:
    print("No failure reports found — all tests passed.")
    sys.exit(0)

run_url = f"https://github.com/{GITHUB_REPO}/actions/runs/{GITHUB_RUN_ID}"


def send_slack(payload):
    data = json.dumps(payload).encode("utf-8")
    req = urllib.request.Request(
        SLACK_WEBHOOK_URL,
        data=data,
        headers={"Content-Type": "application/json"},
        method="POST",
    )
    with urllib.request.urlopen(req, timeout=15) as resp:
        return resp.read()


print(f"Found {len(failures)} failure(s). Sending Slack notifications...")

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

    fluent_frames = [
        line.strip() for line in stack_trace.splitlines()
        if "com.fluent" in line
    ]
    steps_text = "\n".join(fluent_frames[:10]) if fluent_frames else stack_trace[:500]

    payload = {
        "attachments": [
            {
                "color": "#cc0000",
                "blocks": [
                    {
                        "type": "header",
                        "text": {"type": "plain_text", "text": f"❌ Test Failed: {display_name}"},
                    },
                    {
                        "type": "section",
                        "fields": [
                            {"type": "mrkdwn", "text": f"*Class:*\n{class_name}"},
                            {"type": "mrkdwn", "text": f"*Build:*\nv{APK_VERSION} (build {APK_BUILD})"},
                            {"type": "mrkdwn", "text": f"*Run:*\n#{RUN_NUMBER}"},
                            {"type": "mrkdwn", "text": f"*Exception:*\n{exc_type}"},
                        ],
                    },
                    {
                        "type": "section",
                        "text": {
                            "type": "mrkdwn",
                            "text": f"*Error Message:*\n```{message[:300]}```",
                        },
                    },
                    {
                        "type": "section",
                        "text": {
                            "type": "mrkdwn",
                            "text": f"*Failed Steps (com.fluent frames):*\n```{steps_text[:800]}```",
                        },
                    },
                    {
                        "type": "section",
                        "text": {
                            "type": "mrkdwn",
                            "text": f"▶️ *Screen Recording:* Download the *evidence* artifact and open `{safe_name}.mp4`",
                        },
                    },
                    {
                        "type": "actions",
                        "elements": [
                            {
                                "type": "button",
                                "text": {"type": "plain_text", "text": f"View Run · #{RUN_NUMBER}"},
                                "url": run_url,
                                "style": "primary",
                            }
                        ],
                    },
                ],
            }
        ]
    }

    try:
        send_slack(payload)
        print(f"  ✓ Slack notification sent: {display_name}")
    except Exception as e:
        print(f"  ✗ Failed to send Slack notification for '{display_name}': {e}")
