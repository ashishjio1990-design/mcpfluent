package com.fluent.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

    // Toolbar
    @AndroidFindBy(id = "com.fluenthealth.app:id/closeIV")
    private WebElement closeButton;

    // Header content
    @AndroidFindBy(id = "com.fluenthealth.app:id/headerTV")
    private WebElement headerText;

    @AndroidFindBy(id = "com.fluenthealth.app:id/subHeaderTV")
    private WebElement subHeaderText;

    // Phone number section
    @AndroidFindBy(id = "com.fluenthealth.app:id/countryContainer")
    private WebElement countryCode;

    @AndroidFindBy(id = "com.fluenthealth.app:id/phoneET")
    private WebElement phoneInput;

    @AndroidFindBy(id = "com.fluenthealth.app:id/phoneErrorTV")
    private WebElement phoneErrorText;

    // Consent
    @AndroidFindBy(id = "com.fluenthealth.app:id/consentTV")
    private WebElement consentText;

    // Buttons
    @AndroidFindBy(id = "com.fluenthealth.app:id/button")
    private WebElement continueButton;

    @AndroidFindBy(id = "com.fluenthealth.app:id/createAccountBtn")
    private WebElement createAccountButton;

    public boolean isDisplayed() {
        return isVisible(phoneInput);
    }

    public String getHeaderText() {
        return getText(headerText);
    }

    public String getSubHeaderText() {
        return getText(subHeaderText);
    }

    public String getCountryCode() {
        return getText(countryCode);
    }

    public LoginPage enterPhoneNumber(String phone) {
        log.info("Entering phone number: {}", phone);
        clearAndType(phoneInput, phone);
        return this;
    }

    public boolean isContinueButtonEnabled() {
        return continueButton.isEnabled();
    }

    public void tapContinue() {
        log.info("Tapping Continue button");
        tap(continueButton);
    }

    public void tapClose() {
        log.info("Tapping Close button");
        tap(closeButton);
    }

    public CreateAccountPage tapCreateAccount() {
        log.info("Tapping Create account");
        tap(createAccountButton);
        return new CreateAccountPage();
    }

    public String getPhoneErrorText() {
        return getText(phoneErrorText);
    }

    public boolean isPhoneErrorDisplayed() {
        return isVisible(phoneErrorText);
    }
}
