package com.fluent.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;


public class CreateFluentPin extends BasePage {

    @AndroidFindBy(id = "com.fluenthealth.app:id/nestedScrollView")
    private WebElement scrollView;

    @AndroidFindBy(id = "com.fluenthealth.app:id/pinCode")
    private WebElement pinCodeInput;

    @AndroidFindBy(id = "com.fluenthealth.app:id/pinConfirmCode")
    private WebElement pinConfirmCodeInput;

    @AndroidFindBy(id = "com.fluenthealth.app:id/button")
    private WebElement continueButton;

    @AndroidFindBy(id = "com.fluenthealth.app:id/createPinBackIv")
    private WebElement backButton;

    public boolean isDisplayed() {
        return isVisible(pinCodeInput);
    }

    public boolean isContinueButtonDisplayed() {
        return isVisible(continueButton);
    }

    public boolean isContinueButtonEnabled() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return continueButton.isEnabled();
    }

    public boolean isBackButtonDisplayed() {
        return isVisible(backButton);
    }

    public CreateFluentPin enterPin(String pin) {
        log.info("Entering PIN");
        clearAndType(pinCodeInput, pin);
        return this;
    }

    public CreateFluentPin enterConfirmPin(String pin) {
        log.info("Entering Confirm PIN");
        scrollToElementById("com.fluenthealth.app:id/pinConfirmCode");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        clearAndType(pinConfirmCodeInput, pin);
        return this;
    }

    public void tapContinue() {
        log.info("Tapping Continue button");
        tap(continueButton);
    }

    public void tapBack() {
        log.info("Tapping Back button");
        tap(backButton);
    }
}
