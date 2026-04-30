# Fluent Health Mobile Test Automation Framework
## Feature Documentation

---

## 1. Technology Stack

| Component | Technology | Version |
|---|---|---|
| Mobile Automation | Appium Java Client | 9.3.0 |
| Test Framework | JUnit 5 | 5.11.0 |
| WebDriver | Selenium | 4.25.0 |
| Build Tool | Maven | 3.x |
| Reporting | Allure | 2.29.1 |
| Logging | SLF4J + Logback | 2.0.16 / 1.5.8 |
| Language | Java | 11 |
| CI/CD | GitHub Actions | — |
| APK Distribution | Firebase App Distribution | — |

---

## 2. Design Patterns

### Page Object Model (POM)
- Every screen in the app has a dedicated page class under `src/test/java/com/fluent/pages/`
- All page classes extend `BasePage`, which provides shared actions: `tap()`, `clearAndType()`, `getText()`, `isVisible()`
- Elements are declared using Appium `@FindBy` annotations and initialized via Page Factory

### Page Manager
- `PageManager` centralizes all page instances in one place
- Tests access pages via `pages.loginPage()`, `pages.fluentHomePage()`, etc. — no manual instantiation in tests

### Base Test Hierarchy
```
BaseTest  (JUnit lifecycle, driver setup/teardown)
    └── AndroidBaseTest  (Android capabilities, screen recording, failure reporting)
            └── SignInTest, SignUpTest, ...
```

### Thread-Safe Driver Management
- `DriverManager` uses `ThreadLocal<AppiumDriver>` so each parallel test thread has its own isolated driver session

---

## 3. Configuration Management

- Properties loaded from `src/test/resources/config.properties`
- System properties override file values at runtime (e.g. `-Dapp=/path/to.apk`)
- Key configuration values:

| Property | Description |
|---|---|
| `appium.url` | Appium server URL |
| `android.device.name` | Device/emulator name |
| `android.app.package` | App package name |
| `android.app.activity` | Launch activity |
| `implicit.wait` | Implicit wait timeout (seconds) |
| `explicit.wait` | Explicit wait timeout (seconds) |
| `no.reset` | Skip app reset between tests |

---

## 4. Wait Strategy

`WaitUtils` provides multiple wait types, all configurable via `explicit.wait`:

| Method | Condition |
|---|---|
| `waitForVisible()` | Element visible on screen |
| `waitForClickable()` | Element visible and enabled |
| `waitForInvisibility()` | Element disappears |
| `waitForPresence()` | Element exists in DOM |
| `isDisplayed()` | Fluent wait, 5s timeout, 500ms polling |

---

## 5. Screen Recording (Per Test Case)

Every test case is automatically recorded end-to-end.

- **Start**: Recording begins in `@BeforeEach` via Appium's `startRecordingScreen()` API — after the driver is ready
- **Stop**: Recording stops in `@AfterEach` — before the driver quits
- **Max duration**: 10 minutes per test
- **Bitrate**: 4 Mbps
- **Output**: `evidence/<TestName>.mp4` (base64 decoded from Appium response)
- **Local runs**: Saved to `evidence/` in the project root
- **CI runs**: Uploaded as `evidence` artifact in GitHub Actions (14-day retention)

---

## 6. Failure Reporting

### Per-Test JSON Report
`FailureReporter` is a JUnit 5 `TestWatcher` registered via `@ExtendWith` on `AndroidBaseTest`. On every test failure it writes:

```
evidence/failures/<TestName>.json
```

Contents:
- Test display name and class name
- Exception type and error message
- Full stack trace (truncated at 4,000 chars)

### Per-Test Failure Email
`scripts/send_failure_emails.py` runs after tests in CI and sends **one email per failed test** containing:

| Section | Content |
|---|---|
| Test Case | JUnit display name |
| Class | Test class name |
| Build Version | APK version + build number from Firebase |
| Run | GitHub Actions run number |
| Exception | Exception class |
| Error Message | Assertion or exception message |
| Failed Steps | `com.fluent.*` stack frames only |
| Recording | Filename + button link to GitHub Actions run |

---

## 7. Test Tagging & Organisation

Tests are tagged with JUnit 5 `@Tag` to control which suite runs:

| Tag | Purpose |
|---|---|
| `smoke` | Fast critical-path tests, run on every Firebase build |
| `regression` | Full suite, run on every push to main |

Allure annotations enrich the report:
- `@Feature` — groups tests by app feature
- `@Story` — human-readable scenario name
- `@Description` — detailed test description
- `@Severity` — CRITICAL / NORMAL / MINOR

---

## 8. Parallel Execution

- JUnit 5 parallel engine enabled via `junit-platform.properties`
- **Classes** run concurrently — each class gets its own emulator/session
- **Methods within a class** run sequentially — safe for Appium (one session per class)
- Thread count configurable: `-Dparallel.count=N` (default: 2)

---

## 9. CI/CD Pipelines

### Workflow 1 — Regression Tests (`regression.yml`)
- **Trigger**: Push to `main`, pull request to `main`, manual dispatch
- **APK**: Checked-in APK from repository
- **Test tag**: `smoke`
- **Emulator**: Android API 29, Pixel 4 profile, x86_64

### Workflow 2 — Firebase Smoke Tests (`firebase-smoke.yml`)
- **Trigger**: `repository_dispatch` (type: `firebase-build`) or manual dispatch
- **APK**: Downloaded fresh from Firebase App Distribution on every run
- **Extracts**: APK version and build number for use in reports and emails
- **Test tag**: `smoke`

### Workflow 3 — Parallel Regression (`parallel-regression.yml`)
- **Trigger**: Manual dispatch with inputs
- **Inputs**: Test tag, number of parallel emulators (1–4)
- **Architecture**:
  1. `prepare` job — discovers tests and splits them into shards via `split_tests.py`
  2. `test` job (matrix) — each shard runs on a dedicated emulator in parallel
  3. `report` job — merges all shard results, generates a combined Allure report and sends email
- **Fail-fast disabled** — all shards complete even if some fail

### Common Steps (all workflows)
1. Checkout code (with Git LFS)
2. Java 17 + Maven dependency cache
3. Node 20 + Appium server + UiAutomator2 driver
4. KVM enablement for hardware acceleration
5. Android emulator runner
6. Allure HTML report generation
7. PDF report via headless Chrome
8. Summary email with PDF attachment
9. Per-test failure emails
10. Artifact uploads: Allure report, evidence (recordings), Appium log, Surefire reports

---

## 10. Reporting

### Allure Report
- Generated from JSON results in `target/allure-results/`
- Includes test steps, screenshots, timings, tags, severity, and stories
- Uploaded as artifact `allure-report` (14-day retention)

### PDF Report
`scripts/generate_pdf_report.py` produces:
- **SVG donut chart** — pass/fail/skip proportions
- **Summary table** — per-test name, status, duration, and badge
- **GitHub metadata** — run number, branch, commit SHA, timestamp
- **Email-ready HTML** — responsive layout with stats boxes and proportional bar
- Exported to `allure-report.pdf` via headless Chrome and attached to the summary email

### Summary Email
Sent via `dawidd6/action-send-mail` with:
- Subject includes status (PASSED/FAILED), APK version, build number, run number, and pass/fail counts
- HTML body from PDF report generator
- PDF report attached

---

## 11. Firebase App Distribution Integration

`scripts/download_firebase_apk.py`:
- Authenticates using a service account JSON (from GitHub Secret `FIREBASE_SERVICE_ACCOUNT_JSON`)
- Fetches the latest release from Firebase App Distribution API
- Streams the APK download in 64 KB chunks
- Exports `APK_VERSION` and `APK_BUILD` to `GITHUB_ENV` for use across all subsequent CI steps

---

## 12. Logging

- **Console**: thread name + log level + message
- **File**: `target/logs/test-{date}.log` with daily rollover, 7-day retention
- All utilities, page classes, and test base classes log via SLF4J `LoggerFactory`

---

## 13. App Utilities

`AppUtils` provides reusable app-level helpers:

| Method | Description |
|---|---|
| `resetApp()` | Clears app data and relaunches via `mobile: clearApp` |
| `scrollDownToElement()` | UiAutomator2 scroll to element by resource ID |
| `scrollUpToElement()` | Manual swipe-based upward scroll (up to 10 swipes) |

---

## 14. Security

- All credentials stored as GitHub Secrets — never hardcoded
- Secrets used: `FIREBASE_SERVICE_ACCOUNT_JSON`, `FIREBASE_APP_ID`, `MAIL_SERVER`, `MAIL_PORT`, `MAIL_USERNAME`, `MAIL_PASSWORD`, `MAIL_TO`

---

## 15. Evidence Folder Structure (per CI run)

```
evidence/
  <TestName1>.mp4          ← screen recording for test 1
  <TestName2>.mp4          ← screen recording for test 2
  failures/
    <TestName1>.json       ← failure details (only for failed tests)
```
