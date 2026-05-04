package com.fluent.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class BasicInfoPage extends BasePage {

    // ── Basic Info tab ────────────────────────────────────────────────────────
    @AndroidFindBy(xpath = "//*[@text='Basic Info']")
    private WebElement basicInfoTab;

    // ── Profile read-only fields ──────────────────────────────────────────────
    @AndroidFindBy(xpath = "//*[@resource-id='com.fluenthealth.app:id/firstNameBasicInfo']//*[@resource-id='com.fluenthealth.app:id/infoValueTv']")
    private WebElement firstNameValue;

    @AndroidFindBy(xpath = "//*[@resource-id='com.fluenthealth.app:id/lastNameBasicInfo']//*[@resource-id='com.fluenthealth.app:id/infoValueTv']")
    private WebElement lastNameValue;

    @AndroidFindBy(xpath = "//*[@resource-id='com.fluenthealth.app:id/emailBasicInfo']//*[@resource-id='com.fluenthealth.app:id/infoValueTv']")
    private WebElement emailValue;

    @AndroidFindBy(xpath = "//*[@resource-id='com.fluenthealth.app:id/phoneBasicInfo']//*[@resource-id='com.fluenthealth.app:id/infoValueTv']")
    private WebElement mobileValue;

    // ── Alternative Medical Decision Maker – section ─────────────────────────
    @AndroidFindBy(xpath = "(//android.widget.TextView[@resource-id=\"com.fluenthealth.app:id/addItemTv\"])[2]")
    private WebElement amdAddButton;

    // ── Alternative Medical Decision Maker – Add / Edit form ──────────────────
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Add Alternative Medical Decision-Maker']")
    private WebElement addAMDFormTitle;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Edit Alternative Medical Decision-Maker']")
    private WebElement editAMDFormTitle;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='First name*']")
    private WebElement amdFirstNameInput;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Last name*']")
    private WebElement amdLastNameInput;

    @AndroidFindBy(id = "com.fluenthealth.app:id/selectionValueTv")
    private WebElement amdRelationshipSelector;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Contact number*']")
    private WebElement amdContactNumberInput;

    @AndroidFindBy(id = "com.fluenthealth.app:id/button")
    private WebElement amdSaveButton;

    @AndroidFindBy(id = "com.fluenthealth.app:id/fl_cancel")
    private WebElement amdCancelButton;

    @AndroidFindBy(id = "com.fluenthealth.app:id/fl_delete")
    private WebElement amdDeleteButton;

    // ── Alternative Medical Decision Maker – saved card (view mode) ───────────
    @AndroidFindBy(id = "com.fluenthealth.app:id/nameTv")
    private WebElement amdSavedName;

    @AndroidFindBy(id = "com.fluenthealth.app:id/editTv")
    private WebElement amdEditButton;

    @AndroidFindBy(id = "com.fluenthealth.app:id/numberValueTv")
    private WebElement amdSavedNumber;

    @AndroidFindBy(id = "com.fluenthealth.app:id/selectorValueTv")
    private WebElement amdSavedRelationship;

    // ── Health Insurance – section ────────────────────────────────────────────
    @AndroidFindBy(xpath = "//*[@resource-id='com.fluenthealth.app:id/healthInsuranceContainer']//*[@resource-id='com.fluenthealth.app:id/addItemTv']")
    private WebElement hiAddButton;

    // ── Health Insurance – Add / Edit form ────────────────────────────────────
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Add Health Insurance']")
    private WebElement addHIFormTitle;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Edit Health Insurance']")
    private WebElement editHIFormTitle;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Insurance name*'][ancestor::android.widget.ScrollView[contains(@resource-id, 'insurance') or contains(@resource-id, 'hi')]]")
    private WebElement hiInsuranceNameInput;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Policy number'][ancestor::android.widget.ScrollView[contains(@resource-id, 'insurance') or contains(@resource-id, 'hi')]]")
    private WebElement hiPolicyNumberInput;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Insured member ID*'][ancestor::android.widget.ScrollView[contains(@resource-id, 'insurance') or contains(@resource-id, 'hi')]]")
    private WebElement hiMemberIdInput;

    @AndroidFindBy(id = "com.fluenthealth.app:id/button")
    private WebElement hiSaveButton;

    @AndroidFindBy(id = "com.fluenthealth.app:id/fl_cancel")
    private WebElement hiCancelButton;

    @AndroidFindBy(id = "com.fluenthealth.app:id/fl_delete")
    private WebElement hiDeleteButton;

    // ── Health Insurance – saved card (view mode) ─────────────────────────────
    @AndroidFindBy(id = "com.fluenthealth.app:id/nameTv")
    private WebElement hiSavedInsuranceName;

    @AndroidFindBy(id = "com.fluenthealth.app:id/editTv")
    private WebElement hiEditButton;

    @AndroidFindBy(id = "com.fluenthealth.app:id/policyNumberValueTv")
    private WebElement hiSavedPolicyNumber;

    @AndroidFindBy(id = "com.fluenthealth.app:id/memberIdValueTv")
    private WebElement hiSavedMemberId;

    // ── Emergency Contact – section ───────────────────────────────────────────
    @AndroidFindBy(xpath = "//*[@resource-id='com.fluenthealth.app:id/emergencyContainer']//*[@resource-id='com.fluenthealth.app:id/addItemTv']")
    private WebElement emergencyAddButton;

    // ── Emergency Contact – Add / Edit form ───────────────────────────────────
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Add Emergency Contact']")
    private WebElement addECFormTitle;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Edit Emergency Contact']")
    private WebElement editECFormTitle;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='First name*']")
    private WebElement ecFirstNameInput;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Last name*']")
    private WebElement ecLastNameInput;

    @AndroidFindBy(id = "com.fluenthealth.app:id/selectionValueTv")
    private WebElement ecRelationshipSelector;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Contact number*']")
    private WebElement ecContactNumberInput;

    @AndroidFindBy(id = "com.fluenthealth.app:id/button")
    private WebElement ecSaveButton;

    @AndroidFindBy(id = "com.fluenthealth.app:id/fl_cancel")
    private WebElement ecCancelButton;

    @AndroidFindBy(id = "com.fluenthealth.app:id/fl_delete")
    private WebElement ecDeleteButton;

    // ── Emergency Contact – saved card (view mode) ────────────────────────────
    @AndroidFindBy(id = "com.fluenthealth.app:id/nameTv")
    private WebElement ecSavedName;

    @AndroidFindBy(id = "com.fluenthealth.app:id/editTv")
    private WebElement ecEditButton;

    @AndroidFindBy(id = "com.fluenthealth.app:id/numberValueTv")
    private WebElement ecSavedNumber;

    @AndroidFindBy(id = "com.fluenthealth.app:id/selectorValueTv")
    private WebElement ecSavedRelationship;

    // ── Delete confirmation dialog ────────────────────────────────────────────
    @AndroidFindBy(id = "com.fluenthealth.app:id/negativeBtn")
    private WebElement deleteConfirmRemoveBtn;

    @AndroidFindBy(id = "com.fluenthealth.app:id/positiveBtn")
    private WebElement deleteConfirmCancelBtn;

    // ═════════════════════════════════════════════════════════════════════════
    // Profile read-only methods
    // ═════════════════════════════════════════════════════════════════════════

    public boolean isDisplayed() {
        return isVisible(basicInfoTab);
    }

    public BasicInfoPage tapBasicInfoTab() {
        log.info("Tapping Basic Info tab");
        tap(basicInfoTab);
        return this;
    }

    public String getFirstName() {
        scrollToElementById("com.fluenthealth.app:id/firstNameBasicInfo");
        return getText(firstNameValue);
    }

    public String getLastName() {
        scrollToElementById("com.fluenthealth.app:id/lastNameBasicInfo");
        return getText(lastNameValue);
    }

    public String getEmail() {
        scrollToElementById("com.fluenthealth.app:id/emailBasicInfo");
        return getText(emailValue);
    }

    public String getMobileNumber() {
        scrollToElementById("com.fluenthealth.app:id/phoneBasicInfo");
        return getText(mobileValue);
    }

    public boolean isFirstNameEditable() {
        scrollToElementById("com.fluenthealth.app:id/firstNameBasicInfo");
        return "true".equals(firstNameValue.getDomAttribute("clickable"));
    }

    public boolean isLastNameEditable() {
        scrollToElementById("com.fluenthealth.app:id/lastNameBasicInfo");
        return "true".equals(lastNameValue.getDomAttribute("clickable"));
    }

    public boolean isEmailEditable() {
        scrollToElementById("com.fluenthealth.app:id/emailBasicInfo");
        return "true".equals(emailValue.getDomAttribute("clickable"));
    }

    public boolean isMobileEditable() {
        scrollToElementById("com.fluenthealth.app:id/phoneBasicInfo");
        return "true".equals(mobileValue.getDomAttribute("clickable"));
    }

    // ═════════════════════════════════════════════════════════════════════════
    // Emergency Contact – navigation
    // ═════════════════════════════════════════════════════════════════════════

    public BasicInfoPage scrollToEmergencyContactSection() {
        log.info("Scrolling to Emergency Contact section");
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().textContains(\"Emergency Contact\"))"));
        return this;
    }

    public BasicInfoPage scrollToAMDSection() {
        log.info("Scrolling to Alternative Medical Decision Maker section");
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().textContains(\"Medical Decision\"))"));
        return this;
    }

    public BasicInfoPage scrollToHealthInsuranceSection() {
        log.info("Scrolling to Health Insurance section");
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().textContains(\"Health Insurance\"))"));
        return this;
    }

    public BasicInfoPage tapEmergencyContactAddButton() {
        log.info("Tapping Emergency Contact Add button");
        tap(emergencyAddButton);
        return this;
    }

    public BasicInfoPage tapAMDAddButton() {
        log.info("Tapping Alternative Medical Decision Maker Add button");
        tap(amdAddButton);
        return this;
    }

    public BasicInfoPage tapHIAddButton() {
        log.info("Tapping Health Insurance Add button");
        tap(hiAddButton);
        return this;
    }

    // ═════════════════════════════════════════════════════════════════════════
    // Emergency Contact – form visibility checks
    // ═════════════════════════════════════════════════════════════════════════

    public boolean isAddEmergencyContactFormVisible() {
        return waitForVisible(addECFormTitle);
    }

    public boolean isEditEmergencyContactFormVisible() {
        return waitForVisible(editECFormTitle);
    }

    public boolean isAddAMDFormVisible() {
        return waitForVisible(addAMDFormTitle);
    }

    public boolean isEditAMDFormVisible() {
        return waitForVisible(editAMDFormTitle);
    }

    public boolean isAddHIFormVisible() {
        return waitForVisible(addHIFormTitle);
    }

    public boolean isEditHIFormVisible() {
        return waitForVisible(editHIFormTitle);
    }

    // ═════════════════════════════════════════════════════════════════════════
    // Emergency Contact – mandatory field checks
    // ═════════════════════════════════════════════════════════════════════════

    public boolean isFirstNameFieldMandatory() {
        String hint = ecFirstNameInput.getDomAttribute("hint");
        return hint != null && hint.contains("*");
    }

    public boolean isLastNameFieldMandatory() {
        String hint = ecLastNameInput.getDomAttribute("hint");
        return hint != null && hint.contains("*");
    }

    public boolean isContactNumberFieldMandatory() {
        String hint = ecContactNumberInput.getDomAttribute("hint");
        return hint != null && hint.contains("*");
    }

    public boolean isAMDFirstNameMandatory() {
        String hint = amdFirstNameInput.getDomAttribute("hint");
        return hint != null && hint.contains("*");
    }

    public boolean isAMDLNameMandatory() {
        String hint = amdLastNameInput.getDomAttribute("hint");
        return hint != null && hint.contains("*");
    }

    public boolean isAMDContactMandatory() {
        String hint = amdContactNumberInput.getDomAttribute("hint");
        return hint != null && hint.contains("*");
    }

    public boolean isECSaveButtonEnabled() {
        scrollToElementById("com.fluenthealth.app:id/button");
        return ecSaveButton.isEnabled();
    }

    public boolean isAMDSaveButtonEnabled() {
        scrollToElementById("com.fluenthealth.app:id/button");
        return amdSaveButton.isEnabled();
    }

    public boolean isHIFirstNameMandatory() {
        String hint = hiInsuranceNameInput.getDomAttribute("hint");
        return hint != null && hint.contains("*");
    }

    public boolean isHIMemberIdMandatory() {
        String hint = hiMemberIdInput.getDomAttribute("hint");
        return hint != null && hint.contains("*");
    }

    public boolean isHISaveButtonEnabled() {
        scrollToElementById("com.fluenthealth.app:id/button");
        return hiSaveButton.isEnabled();
    }

    // ═════════════════════════════════════════════════════════════════════════
    // Emergency Contact – form input
    // ═════════════════════════════════════════════════════════════════════════

    public BasicInfoPage enterECFirstName(String name) {
        log.info("Entering EC first name: {}", name);
        tap(ecFirstNameInput);
        ecFirstNameInput.sendKeys(name);
        hideKeyboard();
        return this;
    }

    public BasicInfoPage enterECLastName(String name) {
        log.info("Entering EC last name: {}", name);
        tap(ecLastNameInput);
        ecLastNameInput.sendKeys(name);
        hideKeyboard();
        return this;
    }

    public BasicInfoPage enterECContactNumber(String number) {
        log.info("Entering EC contact number: {}", number);
        tap(ecContactNumberInput);
        ecContactNumberInput.sendKeys(number);
        hideKeyboard();
        return this;
    }

    public BasicInfoPage enterAMDFirstName(String name) {
        log.info("Entering AMD first name: {}", name);
        tap(amdFirstNameInput);
        amdFirstNameInput.sendKeys(name);
        hideKeyboard();
        return this;
    }

    public BasicInfoPage enterAMDLName(String name) {
        log.info("Entering AMD last name: {}", name);
        tap(amdLastNameInput);
        amdLastNameInput.sendKeys(name);
        hideKeyboard();
        return this;
    }

    public BasicInfoPage enterAMDContactNumber(String number) {
        log.info("Entering AMD contact number: {}", number);
        tap(amdContactNumberInput);
        amdContactNumberInput.sendKeys(number);
        hideKeyboard();
        return this;
    }

    public BasicInfoPage enterHIInsuranceName(String name) {
        log.info("Entering HI insurance name: {}", name);
        tap(hiInsuranceNameInput);
        hiInsuranceNameInput.sendKeys(name);
        hideKeyboard();
        return this;
    }

    public BasicInfoPage enterHIPolicyNumber(String policy) {
        log.info("Entering HI policy number: {}", policy);
        tap(hiPolicyNumberInput);
        hiPolicyNumberInput.sendKeys(policy);
        hideKeyboard();
        return this;
    }

    public BasicInfoPage enterHIMemberId(String memberId) {
        log.info("Entering HI member ID: {}", memberId);
        tap(hiMemberIdInput);
        hiMemberIdInput.sendKeys(memberId);
        hideKeyboard();
        return this;
    }

    public BasicInfoPage clearAndEnterECFirstName(String name) {
        log.info("Updating EC first name to: {}", name);
        clearAndType(ecFirstNameInput, name);
        return this;
    }

    public BasicInfoPage clearAndEnterECLastName(String name) {
        log.info("Updating EC last name to: {}", name);
        clearAndType(ecLastNameInput, name);
        return this;
    }

    public BasicInfoPage clearAndEnterECContactNumber(String number) {
        log.info("Updating EC contact number to: {}", number);
        clearAndType(ecContactNumberInput, number);
        return this;
    }

    public BasicInfoPage clearAndEnterAMDFirstName(String name) {
        log.info("Updating AMD first name to: {}", name);
        clearAndType(amdFirstNameInput, name);
        return this;
    }

    public BasicInfoPage clearAndEnterAMDLName(String name) {
        log.info("Updating AMD last name to: {}", name);
        clearAndType(amdLastNameInput, name);
        return this;
    }

    public BasicInfoPage clearAndEnterAMDContactNumber(String number) {
        log.info("Updating AMD contact number to: {}", number);
        clearAndType(amdContactNumberInput, number);
        return this;
    }

    public BasicInfoPage clearAndEnterHIInsuranceName(String name) {
        log.info("Updating HI insurance name to: {}", name);
        clearAndType(hiInsuranceNameInput, name);
        return this;
    }

    public BasicInfoPage clearAndEnterHIPolicyNumber(String policy) {
        log.info("Updating HI policy number to: {}", policy);
        clearAndType(hiPolicyNumberInput, policy);
        return this;
    }

    public BasicInfoPage clearAndEnterHIMemberId(String memberId) {
        log.info("Updating HI member ID to: {}", memberId);
        clearAndType(hiMemberIdInput, memberId);
        return this;
    }

    public BasicInfoPage selectECRelationship(String value) {
        log.info("Selecting EC relationship: {}", value);
        tap(ecRelationshipSelector);
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"" + value + "\")")).click();
        return this;
    }

    public BasicInfoPage selectAMDRelationship(String value) {
        log.info("Selecting AMD relationship: {}", value);
        tap(amdRelationshipSelector);
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"" + value + "\")")).click();
        return this;
    }

    public BasicInfoPage tapSaveECForm() {
        scrollToElementById("com.fluenthealth.app:id/button");
        if (!ecSaveButton.isEnabled()) {
            log.warn(
                    "Save button is disabled — all mandatory fields (First name*, Last name*, Contact number*) must be filled");
            return this;
        }
        log.info("Tapping Save on Emergency Contact form");
        tap(ecSaveButton);
        return this;
    }

    public BasicInfoPage tapSaveAMDForm() {
        scrollToElementById("com.fluenthealth.app:id/button");
        if (!amdSaveButton.isEnabled()) {
            log.warn("Save button is disabled — all mandatory fields must be filled");
            return this;
        }
        log.info("Tapping Save on AMD form");
        tap(amdSaveButton);
        return this;
    }

    public BasicInfoPage tapCancelECForm() {
        log.info("Tapping Cancel/Close on Emergency Contact form");
        tap(ecCancelButton);
        return this;
    }

    public BasicInfoPage tapCancelAMDForm() {
        log.info("Tapping Cancel/Close on AMD form");
        tap(amdCancelButton);
        return this;
    }

    public BasicInfoPage tapSaveHIForm() {
        scrollToElementById("com.fluenthealth.app:id/button");
        if (!hiSaveButton.isEnabled()) {
            log.warn("Save button is disabled — all mandatory fields must be filled");
            return this;
        }
        log.info("Tapping Save on HI form");
        tap(hiSaveButton);
        return this;
    }

    public BasicInfoPage tapCancelHIForm() {
        log.info("Tapping Cancel/Close on HI form");
        tap(hiCancelButton);
        return this;
    }

    // ═════════════════════════════════════════════════════════════════════════
    // Emergency Contact – saved card (view mode)
    // ═════════════════════════════════════════════════════════════════════════

    public boolean isEmergencyContactPresent() {
        return isVisible(ecSavedName);
    }

    public String getECSavedName() {
        log.info("Getting saved EC contact name");
        return getText(ecSavedName);
    }

    public String getECSavedNumber() {
        log.info("Getting saved EC contact number");
        // Strip country code (+91) and any spaces/formatting; return digits only
        return getText(ecSavedNumber).replaceAll("[^0-9]", "");
    }

    public String getECSavedRelationship() {
        log.info("Getting saved EC relationship");
        return getText(ecSavedRelationship);
    }

    public boolean isAMDPresent() {
        return isVisible(amdSavedName);
    }

    public boolean isHIPresent() {
        return isVisible(hiSavedInsuranceName);
    }

    public String getAMDSavedName() {
        log.info("Getting saved AMD name");
        return getText(amdSavedName);
    }

    public String getHISavedInsuranceName() {
        log.info("Getting saved HI insurance name");
        return getText(hiSavedInsuranceName);
    }

    public String getHISavedPolicyNumber() {
        log.info("Getting saved HI policy number");
        return getText(hiSavedPolicyNumber);
    }

    public String getHISavedMemberId() {
        log.info("Getting saved HI member ID");
        return getText(hiSavedMemberId);
    }

    public String getAMDSavedNumber() {
        log.info("Getting saved AMD number");
        return getText(amdSavedNumber).replaceAll("[^0-9]", "");
    }

    public String getAMDSavedRelationship() {
        log.info("Getting saved AMD relationship");
        return getText(amdSavedRelationship);
    }

    public BasicInfoPage tapEditEmergencyContact() {
        log.info("Tapping Edit on Emergency Contact card");
        tap(ecEditButton);
        return this;
    }

    public BasicInfoPage tapEditAMD() {
        log.info("Tapping Edit on AMD card");
        tap(amdEditButton);
        return this;
    }

    public BasicInfoPage tapEditHI() {
        log.info("Tapping Edit on HI card");
        tap(hiEditButton);
        return this;
    }

    // ═════════════════════════════════════════════════════════════════════════
    // Emergency Contact – delete flow
    // ═════════════════════════════════════════════════════════════════════════

    public BasicInfoPage tapDeleteECButton() {
        log.info("Tapping Delete (trash) on Emergency Contact edit form");
        tap(ecDeleteButton);
        return this;
    }

    public BasicInfoPage tapDeleteAMDButton() {
        log.info("Tapping Delete (trash) on AMD edit form");
        tap(amdDeleteButton);
        return this;
    }

    public BasicInfoPage tapDeleteHIButton() {
        log.info("Tapping Delete (trash) on HI edit form");
        tap(hiDeleteButton);
        return this;
    }

    public boolean isDeleteConfirmationDialogVisible() {
        return isVisible(deleteConfirmRemoveBtn);
    }

    public BasicInfoPage confirmDeleteEC() {
        log.info("Confirming Emergency Contact deletion");
        tap(deleteConfirmRemoveBtn);
        return this;
    }

    public BasicInfoPage confirmDeleteAMD() {
        log.info("Confirming AMD deletion");
        tap(deleteConfirmRemoveBtn);
        return this;
    }

    public BasicInfoPage cancelDeleteEC() {
        log.info("Cancelling Emergency Contact deletion");
        tap(deleteConfirmCancelBtn);
        return this;
    }

    public BasicInfoPage cancelDeleteAMD() {
        log.info("Cancelling AMD deletion");
        tap(deleteConfirmCancelBtn);
        return this;
    }
}
