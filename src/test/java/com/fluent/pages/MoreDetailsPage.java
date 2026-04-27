package com.fluent.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class MoreDetailsPage extends BasePage {

    @AndroidFindBy(id = "com.fluenthealth.app:id/nestedScrollView")
    private WebElement scrollView;

    @AndroidFindBy(id = "com.fluenthealth.app:id/tv_dob")
    private WebElement dateOfBirthField;

    @AndroidFindBy(id = "com.fluenthealth.app:id/genderET")
    private WebElement genderField;

    @AndroidFindBy(id = "com.fluenthealth.app:id/sexAssignedAtBirthET")
    private WebElement sexAssignedAtBirthField;

    @AndroidFindBy(id = "com.fluenthealth.app:id/whyWeAskTV")
    private WebElement whyWeAskLink;

    @AndroidFindBy(id = "com.fluenthealth.app:id/button")
    private WebElement finishSetupButton;

    @AndroidFindBy(id = "com.fluenthealth.app:id/img_back")
    private WebElement backButton;

    // Sex assigned at birth dropdown items (visible when DDL is open)
    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Male\"]")
    private WebElement sexOptionMale;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Female\"]")
    private WebElement sexOptionFemale;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Intersex\"]")
    private WebElement sexOptionIntersex;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Prefer not to say\"]")
    private WebElement sexOptionPreferNotToSay;

    // Gender dropdown item (visible when DDL is open)
    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Male\"]")
    private WebElement genderOptionMale;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Female\"]")
    private WebElement genderOptionFemale;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Non-binary\"]")
    private WebElement genderOptionNonBinary;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Prefer not to say\"]")
    private WebElement genderOptionPreferNotToSay;

    // Date picker dialog elements
    @AndroidFindBy(xpath = "//android.widget.EditText[@resource-id=\"android:id/numberpicker_input\" and @text]")
    private WebElement datePickerDayInput;

    @AndroidFindBy(xpath = "(//android.widget.EditText[@resource-id=\"android:id/numberpicker_input\"])[1]")
    private WebElement datePickerDay;

    @AndroidFindBy(xpath = "(//android.widget.EditText[@resource-id=\"android:id/numberpicker_input\"])[2]")
    private WebElement datePickerMonth;

    @AndroidFindBy(xpath = "(//android.widget.EditText[@resource-id=\"android:id/numberpicker_input\"])[3]")
    private WebElement datePickerYear;

    @AndroidFindBy(id = "android:id/button1")
    private WebElement datePickerOkButton;

    @AndroidFindBy(id = "android:id/button2")
    private WebElement datePickerCancelButton;

    public boolean isDisplayed() {
        return isVisible(dateOfBirthField);
    }

    public boolean isFinishSetupButtonDisplayed() {
        return isVisible(finishSetupButton);
    }

    public boolean isBackButtonDisplayed() {
        return isVisible(backButton);
    }

    public String getDateOfBirthText() {
        return getText(dateOfBirthField);
    }

    public String getGenderText() {
        return getText(genderField);
    }

    public String getSexAssignedAtBirthText() {
        return getText(sexAssignedAtBirthField);
    }

    public MoreDetailsPage tapDateOfBirth() {
        log.info("Tapping Date of Birth field");
        tap(dateOfBirthField);
        return this;
    }

    public MoreDetailsPage tapGender() {
        log.info("Tapping Gender field");
        tap(genderField);
        return this;
    }

    public MoreDetailsPage tapSexAssignedAtBirth() {
        log.info("Tapping Sex Assigned at Birth field");
        tap(sexAssignedAtBirthField);
        return this;
    }

    public MoreDetailsPage tapWhyWeAsk() {
        log.info("Tapping Why we ask link");
        tap(whyWeAskLink);
        return this;
    }

    public void tapFinishSetup() {
        log.info("Tapping Finish Setup button");
        tap(finishSetupButton);
    }

    public void tapBack() {
        log.info("Tapping Back button");
        tap(backButton);
    }

    public MoreDetailsPage selectSexMale() {
        log.info("Selecting sex assigned at birth: Male");
        tap(sexOptionMale);
        return this;
    }

    public MoreDetailsPage selectSexFemale() {
        log.info("Selecting sex assigned at birth: Female");
        tap(sexOptionFemale);
        return this;
    }

    public MoreDetailsPage selectSexIntersex() {
        log.info("Selecting sex assigned at birth: Intersex");
        tap(sexOptionIntersex);
        return this;
    }

    public MoreDetailsPage selectSexPreferNotToSay() {
        log.info("Selecting sex assigned at birth: Prefer not to say");
        tap(sexOptionPreferNotToSay);
        return this;
    }

    public MoreDetailsPage selectGenderMale() {
        log.info("Selecting gender: Male");
        tap(genderOptionMale);
        return this;
    }

    public MoreDetailsPage selectGenderFemale() {
        log.info("Selecting gender: Female");
        tap(genderOptionFemale);
        return this;
    }

    public MoreDetailsPage selectGenderNonBinary() {
        log.info("Selecting gender: Non-binary");
        tap(genderOptionNonBinary);
        return this;
    }

    public MoreDetailsPage selectGenderPreferNotToSay() {
        log.info("Selecting gender: Prefer not to say");
        tap(genderOptionPreferNotToSay);
        return this;
    }

    public MoreDetailsPage setDateOfBirth(String day, String month, String year) {
        log.info("Setting date of birth to {}/{}/{}", day, month, year);
        clearAndType(datePickerDay, day);
        clearAndType(datePickerMonth, month);
        clearAndType(datePickerYear, year);
        return this;
    }

    public MoreDetailsPage tapDatePickerOk() {
        log.info("Tapping OK on date picker");
        tap(datePickerOkButton);
        return this;
    }

    public MoreDetailsPage tapDatePickerCancel() {
        log.info("Tapping Cancel on date picker");
        tap(datePickerCancelButton);
        return this;
    }

    public boolean isDatePickerOkButtonDisplayed() {
        return isVisible(datePickerOkButton);
    }
}
