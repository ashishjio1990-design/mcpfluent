#!/bin/bash
set -e

TAG=${TEST_TAG:-regression}
CLASSES_ARG=""
if [ -n "${TEST_CLASSES:-}" ]; then
  CLASSES_ARG="-Dtest=$TEST_CLASSES"
fi

if [ -n "${FIREBASE_APK:-}" ]; then
  APP_ARG="-Dapp=$FIREBASE_APK -DhelloApp=$FIREBASE_APK"
else
  APP_ARG="-Dapp=$GITHUB_WORKSPACE/apps/fluenthealth.apk"
fi

adb wait-for-device
adb devices

echo "=========================================="
if [ -n "${APK_VERSION:-}" ]; then
  echo "  Installing APK version : $APK_VERSION (build $APK_BUILD)"
  echo "  APK path               : ${FIREBASE_APK:-$GITHUB_WORKSPACE/apps/fluenthealth.apk}"
else
  echo "  Installing checked-in APK (no Firebase version)"
fi
echo "=========================================="

appium --address 127.0.0.1 --port 4723 --log-level info > "$GITHUB_WORKSPACE/appium.log" 2>&1 &

echo "Waiting for Appium to start..."
for i in 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15; do
  if curl -s http://127.0.0.1:4723/status > /dev/null 2>&1; then
    echo "Appium ready after attempt $i"
    break
  fi
  echo "Attempt $i/15: not ready, waiting 3s..."
  sleep 3
done

mkdir -p "$GITHUB_WORKSPACE/evidence"

MVN_BASE="-DdeviceName=emulator-5554 -DappiumUrl=http://127.0.0.1:4723 -Dplatform=android $APP_ARG --no-transfer-progress"

# ── Phase 1: run all tests ────────────────────────────────────────────────────
echo "=========================================="
echo "  Phase 1: running full test suite"
echo "=========================================="
set +e
mvn test -Dgroups="$TAG" $CLASSES_ARG $MVN_BASE
PHASE1_EXIT=$?
set -e

# ── Phase 2: rerun failed tests once ─────────────────────────────────────────
FAILED=$(python3 scripts/get_failed_tests.py 2>/dev/null || echo "")

if [ -n "$FAILED" ]; then
  echo "=========================================="
  echo "  Phase 2: rerunning failed classes"
  echo "  $FAILED"
  echo "=========================================="

  # Remove phase-1 Allure results for these classes so they are not double-counted
  python3 scripts/clean_allure_retries.py "$FAILED"

  set +e
  mvn test -Dtest="$FAILED" $MVN_BASE
  TEST_EXIT=$?
  set -e
else
  echo "No failures in phase 1 — skipping rerun."
  TEST_EXIT=$PHASE1_EXIT
fi

exit $TEST_EXIT
