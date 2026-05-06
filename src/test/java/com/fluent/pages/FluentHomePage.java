package com.fluent.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class FluentHomePage extends BasePage {

    // Home content sections (Jetpack Compose views)
    @AndroidFindBy(xpath = "//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View[1]")
    private WebElement setting;

    @AndroidFindBy(xpath = "//android.widget.FrameLayout[@content-desc=\"Home\"]")
    private WebElement homeNav;

    @AndroidFindBy(xpath = "//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View[2]")
    private WebElement notificationicon;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Explore content for you\"]")
    private WebElement exploreContentSection;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Power up your records\"]")
    private WebElement powerUpRecordsSection;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Update My Health\"]")
    private WebElement updateMyHealthSection;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Diet & Nutrition\"]")
    private WebElement dietNutritionSection;

    // Bottom navigation tabs
    @AndroidFindBy(id = "com.fluenthealth.app:id/home_fragment")
    private WebElement homeTab;

    @AndroidFindBy(id = "com.fluenthealth.app:id/my_profile_fragment")
    private WebElement myHealthTab;

    @AndroidFindBy(id = "com.fluenthealth.app:id/records_fragment")
    private WebElement recordsTab;

    @AndroidFindBy(id = "com.fluenthealth.app:id/library_fragment")
    private WebElement contentTab;

    // FAB
    @AndroidFindBy(id = "com.fluenthealth.app:id/fab_menu")
    private WebElement fabMenu;

    @AndroidFindBy(id = "com.fluenthealth.app:id/fabContainer")
    private WebElement fabContainer;

    public boolean isDisplayed() {
        return isVisible(homeTab);
    }

    public boolean isMyHealthTabDisplayed() {
        return isVisible(myHealthTab);
    }

    public boolean isRecordsTabDisplayed() {
        return isVisible(recordsTab);
    }

    public boolean isContentTabDisplayed() {
        return isVisible(contentTab);
    }

    public boolean isFabMenuDisplayed() {
        return isVisible(fabMenu);
    }

    public SettingPage tapHomeSection1() {
        log.info("Tapping Settings");
        tap(setting);
        return new SettingPage();
    }

    public FluentHomePage tapHomeSection2() {
        log.info("Tapping home section 2");
        tap(notificationicon);
        return this;
    }

    public FluentHomePage tapExploreContent() {
        log.info("Tapping Explore content for you");
        tap(exploreContentSection);
        return this;
    }

    public FluentHomePage tapPowerUpRecords() {
        log.info("Tapping Power up your records");
        tap(powerUpRecordsSection);
        return this;
    }

    public FluentHomePage tapUpdateMyHealth() {
        log.info("Tapping Update My Health");
        tap(updateMyHealthSection);
        return this;
    }

    public FluentHomePage tapDietNutrition() {
        log.info("Tapping Diet & Nutrition");
        tap(dietNutritionSection);
        return this;
    }

    public FluentHomePage tapHomeTab() {
        log.info("Tapping Home tab");
        tap(homeTab);
        return this;
    }

    public FluentHomePage tapHome() {
        log.info("Tapping Home nav (content-desc)");
        tap(homeNav);
        return this;
    }

    public void tapMyHealthTab() {
        log.info("Tapping My Health tab");
        tap(myHealthTab);
    }

    public void tapRecordsTab() {
        log.info("Tapping Records tab");
        tap(recordsTab);
    }

    public void tapContentTab() {
        log.info("Tapping Content tab");
        tap(contentTab);
    }

    public FluentHomePage tapFabMenu() {
        log.info("Tapping FAB menu");
        tap(fabMenu);
        return this;
    }

    public FluentHomePage tapFabContainer() {
        log.info("Tapping FAB container");
        tap(fabContainer);
        return this;
    }

    public void tapFabAndSelectAddHealthInfo() {
        log.info("Tapping FAB and selecting Add health info");
        tap(fabMenu);
        driver.findElement(io.appium.java_client.AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"Add health info\")")).click();
    }
}
