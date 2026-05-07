#!/usr/bin/env python3
"""Send a Slack summary notification after a test run, with pass/fail/skip counts and a run link."""

import json
import os
import sys
import urllib.request

SLACK_BOT_TOKEN = os.environ.get("SLACK_BOT_TOKEN", "")
SLACK_CHANNEL   = os.environ.get("SLACK_CHANNEL", "")
PASSED          = int(os.environ.get("REPORT_PASSED",  "0"))
FAILED          = int(os.environ.get("REPORT_FAILED",  "0"))
SKIPPED         = int(os.environ.get("REPORT_SKIPPED", "0"))
TOTAL           = int(os.environ.get("REPORT_TOTAL",   "0"))
APK_VERSION     = os.environ.get("APK_VERSION", "")
APK_BUILD       = os.environ.get("APK_BUILD",   "")
GITHUB_REPO     = os.environ.get("GITHUB_REPOSITORY", "")
GITHUB_RUN_ID   = os.environ.get("GITHUB_RUN_ID", "")
RUN_NUMBER      = os.environ.get("GITHUB_RUN_NUMBER", "")
BRANCH          = os.environ.get("GITHUB_REF_NAME", "")
SHA             = (os.environ.get("GITHUB_SHA", "") or "")[:7]
WORKFLOW_NAME   = os.environ.get("GITHUB_WORKFLOW", "Regression")

if not SLACK_BOT_TOKEN or not SLACK_CHANNEL:
    print("SLACK_BOT_TOKEN or SLACK_CHANNEL not set — skipping Slack summary notification.")
    sys.exit(0)

run_url      = f"https://github.com/{GITHUB_REPO}/actions/runs/{GITHUB_RUN_ID}"
overall_pass = FAILED == 0 and TOTAL > 0
color        = "#97cc64" if overall_pass else "#fd5a3e"
status_emoji = "✅" if overall_pass else "❌"
status_text  = "PASSED" if overall_pass else "FAILED"

version_text = f"v{APK_VERSION} (build {APK_BUILD})" if APK_VERSION else ""
meta_parts   = [f"Branch: `{BRANCH}`", f"Commit: `{SHA}`"]
if version_text:
    meta_parts.insert(0, f"App: *{version_text}*")
meta_line = "  ·  ".join(meta_parts)

payload = {
    "channel": SLACK_CHANNEL,
    "attachments": [
        {
            "color": color,
            "blocks": [
                {
                    "type": "header",
                    "text": {
                        "type": "plain_text",
                        "text": f"{status_emoji} {WORKFLOW_NAME} · Run #{RUN_NUMBER} · {status_text}",
                    },
                },
                {
                    "type": "section",
                    "text": {"type": "mrkdwn", "text": meta_line},
                },
                {
                    "type": "section",
                    "fields": [
                        {"type": "mrkdwn", "text": f"*✅ Passed:*\n{PASSED}"},
                        {"type": "mrkdwn", "text": f"*❌ Failed:*\n{FAILED}"},
                        {"type": "mrkdwn", "text": f"*⏭ Skipped:*\n{SKIPPED}"},
                        {"type": "mrkdwn", "text": f"*Total:*\n{TOTAL}"},
                    ],
                },
                {
                    "type": "section",
                    "text": {
                        "type": "mrkdwn",
                        "text": "📄 *Report:* Download the *allure-report* artifact from the Actions run for detailed results.",
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
    ],
}

try:
    data = json.dumps(payload).encode("utf-8")
    req = urllib.request.Request(
        "https://slack.com/api/chat.postMessage",
        data=data,
        headers={
            "Content-Type": "application/json",
            "Authorization": f"Bearer {SLACK_BOT_TOKEN}",
        },
        method="POST",
    )
    with urllib.request.urlopen(req, timeout=15) as resp:
        body = json.loads(resp.read())
        if not body.get("ok"):
            raise RuntimeError(f"Slack API error: {body.get('error')}")
    print(f"Slack summary sent: {PASSED} passed, {FAILED} failed, {SKIPPED} skipped, {TOTAL} total")
except Exception as e:
    print(f"Failed to send Slack summary: {e}")
    sys.exit(1)
