package com.fluent.utils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.screenrecording.CanRecordScreen;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.List;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Base64;

@ExtendWith(FailureReporter.class)
public abstract class AndroidBaseTest extends BaseTest {

    private String currentTestName;

    private void dismissUpdatePopupIfPresent() {
        try {
            List<WebElement> closeButtons = getDriver().findElements(AppiumBy.id("com.fluenthealth.app:id/iv_close"));
            if (!closeButtons.isEmpty() && closeButtons.get(0).isDisplayed()) {
                closeButtons.get(0).click();
                log.info("Update popup dismissed");
            }
        } catch (Exception e) {
            log.warn("Could not check for update popup: {}", e.getMessage());
        }
    }

    @BeforeEach
    public void startRecording(TestInfo testInfo) {
        dismissUpdatePopupIfPresent();
        currentTestName = testInfo.getDisplayName().replaceAll("[^a-zA-Z0-9_-]", "_");
        try {
            ((CanRecordScreen) getDriver()).startRecordingScreen(
                new AndroidStartScreenRecordingOptions()
                    .withTimeLimit(Duration.ofMinutes(10))
                    .withBitRate(4000000)
            );
            log.info("Recording started: {}", currentTestName);
        } catch (Exception e) {
            log.warn("Could not start recording: {}", e.getMessage());
        }
    }

    @AfterEach
    public void saveRecording() {
        try {
            String base64 = ((CanRecordScreen) getDriver()).stopRecordingScreen();
            String workspace = System.getenv("GITHUB_WORKSPACE");
            Path evidenceDir = workspace != null
                ? Paths.get(workspace, "evidence")
                : Paths.get("evidence");
            Files.createDirectories(evidenceDir);
            Path file = evidenceDir.resolve(currentTestName + ".mp4");
            Files.write(file, Base64.getDecoder().decode(base64));
            log.info("Recording saved: {}", file);
        } catch (Exception e) {
            log.warn("Could not save recording: {}", e.getMessage());
        }
    }

    @Override
    protected DesiredCapabilities buildCapabilities() {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        String deviceName = System.getProperty("deviceName", "").trim();
        if (deviceName.isEmpty()) deviceName = ConfigLoader.get("android.device.name", "emulator-5554");
        options.setDeviceName(deviceName);
        options.setAutomationName("UiAutomator2");

        String appPath = System.getProperty("app", "").trim();
        if (appPath.isEmpty()) appPath = ConfigLoader.get("android.app.path", "");
        if (!appPath.isEmpty()) options.setApp(appPath);

        String appPackage = ConfigLoader.get("android.app.package", "");
        String appActivity = ConfigLoader.get("android.app.activity", "");
        if (!appPackage.isEmpty()) options.setAppPackage(appPackage);
        if (!appActivity.isEmpty()) options.setAppActivity(appActivity);

        options.setNoReset(Boolean.parseBoolean(ConfigLoader.get("no.reset", "false")));
        options.setAutoGrantPermissions(true);
        return new DesiredCapabilities(options.asMap());
    }
}
