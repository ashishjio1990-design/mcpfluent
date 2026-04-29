# Firebase Smoke Test

Upload an APK to Firebase App Distribution and trigger the smoke test workflow on GitHub Actions.

## Usage
```
/firebase-smoke [apk-path]
```

If `apk-path` is not provided, ask the user for it.

## What to do

1. **Resolve the APK path**
   - If the user passed an argument, use it as the APK path.
   - Otherwise ask: "Please provide the path to the APK file."
   - Confirm the file exists before proceeding.

2. **Credentials for this project:**
   - SA JSON file: `C:/Users/ashish.taralkar/Downloads/hello-c48a8-firebase-adminsdk-fbsvc-ac9ca5ca5d.json`
   - Firebase App ID: `1:657196305078:android:c5ecdb4254635ad07e7417`
   - GitHub Repo: `ashishjio1990-design/mcpfluent`
   - GitHub Token: read from env var `GITHUB_TOKEN` or ask the user

3. **Upload to Firebase** — run this in Bash:
   ```bash
   SA_FILE=$(mktemp /tmp/sa_XXXXXX.json)
   cat "C:/Users/ashish.taralkar/Downloads/hello-c48a8-firebase-adminsdk-fbsvc-ac9ca5ca5d.json" > "$SA_FILE"
   GOOGLE_APPLICATION_CREDENTIALS="$SA_FILE" firebase appdistribution:distribute \
     "<APK_PATH>" \
     --app "1:657196305078:android:c5ecdb4254635ad07e7417" \
     --release-notes "smoke test build"
   rm -f "$SA_FILE"
   ```
   Report the uploaded version and build number from the output.

4. **Trigger GitHub Action** — run this in Bash (replace TOKEN with the value from step 2):
   ```bash
   HTTP_STATUS=$(curl -s -o /tmp/gh_response.json -w "%{http_code}" \
     -X POST \
     -H "Authorization: token $GITHUB_TOKEN" \
     -H "Accept: application/vnd.github.v3+json" \
     "https://api.github.com/repos/ashishjio1990-design/mcpfluent/dispatches" \
     -d '{"event_type":"firebase-build"}')
   echo "HTTP Status: $HTTP_STATUS"
   ```
   If HTTP status is 204, confirm success. Otherwise show the error response.

5. **Report back** with:
   - Firebase release version and build number
   - GitHub Actions URL: `https://github.com/ashishjio1990-design/mcpfluent/actions`
