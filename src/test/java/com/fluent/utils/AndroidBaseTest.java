package com.fluent.utils;

import com.fluent.pages.PageManager;
import com.fluent.testdata.TestData;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.appmanagement.ApplicationState;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.screenrecording.CanRecordScreen;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Map;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(FailureReporter.class)
public abstract class AndroidBaseTest extends BaseTest {

    private String currentTestName;

    protected void signIn(PageManager pages) {
        // Layer 3: if a previous test left the app signed-in, force a clean reset
        if (pages.fluentHomePage().isDisplayed()) {
            log.warn("Home screen visible at start of signIn() — previous reset failed; forcing restart");
            clearAppDataAndRestart();
            dismissUpdatePopupIfPresent();
        }
        if (pages.settingPage().isPinBackButtonDisplayed()) {
            log.info("PIN back button detected before welcome screen — tapping to dismiss");
            pages.settingPage().tapPinBackButton();
        }
        assertTrue(pages.createAccountPage().isDisplayed(), "Welcome screen should be displayed");
        pages.createAccountPage().tapLogIn();

        assertTrue(pages.loginPage().isDisplayed(), "Sign In screen should be displayed");
        assertFalse(pages.loginPage().isContinueButtonEnabled(),
                "Continue button should be disabled before entering phone number");

        pages.loginPage().enterPhoneNumber(TestData.SIGN_IN_MOBILE);

        assertTrue(pages.loginPage().isContinueButtonEnabled(),
                "Continue button should be enabled after entering phone number");
        pages.loginPage().tapContinue();

        assertTrue(pages.enterPinPage().isDisplayed(), "Enter PIN screen should be displayed");
        pages.enterPinPage().enterPin(TestData.PIN);

        assertTrue(pages.fluentHomePage().isDisplayed(), "Home page should be displayed after sign-in");
    }

    protected void navigateToSurgeries(PageManager pages) {
        pages.fluentHomePage().tapFabAndSelectAddHealthInfo();
        pages.surgeriesAndProceduresPage().scrollToSurgeriesSection();
        pages.surgeriesAndProceduresPage().tapSurgeriesAndProcedures();
    }

    protected void fillSurgeryMandatoryFields(PageManager pages, String surgeryName, String status) {
        pages.surgeriesAndProceduresPage().tapSurgerySearchField();
        pages.surgeriesAndProceduresPage().selectSurgeryFromCommonList(surgeryName);
        pages.surgeriesAndProceduresPage().tapStatusField();
        pages.surgeriesAndProceduresPage().tapDateField();
        pages.surgeriesAndProceduresPage().tapDateField();
        pages.surgeriesAndProceduresPage().confirmDatePicker();
    }

    protected void signOut(PageManager pages) {
        pages.fluentHomePage().tapHome();
        pages.fluentHomePage().tapHomeSection1();
        pages.settingPage().tapLogOut();
        pages.settingPage().tapPinBackButton();
        String appPackage = ConfigLoader.get("android.app.package", "com.fluenthealth.app");
        AndroidDriver androidDriver = (AndroidDriver) getDriver();
        try {
            androidDriver.executeScript("mobile: clearApp", Map.of("appId", appPackage));
            log.info("App data cleared for {}", appPackage);
        } catch (Exception e) {
            log.warn("Could not clear app data: {}", e.getMessage());
        }
    }

    protected void dismissUpdatePopupIfPresent() {
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

    protected void clearAppDataAndRestart() {
        String appPackage = ConfigLoader.get("android.app.package", "com.fluenthealth.app");
        AndroidDriver androidDriver = (AndroidDriver) getDriver();
        // mobile: clearApp is a UiAutomator2 built-in; does not require adb_shell security flag
        try {
            androidDriver.executeScript("mobile: clearApp", Map.of("appId", appPackage));
            log.info("App data cleared for {}", appPackage);
        } catch (Exception e) {
            log.warn("Could not clear app data: {}", e.getMessage());
        }
        // activateApp() relaunches without needing adb_shell / am start
        try {
            androidDriver.activateApp(appPackage);
            new WebDriverWait(androidDriver, Duration.ofSeconds(15))
                    .until(d -> androidDriver.queryAppState(appPackage) == ApplicationState.RUNNING_IN_FOREGROUND);
            log.info("App launched fresh for {}", appPackage);
        } catch (Exception e) {
            throw new IllegalStateException(
                    "App failed to reach foreground after reset — aborting test setup: " + e.getMessage(), e);
        }
    }

    @BeforeEach
    public void startRecording(TestInfo testInfo) {
        clearAppDataAndRestart();
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
        // Layer 2: terminate app after every test so the next @BeforeEach starts with a dead process
        String appPackage = ConfigLoader.get("android.app.package", "com.fluenthealth.app");
        try {
            ((AndroidDriver) getDriver()).terminateApp(appPackage);
            log.info("App terminated after test — clean state for next @BeforeEach");
        } catch (Exception e) {
            log.warn("Could not terminate app after test: {}", e.getMessage());
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
