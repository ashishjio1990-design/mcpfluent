package com.fluent.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class SurgeriesAndProceduresPage extends BasePage {

    // ── Toolbar ───────────────────────────────────────────────────────────────
    @AndroidFindBy(id = "com.fluenthealth.app:id/backIV")
    private WebElement backButton;

    @AndroidFindBy(id = "com.fluenthealth.app:id/ic_delete")
    private WebElement deleteButton;

    // ── List screen ───────────────────────────────────────────────────────────
    @AndroidFindBy(id = "com.fluenthealth.app:id/contentRV")
    private WebElement contentRecyclerView;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.LinearLayout\").instance(2)")
    private WebElement surgeryDetail;

    @AndroidFindBy(id = "com.fluenthealth.app:id/titleTV")
    private WebElement surgeryTitleCard;

    @AndroidFindBy(id = "com.fluenthealth.app:id/dateTV")
    private WebElement surgeryDateCard;

    @AndroidFindBy(id = "com.fluenthealth.app:id/tagTV")
    private WebElement surgeryStatusTag;

    @AndroidFindBy(id = "com.fluenthealth.app:id/button")
    private WebElement primaryButton;

    // ── Add/Edit form – three selectionValueTv fields (surgery, status, date) ─
    // Index-based fallback: text changes after selection per field
    @AndroidFindBy(xpath = "(//android.widget.TextView[@resource-id=\"com.fluenthealth.app:id/selectionValueTv\"])[1]")
    private WebElement surgerySearchField;

    @AndroidFindBy(xpath = "(//android.widget.TextView[@resource-id=\"com.fluenthealth.app:id/selectionValueTv\"])[2]")
    private WebElement statusField;

    @AndroidFindBy(xpath = "(//android.widget.TextView[@resource-id=\"com.fluenthealth.app:id/selectionValueTv\"])[3]")
    private WebElement dateField;

    @AndroidFindBy(id = "com.fluenthealth.app:id/noteTextInputEt")
    private WebElement notesInput;

    // ── Search bottom sheet ───────────────────────────────────────────────────
    @AndroidFindBy(id = "com.fluenthealth.app:id/et_search_bar")
    private WebElement surgerySearchInput;

    // ── Delete confirmation dialog ────────────────────────────────────────────
    @AndroidFindBy(id = "com.fluenthealth.app:id/negativeBtn")
    private WebElement deleteConfirmRemoveBtn;

    @AndroidFindBy(id = "com.fluenthealth.app:id/positiveBtn")
    private WebElement deleteConfirmCancelBtn;

    // ═════════════════════════════════════════════════════════════════════════
    // Navigation – My Health Information screen
    // ═════════════════════════════════════════════════════════════════════════

    public SurgeriesAndProceduresPage scrollToSurgeriesSection() {
        log.info("Scrolling to Surgeries and/or Procedures");
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().textContains(\"Surgeries\"))"));
        return this;
    }

    public SurgeriesAndProceduresPage tapSurgeriesAndProcedures() {
        log.info("Tapping Surgeries and/or Procedures");
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().textContains(\"Surgeries\")")).click();
        return this;
    }

    // ═════════════════════════════════════════════════════════════════════════
    // List screen
    // ═════════════════════════════════════════════════════════════════════════

    public boolean isSurgeriesListVisible() {
        return waitForVisible(contentRecyclerView);
    }

    public boolean isSurgeryCardPresent() {
        return isVisible(surgeryTitleCard);
    }

    public String getSurgeryDetailText() {
        log.info("Getting full surgery detail card text");
        return getText(surgeryDetail);
    }

    public boolean surgeryDetailContains(String value) {
        return getSurgeryDetailText().contains(value);
    }

    public String getSavedSurgeryName() {
        log.info("Getting saved surgery name");
        return getText(surgeryTitleCard);
    }

    public String getSavedDate() {
        log.info("Getting saved surgery date");
        return getText(surgeryDateCard);
    }

    public String getSavedStatus() {
        log.info("Getting saved surgery status");
        return getText(surgeryStatusTag);
    }

    public SurgeriesAndProceduresPage tapSurgeryCard() {
        log.info("Tapping surgery card to open edit form");
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().clickable(true).childSelector(new UiSelector().resourceId(\"com.fluenthealth.app:id/titleTV\"))")).click();
        return this;
    }

    public SurgeriesAndProceduresPage tapAddNewButton() {
        log.info("Tapping Add new button");
        scrollToElementById("com.fluenthealth.app:id/button");
        tap(primaryButton);
        return this;
    }

    // ═════════════════════════════════════════════════════════════════════════
    // Add/Edit form
    // ═════════════════════════════════════════════════════════════════════════

    public boolean isAddFormVisible() {
        return waitForVisible(surgerySearchField);
    }

    public SurgeriesAndProceduresPage tapSurgerySearchField() {
        log.info("Tapping surgery search field");
        tap(surgerySearchField);
        return this;
    }

    public SurgeriesAndProceduresPage selectSurgeryFromCommonList(String surgeryName) {
        log.info("Selecting surgery from common list: {}", surgeryName);
        waitForVisible(surgerySearchInput);
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().resourceId(\"com.fluenthealth.app:id/valueTv\").text(\"" + surgeryName + "\")")).click();
        return this;
    }

    public SurgeriesAndProceduresPage searchAndSelectSurgery(String query) {
        log.info("Searching for surgery: {}", query);
        waitForVisible(surgerySearchInput);
        tap(surgerySearchInput);
        surgerySearchInput.sendKeys(query);
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().resourceId(\"com.fluenthealth.app:id/valueTv\").textContains(\"" + query + "\")")).click();
        return this;
    }

    public SurgeriesAndProceduresPage tapStatusField() {
        log.info("Tapping status dropdown field");
        tap(statusField);
        return this;
    }

    public SurgeriesAndProceduresPage selectStatus(String status) {
        log.info("Selecting status: {}", status);
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"" + status + "\")")).click();
        return this;
    }

    public SurgeriesAndProceduresPage tapDateField() {
        log.info("Tapping date field");
        tap(dateField);
        return this;
    }

    public SurgeriesAndProceduresPage confirmDatePicker() {
        log.info("Confirming date picker selection");
        driver.findElement(AppiumBy.id("android:id/button1")).click();
        return this;
    }

    public SurgeriesAndProceduresPage cancelDatePicker() {
        log.info("Cancelling date picker");
        driver.findElement(AppiumBy.id("android:id/button2")).click();
        return this;
    }

    public String getNotesText() {
        log.info("Getting notes field text");
        return getText(notesInput);
    }

    public SurgeriesAndProceduresPage enterNotes(String notes) {
        log.info("Entering notes: {}", notes);
        tap(notesInput);
        notesInput.sendKeys(notes);
        hideKeyboard();
        return this;
    }

    public SurgeriesAndProceduresPage clearAndEnterNotes(String notes) {
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

    public SurgeriesAndProceduresPage tapDoneButton() {
        log.info("Tapping Done button");
        scrollToElementById("com.fluenthealth.app:id/button");
        tap(primaryButton);
        return this;
    }

    public SurgeriesAndProceduresPage tapDeleteButton() {
        log.info("Tapping delete (trash) icon");
        tap(deleteButton);
        return this;
    }

    public SurgeriesAndProceduresPage tapBackButton() {
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

    public SurgeriesAndProceduresPage confirmDelete() {
        log.info("Confirming delete – tapping Remove");
        tap(deleteConfirmRemoveBtn);
        return this;
    }

    public SurgeriesAndProceduresPage cancelDelete() {
        log.info("Cancelling delete – tapping Cancel");
        tap(deleteConfirmCancelBtn);
        return this;
    }
}
