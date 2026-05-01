package com.fluent.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class EnterPinPage extends BasePage {

    @AndroidFindBy(id = "com.fluenthealth.app:id/pinCode")
    private WebElement pinInput;

    @AndroidFindBy(id = "com.fluenthealth.app:id/button")
    private WebElement continueButton;

    @AndroidFindBy(id = "com.fluenthealth.app:id/createPinBackIv")
    private WebElement backButton;

    @AndroidFindBy(id = "com.fluenthealth.app:id/errorTV")
    private WebElement errorMessage;

    public boolean isDisplayed() {
        return isVisible(pinInput);
    }

    public boolean isContinueButtonEnabled() {
        return continueButton.isEnabled();
    }

    public EnterPinPage enterPin(String pin) {
        log.info("Entering PIN: {}", pin);
        clearAndType(pinInput, pin);
        return this;
    }

    public void tapContinue() {
        log.info("Tapping Continue button");
        tap(continueButton);
    }

    public boolean isErrorDisplayed() {
        return isVisible(errorMessage);
    }

    public String getErrorMessage() {
        log.info("Getting error message text");
        return getText(errorMessage);
    }

    public void tapBack() {
        log.info("Tapping Back button");
        tap(backButton);
    }
}
