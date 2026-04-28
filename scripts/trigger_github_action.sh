#!/bin/bash
# Trigger the Firebase smoke-test workflow in GitHub Actions.
# Call this script from your build pipeline after uploading the APK to Firebase.
#
# Required env vars:
#   GITHUB_TOKEN  – personal access token with repo scope
#
# Usage:
#   GITHUB_TOKEN=ghp_xxx bash scripts/trigger_github_action.sh

set -e

REPO="ashishjio1990-design/mcpfluent"
EVENT_TYPE="firebase-build"

if [ -z "${GITHUB_TOKEN:-}" ]; then
  echo "ERROR: GITHUB_TOKEN is not set" >&2
  exit 1
fi

echo "Triggering firebase-smoke workflow on $REPO ..."

HTTP_STATUS=$(curl -s -o /tmp/gh_response.json -w "%{http_code}" \
  -X POST \
  -H "Authorization: token $GITHUB_TOKEN" \
  -H "Accept: application/vnd.github.v3+json" \
  "https://api.github.com/repos/$REPO/dispatches" \
  -d "{\"event_type\":\"$EVENT_TYPE\"}")

if [ "$HTTP_STATUS" -eq 204 ]; then
  echo "Workflow triggered successfully."
else
  echo "ERROR: GitHub API returned HTTP $HTTP_STATUS" >&2
  cat /tmp/gh_response.json >&2
  exit 1
fi
