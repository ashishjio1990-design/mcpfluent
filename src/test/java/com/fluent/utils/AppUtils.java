package com.fluent.utils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * HOW TO USE AppUtils in your tests and page classes
 * ====================================================
 *
 * 1. RESET APP — use in @BeforeEach to start every test from a clean slate:
 *
 *    @BeforeEach
 *    void setUp() {
 *        AppUtils.resetApp();   // clears cache/data, relaunches the app fresh
 *    }
 *
 * 2. SCROLL DOWN TO ELEMENT — use in a page class before tapping an element
 *    that is below the current screen (e.g. "Delete Account" at the bottom of Settings):
 *
 *    public void tapDeleteAccount() {
 *        AppUtils.scrollDownToElement("com.fluenthealth.app:id/tv_delete_account_and_all_personal_data");
 *        tap(deleteAccountButton);
 *    }
 *
 *    Or directly in a test:
 *
 *    AppUtils.scrollDownToElement("com.fluenthealth.app:id/text_view_profile_settings_logout");
 *    pages.getSettingPage().tapLogOut();
 *
 * 3. SCROLL UP TO ELEMENT — use when you've scrolled down and need to go back up
 *    to an element that is now above the screen (e.g. Account section header):
 *
 *    public void tapAccountSettingsHeader() {
 *        AppUtils.scrollUpToElement("com.fluenthealth.app:id/ll_profile_settings_account_header");
 *        tap(accountSectionHeader);
 *    }
 *
 *    Or directly in a test:
 *
 *    AppUtils.scrollUpToElement("com.fluenthealth.app:id/tv_account_settings");
 *
 * NOTE: Pass the full resource-id string exactly as it appears in @AndroidFindBy(id = "...").
 */
public class AppUtils {

    private static final Logger log = LoggerFactory.getLogger(AppUtils.class);
    private static final int MAX_SCROLLS = 10;

    private AppUtils() {}

    /**
     * Clears all app data and cache (equivalent to "Clear Data" in Android settings),
     * then relaunches the app so the next test starts from a completely fresh state.
     */
    public static void resetApp() {
        AppiumDriver driver = DriverManager.getDriver();
        String appPackage = ConfigLoader.get("android.app.package", "");
        log.info("Resetting app — clearing data for package: {}", appPackage);

        Map<String, Object> args = new HashMap<>();
        args.put("appId", appPackage);
        driver.executeScript("mobile: clearApp", args);
        ((AndroidDriver) driver).activateApp(appPackage);

        log.info("App reset complete — relaunched fresh");
    }

    /**
     * Scrolls DOWN the screen until the element with the given resource ID becomes visible.
     *
     * @param resourceId full resource-id of the target element (e.g. "com.example.app:id/button")
     */
    public static void scrollDownToElement(String resourceId) {
        AppiumDriver driver = DriverManager.getDriver();
        log.info("Scrolling down to element: {}", resourceId);
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))" +
                ".scrollIntoView(new UiSelector().resourceId(\"" + resourceId + "\"))"));
    }

    /**
     * Scrolls UP the screen until the element with the given resource ID becomes visible.
     * Performs repeated upward swipes (finger moves down) up to {@value MAX_SCROLLS} times.
     *
     * @param resourceId full resource-id of the target element (e.g. "com.example.app:id/button")
     */
    public static void scrollUpToElement(String resourceId) {
        AppiumDriver driver = DriverManager.getDriver();
        log.info("Scrolling up to element: {}", resourceId);

        Dimension size = driver.manage().window().getSize();
        int centerX = size.width / 2;
        int swipeStartY = (int) (size.height * 0.3);
        int swipeEndY   = (int) (size.height * 0.7);

        for (int attempt = 1; attempt <= MAX_SCROLLS; attempt++) {
            try {
                if (driver.findElement(By.id(resourceId)).isDisplayed()) {
                    log.info("Element found after {} upward swipe(s)", attempt - 1);
                    return;
                }
            } catch (Exception ignored) {}

            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipeUp = new Sequence(finger, 0);
            swipeUp.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerX, swipeStartY));
            swipeUp.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipeUp.addAction(finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(), centerX, swipeEndY));
            swipeUp.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            driver.perform(List.of(swipeUp));
        }

        log.warn("Element '{}' not found after {} upward swipes", resourceId, MAX_SCROLLS);
    }
}
