#!/bin/bash
# Upload an APK to Firebase App Distribution and trigger the smoke test workflow.
#
# Usage:
#   bash scripts/upload_and_trigger.sh --apk /path/to/app.apk [--notes "release notes"]
#
# Required env vars:
#   FIREBASE_APP_ID               – e.g. 1:123456789:android:abcdef
#   FIREBASE_SERVICE_ACCOUNT_JSON – full service account JSON as a string
#   GITHUB_TOKEN                  – GitHub PAT with repo scope

set -e

# ── Parse arguments ────────────────────────────────────────────────────────────
APK_PATH=""
RELEASE_NOTES="Manual smoke test build"

while [[ $# -gt 0 ]]; do
  case "$1" in
    --apk)    APK_PATH="$2";        shift 2 ;;
    --notes)  RELEASE_NOTES="$2";   shift 2 ;;
    *) echo "Unknown argument: $1" >&2; exit 1 ;;
  esac
done

# ── Validate inputs ────────────────────────────────────────────────────────────
if [ -z "$APK_PATH" ]; then
  echo "ERROR: --apk <path> is required" >&2
  echo "Usage: bash scripts/upload_and_trigger.sh --apk /path/to/app.apk" >&2
  exit 1
fi

if [ ! -f "$APK_PATH" ]; then
  echo "ERROR: APK not found at: $APK_PATH" >&2
  exit 1
fi

if [ -z "${FIREBASE_APP_ID:-}" ] || [ -z "${FIREBASE_SERVICE_ACCOUNT_JSON:-}" ]; then
  echo "ERROR: FIREBASE_APP_ID and FIREBASE_SERVICE_ACCOUNT_JSON must be set" >&2
  exit 1
fi

if [ -z "${GITHUB_TOKEN:-}" ]; then
  echo "ERROR: GITHUB_TOKEN must be set" >&2
  exit 1
fi

# ── Check Firebase CLI ─────────────────────────────────────────────────────────
if ! command -v firebase &> /dev/null; then
  echo "Firebase CLI not found. Installing..."
  npm install -g firebase-tools
fi

# ── Upload APK to Firebase App Distribution ────────────────────────────────────
echo "Uploading $(basename "$APK_PATH") to Firebase App Distribution..."

SA_FILE=$(mktemp /tmp/sa_XXXXXX.json)
echo "$FIREBASE_SERVICE_ACCOUNT_JSON" > "$SA_FILE"

GOOGLE_APPLICATION_CREDENTIALS="$SA_FILE" firebase appdistribution:distribute "$APK_PATH" \
  --app "$FIREBASE_APP_ID" \
  --release-notes "$RELEASE_NOTES"

rm -f "$SA_FILE"
echo "Upload complete."

# ── Trigger GitHub Actions smoke workflow ─────────────────────────────────────
REPO="ashishjio1990-design/mcpfluent"
echo "Triggering firebase-smoke workflow on $REPO ..."

HTTP_STATUS=$(curl -s -o /tmp/gh_response.json -w "%{http_code}" \
  -X POST \
  -H "Authorization: token $GITHUB_TOKEN" \
  -H "Accept: application/vnd.github.v3+json" \
  "https://api.github.com/repos/$REPO/dispatches" \
  -d '{"event_type":"firebase-build"}')

if [ "$HTTP_STATUS" -eq 204 ]; then
  echo "Workflow triggered. Check progress at:"
  echo "  https://github.com/$REPO/actions"
else
  echo "ERROR: GitHub API returned HTTP $HTTP_STATUS" >&2
  cat /tmp/gh_response.json >&2
  exit 1
fi
