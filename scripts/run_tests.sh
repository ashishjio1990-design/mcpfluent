#!/bin/bash
set -e

TAG=${TEST_TAG:-regression}
CLASSES_ARG=""
if [ -n "${TEST_CLASSES:-}" ]; then
  CLASSES_ARG="-Dtest=$TEST_CLASSES"
fi

# If a Firebase APK was downloaded, use it for both Fluent Health and Hello app tests.
# Otherwise fall back to the checked-in APK paths.
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

EVIDENCE_DIR="$GITHUB_WORKSPACE/evidence"
mkdir -p "$EVIDENCE_DIR"

# Record directly on device — avoids pipe/timing loss that causes slideshow effect
adb shell mkdir -p /sdcard/evidence
echo "Starting screen recording on device..."
adb shell screenrecord --bit-rate=4000000 /sdcard/evidence/recording.mp4 &
RECORD_PID=$!

mvn test \
  -Dgroups="$TAG" \
  $CLASSES_ARG \
  -DdeviceName=emulator-5554 \
  -DappiumUrl=http://127.0.0.1:4723 \
  -Dplatform=android \
  $APP_ARG \
  --no-transfer-progress
TEST_EXIT=$?

echo "Stopping screen recording..."
kill $RECORD_PID 2>/dev/null || true
adb shell pkill -SIGINT screenrecord 2>/dev/null || true
sleep 3  # Allow device to finalize the MP4

echo "Pulling recording from device..."
adb pull /sdcard/evidence/recording.mp4 "$EVIDENCE_DIR/smoke-recording.mp4" \
  && echo "Recording saved: $EVIDENCE_DIR/smoke-recording.mp4" \
  || echo "Warning: could not pull recording"

exit $TEST_EXIT
