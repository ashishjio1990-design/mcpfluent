package com.fluent.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class NewUserWelcomeScreen extends BasePage {

    @AndroidFindBy(id = "com.fluenthealth.app:id/optionExplore")
    private WebElement exploreOption;

    @AndroidFindBy(id = "com.fluenthealth.app:id/optionUpload")
    private WebElement uploadOption;

    public boolean isDisplayed() {
        return isVisible(exploreOption);
    }

    public boolean isExploreOptionDisplayed() {
        return isVisible(exploreOption);
    }

    public boolean isUploadOptionDisplayed() {
        return isVisible(uploadOption);
    }

    public void tapExplore() {
        log.info("Tapping Explore option");
        tap(exploreOption);
    }

    public void tapUpload() {
        log.info("Tapping Upload option");
        tap(uploadOption);
    }
}
