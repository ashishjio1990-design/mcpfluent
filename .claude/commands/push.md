# Push to Git

Stage relevant code changes and push to the main branch.

## Usage
```
/push [message]
```

If `message` is not provided, generate a commit message from the staged changes.

## What to do

1. Run `git status` to see what has changed.

2. Stage only source code files — never stage:
   - `.claude/settings.json`
   - `.vscode/settings.json`
   - `allure-results/`
   - `*.png`, `ui_dump.xml`
   - Deletion of `apps/fluenthealth.apk` unless the user explicitly asks

3. Files to stage when changed:
   - `src/test/java/**/*.java`
   - `scripts/*.sh`, `scripts/*.py`
   - `.github/workflows/*.yml`
   - `pom.xml`
   - `src/test/resources/*.properties`
   - `apps/hello-v1.apk` (via LFS)

4. Commit with the provided message, or generate a concise one describing the changes.
   Always append:
   ```
   Co-Authored-By: Claude Sonnet 4.6 <noreply@anthropic.com>
   ```

5. Push to `origin main`.

6. Report the commit hash and confirm the push succeeded.
