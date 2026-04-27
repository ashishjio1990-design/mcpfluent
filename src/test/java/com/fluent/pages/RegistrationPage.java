package com.fluent.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class RegistrationPage extends BasePage {

    @AndroidFindBy(id = "com.fluenthealth.app:id/onboardingBackIv")
    private WebElement backButton;

    @AndroidFindBy(id = "com.fluenthealth.app:id/nestedScrollView")
    private WebElement scrollView;

    @AndroidFindBy(id = "com.fluenthealth.app:id/firstNameET")
    private WebElement firstNameInput;

    @AndroidFindBy(id = "com.fluenthealth.app:id/lastNameET")
    private WebElement lastNameInput;

    @AndroidFindBy(id = "com.fluenthealth.app:id/emailET")
    private WebElement emailInput;

    @AndroidFindBy(id = "com.fluenthealth.app:id/contactNumberET")
    private WebElement mobileNumberInput;

    @AndroidFindBy(id = "com.fluenthealth.app:id/ll_whatsapp_consent")
    private WebElement whatsappConsent;

    @AndroidFindBy(id = "com.fluenthealth.app:id/termsTV")
    private WebElement termsText;

    @AndroidFindBy(id = "com.fluenthealth.app:id/button")
    private WebElement continueButton;

    public boolean isDisplayed() {
        return isVisible(firstNameInput);
    }

    public boolean isContinueButtonEnabled() {
        return continueButton.isEnabled();
    }

    public RegistrationPage enterFirstName(String firstName) {
        log.info("Entering first name: {}", firstName);
        clearAndType(firstNameInput, firstName);
        return this;
    }

    public RegistrationPage enterLastName(String lastName) {
        log.info("Entering last name: {}", lastName);
        clearAndType(lastNameInput, lastName);
        return this;
    }

    public RegistrationPage enterEmail(String email) {
        log.info("Entering email: {}", email);
        clearAndType(emailInput, email);
        return this;
    }

    public RegistrationPage enterMobileNumber(String mobile) {
        log.info("Entering mobile number: {}", mobile);
        clearAndType(mobileNumberInput, mobile);
        return this;
    }

    public RegistrationPage tapWhatsappConsent() {
        log.info("Tapping WhatsApp consent");
        tap(whatsappConsent);
        return this;
    }

    public RegistrationPage scrollToContinueButton() {
        log.info("Scrolling to Continue button");
        scrollToElementById("com.fluenthealth.app:id/button");
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
