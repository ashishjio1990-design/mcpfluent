# Trigger Smoke Tests

Trigger the Firebase smoke test workflow on GitHub Actions without uploading a new APK.
This runs smoke tests against the latest APK already on Firebase.

## Usage
```
/trigger-smoke
```

## What to do

1. **Get GitHub Token** — read from env var `GITHUB_TOKEN`, or ask the user if not set.

2. **Trigger GitHub Action** — run this in Bash:
   ```bash
   HTTP_STATUS=$(curl -s -o /tmp/gh_response.json -w "%{http_code}" \
     -X POST \
     -H "Authorization: token $GITHUB_TOKEN" \
     -H "Accept: application/vnd.github.v3+json" \
     "https://api.github.com/repos/ashishjio1990-design/mcpfluent/dispatches" \
     -d '{"event_type":"firebase-build"}')
   echo "HTTP Status: $HTTP_STATUS"
   cat /tmp/gh_response.json
   ```

3. **Report back**:
   - If HTTP 204: workflow triggered successfully
   - Otherwise: show the error
   - Always print the Actions URL: `https://github.com/ashishjio1990-design/mcpfluent/actions`
