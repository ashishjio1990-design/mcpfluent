package com.fluent.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class VaccinesPage extends BasePage {

    // ── Toolbar ───────────────────────────────────────────────────────────────
    @AndroidFindBy(id = "com.fluenthealth.app:id/backIV")
    private WebElement backButton;

    @AndroidFindBy(id = "com.fluenthealth.app:id/ic_delete")
    private WebElement deleteButton;

    // ── List screen ───────────────────────────────────────────────────────────
    @AndroidFindBy(id = "com.fluenthealth.app:id/contentRV")
    private WebElement contentRecyclerView;

    @AndroidFindBy(id = "com.fluenthealth.app:id/titleTV")
    private WebElement vaccineTitleCard;

    @AndroidFindBy(id = "com.fluenthealth.app:id/dateTV")
    private WebElement vaccineDateCard;

    @AndroidFindBy(id = "com.fluenthealth.app:id/button")
    private WebElement primaryButton;

    // ── Add/Edit form – three selectionValueTv fields (vaccine, dose, date) ──
    @AndroidFindBy(xpath = "(//android.widget.TextView[@resource-id=\"com.fluenthealth.app:id/selectionValueTv\"])[1]")
    private WebElement vaccineSearchField;

    @AndroidFindBy(xpath = "(//android.widget.TextView[@resource-id=\"com.fluenthealth.app:id/selectionValueTv\"])[2]")
    private WebElement doseSelectorField;

    @AndroidFindBy(xpath = "(//android.widget.TextView[@resource-id=\"com.fluenthealth.app:id/selectionValueTv\"])[3]")
    private WebElement dateField;

    @AndroidFindBy(id = "com.fluenthealth.app:id/noteTextInputEt")
    private WebElement notesInput;

    // ── Search bottom sheet ───────────────────────────────────────────────────
    @AndroidFindBy(id = "com.fluenthealth.app:id/et_search_bar")
    private WebElement vaccineSearchInput;

    // ── Delete confirmation dialog ────────────────────────────────────────────
    @AndroidFindBy(id = "com.fluenthealth.app:id/negativeBtn")
    private WebElement deleteConfirmRemoveBtn;

    @AndroidFindBy(id = "com.fluenthealth.app:id/positiveBtn")
    private WebElement deleteConfirmCancelBtn;

    // ═════════════════════════════════════════════════════════════════════════
    // Navigation – My Health Information screen
    // ═════════════════════════════════════════════════════════════════════════

    public VaccinesPage scrollToVaccinesSection() {
        log.info("Scrolling to Vaccines section");
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().textContains(\"Vaccines\"))"));
        return this;
    }

    public VaccinesPage tapVaccines() {
        log.info("Tapping Vaccines");
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().textContains(\"Vaccines\")")).click();
        return this;
    }

    // ═════════════════════════════════════════════════════════════════════════
    // List screen
    // ═════════════════════════════════════════════════════════════════════════

    public boolean isVaccinesListVisible() {
        return waitForVisible(contentRecyclerView);
    }

    public boolean isVaccineCardPresent() {
        return isVisible(vaccineTitleCard);
    }

    public String getSavedVaccineName() {
        log.info("Getting saved vaccine name");
        return getText(vaccineTitleCard);
    }

    public String getSavedDate() {
        log.info("Getting saved vaccine date");
        return getText(vaccineDateCard);
    }

    public VaccinesPage tapVaccineCard() {
        log.info("Tapping vaccine card to open edit form");
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().clickable(true).childSelector(new UiSelector().resourceId(\"com.fluenthealth.app:id/titleTV\"))")).click();
        return this;
    }

    public VaccinesPage tapAddNewButton() {
        log.info("Tapping Add new button");
        scrollToElementById("com.fluenthealth.app:id/button");
        tap(primaryButton);
        return this;
    }

    // ═════════════════════════════════════════════════════════════════════════
    // Add/Edit form
    // ═════════════════════════════════════════════════════════════════════════

    public boolean isAddFormVisible() {
        return waitForVisible(vaccineSearchField);
    }

    public VaccinesPage tapVaccineSearchField() {
        log.info("Tapping vaccine search field");
        tap(vaccineSearchField);
        return this;
    }

    public VaccinesPage selectVaccineFromCommonList(String vaccineName) {
        log.info("Selecting vaccine from common list: {}", vaccineName);
        waitForVisible(vaccineSearchInput);
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().resourceId(\"com.fluenthealth.app:id/valueTv\").text(\"" + vaccineName + "\")")).click();
        return this;
    }

    public VaccinesPage searchAndSelectVaccine(String query) {
        log.info("Searching for vaccine: {}", query);
        waitForVisible(vaccineSearchInput);
        tap(vaccineSearchInput);
        vaccineSearchInput.sendKeys(query);
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().resourceId(\"com.fluenthealth.app:id/valueTv\").textContains(\"" + query + "\")")).click();
        return this;
    }

    public VaccinesPage tapDoseSelectorField() {
        log.info("Tapping dose selector field");
        tap(doseSelectorField);
        return this;
    }

    public VaccinesPage selectDose(String dose) {
        log.info("Selecting dose: {}", dose);
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"" + dose + "\")")).click();
        return this;
    }

    public VaccinesPage sendKeysDose(String dose) {
        log.info("Sending keys for dose: {}", dose);
        doseSelectorField.sendKeys(dose);
        return this;
    }

    public VaccinesPage tapDateField() {
        log.info("Tapping date field");
        tap(dateField);
        return this;
    }

    public VaccinesPage confirmDatePicker() {
        log.info("Confirming date picker selection");
        driver.findElement(AppiumBy.id("android:id/button1")).click();
        return this;
    }

    public VaccinesPage cancelDatePicker() {
        log.info("Cancelling date picker");
        driver.findElement(AppiumBy.id("android:id/button2")).click();
        return this;
    }

    public String getNotesText() {
        log.info("Getting notes field text");
        return getText(notesInput);
    }

    public VaccinesPage enterNotes(String notes) {
        log.info("Entering notes: {}", notes);
        tap(notesInput);
        notesInput.sendKeys(notes);
        hideKeyboard();
        return this;
    }

    public VaccinesPage clearAndEnterNotes(String notes) {
        log.info("Updating notes to: {}", notes);
        tap(notesInput);
        clearAndType(notesInput, notes);
        hideKeyboard();
        return this;
    }

    public boolean isDoneButtonEnabled() {
        scrollToElementById("com.fluenthealth.app:id/button");
        return primaryButton.isEnabled();
    }

    public VaccinesPage tapDoneButton() {
        log.info("Tapping Done button");
        scrollToElementById("com.fluenthealth.app:id/button");
        tap(primaryButton);
        return this;
    }

    public VaccinesPage tapDeleteButton() {
        log.info("Tapping delete (trash) icon");
        tap(deleteButton);
        return this;
    }

    public VaccinesPage tapBackButton() {
        log.info("Tapping back button");
        tap(backButton);
        return this;
    }

    // ═════════════════════════════════════════════════════════════════════════
    // Delete confirmation dialog
    // ═════════════════════════════════════════════════════════════════════════

    public boolean isDeleteConfirmationVisible() {
        return isVisible(deleteConfirmRemoveBtn);
    }

    public VaccinesPage confirmDelete() {
        log.info("Confirming delete – tapping Remove");
        tap(deleteConfirmRemoveBtn);
        return this;
    }

    public VaccinesPage cancelDelete() {
        log.info("Cancelling delete – tapping Cancel");
        tap(deleteConfirmCancelBtn);
        return this;
    }
}
