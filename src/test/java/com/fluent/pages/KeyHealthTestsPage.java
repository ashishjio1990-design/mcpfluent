package com.fluent.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class KeyHealthTestsPage extends BasePage {

    // ── Toolbar ───────────────────────────────────────────────────────────────
    @AndroidFindBy(id = "com.fluenthealth.app:id/backIV")
    private WebElement backButton;

    @AndroidFindBy(id = "com.fluenthealth.app:id/ic_delete")
    private WebElement deleteButton;

    // ── List screen ───────────────────────────────────────────────────────────
    @AndroidFindBy(id = "com.fluenthealth.app:id/contentRV")
    private WebElement contentRecyclerView;

    @AndroidFindBy(id = "com.fluenthealth.app:id/titleTV")
    private WebElement testTitleCard;

    @AndroidFindBy(id = "com.fluenthealth.app:id/dateTV")
    private WebElement testDateCard;

    @AndroidFindBy(id = "com.fluenthealth.app:id/button")
    private WebElement primaryButton;

    // ── Add/Edit form – two selectionValueTv fields (test name, date) ─────────
    @AndroidFindBy(xpath = "(//android.widget.TextView[@resource-id=\"com.fluenthealth.app:id/selectionValueTv\"])[1]")
    private WebElement testSearchField;

    @AndroidFindBy(xpath = "(//android.widget.TextView[@resource-id=\"com.fluenthealth.app:id/selectionValueTv\"])[2]")
    private WebElement dateField;

    @AndroidFindBy(id = "com.fluenthealth.app:id/noteTextInputEt")
    private WebElement notesInput;

    // ── Search bottom sheet ───────────────────────────────────────────────────
    @AndroidFindBy(id = "com.fluenthealth.app:id/et_search_bar")
    private WebElement testSearchInput;

    // ── Delete confirmation dialog ────────────────────────────────────────────
    @AndroidFindBy(id = "com.fluenthealth.app:id/negativeBtn")
    private WebElement deleteConfirmRemoveBtn;

    @AndroidFindBy(id = "com.fluenthealth.app:id/positiveBtn")
    private WebElement deleteConfirmCancelBtn;

    // ═════════════════════════════════════════════════════════════════════════
    // Navigation – My Health Information screen
    // ═════════════════════════════════════════════════════════════════════════

    public KeyHealthTestsPage scrollToKeyHealthTestsSection() {
        log.info("Scrolling to Key Health Tests");
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().textContains(\"Key Health Tests\"))"));
        return this;
    }

    public KeyHealthTestsPage tapKeyHealthTests() {
        log.info("Tapping Key Health Tests");
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().textContains(\"Key Health Tests\")")).click();
        return this;
    }

    // ═════════════════════════════════════════════════════════════════════════
    // List screen
    // ═════════════════════════════════════════════════════════════════════════

    public boolean isKeyHealthTestsListVisible() {
        return waitForVisible(contentRecyclerView);
    }

    public boolean isTestCardPresent() {
        return isVisible(testTitleCard);
    }

    public String getSavedTestName() {
        log.info("Getting saved test name");
        return getText(testTitleCard);
    }

    public String getSavedDate() {
        log.info("Getting saved test date");
        return getText(testDateCard);
    }

    public KeyHealthTestsPage tapTestCard() {
        log.info("Tapping test card to open edit form");
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().clickable(true).childSelector(new UiSelector().resourceId(\"com.fluenthealth.app:id/titleTV\"))")).click();
        return this;
    }

    public KeyHealthTestsPage tapAddNewButton() {
        log.info("Tapping Add new button");
        scrollToElementById("com.fluenthealth.app:id/button");
        tap(primaryButton);
        return this;
    }

    // ═════════════════════════════════════════════════════════════════════════
    // Add/Edit form
    // ═════════════════════════════════════════════════════════════════════════

    public boolean isAddFormVisible() {
        return waitForVisible(testSearchField);
    }

    public KeyHealthTestsPage tapTestSearchField() {
        log.info("Tapping test search field");
        tap(testSearchField);
        return this;
    }

    public KeyHealthTestsPage selectTestFromCommonList(String testName) {
        log.info("Selecting test from common list: {}", testName);
        waitForVisible(testSearchInput);
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().resourceId(\"com.fluenthealth.app:id/valueTv\").text(\"" + testName + "\")")).click();
        return this;
    }

    public KeyHealthTestsPage searchAndSelectTest(String query) {
        log.info("Searching for test: {}", query);
        waitForVisible(testSearchInput);
        tap(testSearchInput);
        testSearchInput.sendKeys(query);
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().resourceId(\"com.fluenthealth.app:id/valueTv\").textContains(\"" + query + "\")")).click();
        return this;
    }

    public KeyHealthTestsPage tapDateField() {
        log.info("Tapping date field");
        tap(dateField);
        return this;
    }

    public KeyHealthTestsPage confirmDatePicker() {
        log.info("Confirming date picker selection");
        driver.findElement(AppiumBy.id("android:id/button1")).click();
        return this;
    }

    public KeyHealthTestsPage cancelDatePicker() {
        log.info("Cancelling date picker");
        driver.findElement(AppiumBy.id("android:id/button2")).click();
        return this;
    }

    public String getNotesText() {
        log.info("Getting notes field text");
        return getText(notesInput);
    }

    public KeyHealthTestsPage enterNotes(String notes) {
        log.info("Entering notes: {}", notes);
        tap(notesInput);
        notesInput.sendKeys(notes);
        hideKeyboard();
        return this;
    }

    public KeyHealthTestsPage clearAndEnterNotes(String notes) {
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

    public KeyHealthTestsPage tapDoneButton() {
        log.info("Tapping Done button");
        scrollToElementById("com.fluenthealth.app:id/button");
        tap(primaryButton);
        return this;
    }

    public KeyHealthTestsPage tapDeleteButton() {
        log.info("Tapping delete (trash) icon");
        tap(deleteButton);
        return this;
    }

    public KeyHealthTestsPage tapBackButton() {
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

    public KeyHealthTestsPage confirmDelete() {
        log.info("Confirming delete – tapping Remove");
        tap(deleteConfirmRemoveBtn);
        return this;
    }

    public KeyHealthTestsPage cancelDelete() {
        log.info("Cancelling delete – tapping Cancel");
        tap(deleteConfirmCancelBtn);
        return this;
    }
}
