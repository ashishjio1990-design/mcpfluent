package com.fluent.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class VitalsPage extends BasePage {

    // ── Toolbar ───────────────────────────────────────────────────────────────
    @AndroidFindBy(id = "com.fluenthealth.app:id/backIV")
    private WebElement backButton;

    @AndroidFindBy(id = "com.fluenthealth.app:id/ic_delete")
    private WebElement deleteButton;

    // ── Vitals list screen ────────────────────────────────────────────────────
    @AndroidFindBy(id = "com.fluenthealth.app:id/scrollView")
    private WebElement vitalsScrollView;

    // ── Blood Pressure list screen ────────────────────────────────────────────
    @AndroidFindBy(id = "com.fluenthealth.app:id/contentRV")
    private WebElement contentRecyclerView;

    @AndroidFindBy(id = "com.fluenthealth.app:id/titleTV")
    private WebElement recordTitleCard;

    // ── Blood Pressure form – numeric inputs ──────────────────────────────────
    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Systolic (mmHg)*']")
    private WebElement systolicInput;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Diastolic (mmHg)*']")
    private WebElement diastolicInput;

    // ── Body Temperature form ─────────────────────────────────────────────────
    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Temperature (in °C or °F)*']")
    private WebElement temperatureInput;

    // ── Oxygen Saturation Level form ──────────────────────────────────────────
    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Oxygen saturation level (%)*']")
    private WebElement oxygenInput;

    // ── Pulse Rate form ───────────────────────────────────────────────────────
    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Beats per minute*']")
    private WebElement pulseRateInput;

    // ── Respiratory Rate form ─────────────────────────────────────────────────
    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Breaths per minute*']")
    private WebElement respiratoryRateInput;

    // ── Date / Time pickers (selectionValueTv [1]=Date, [2]=Time) ─────────────
    @AndroidFindBy(xpath = "(//android.widget.TextView[@resource-id=\"com.fluenthealth.app:id/selectionValueTv\"])[1]")
    private WebElement dateField;

    @AndroidFindBy(xpath = "(//android.widget.TextView[@resource-id=\"com.fluenthealth.app:id/selectionValueTv\"])[2]")
    private WebElement timeField;

    // ── Done button ───────────────────────────────────────────────────────────
    @AndroidFindBy(id = "com.fluenthealth.app:id/button")
    private WebElement doneButton;

    // ── Delete confirmation dialog ────────────────────────────────────────────
    @AndroidFindBy(id = "com.fluenthealth.app:id/negativeBtn")
    private WebElement deleteConfirmRemoveBtn;

    @AndroidFindBy(id = "com.fluenthealth.app:id/positiveBtn")
    private WebElement deleteConfirmCancelBtn;

    // ═════════════════════════════════════════════════════════════════════════
    // Navigation – My Health Information screen
    // ═════════════════════════════════════════════════════════════════════════

    public VitalsPage scrollToVitalsSection() {
        log.info("Scrolling to Vitals section");
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().textContains(\"Vitals\"))"));
        return this;
    }

    public VitalsPage tapVitals() {
        log.info("Tapping Vitals");
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().textContains(\"Vitals\")")).click();
        return this;
    }

    // ═════════════════════════════════════════════════════════════════════════
    // Vitals list screen – vital type cards
    // ═════════════════════════════════════════════════════════════════════════

    public boolean isVitalsListVisible() {
        return waitForVisible(vitalsScrollView);
    }

    public boolean isBloodPressureCardPresent() {
        return isVisible(driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"Blood Pressure\")")));
    }

    public VitalsPage tapBloodPressure() {
        log.info("Tapping Blood Pressure card");
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().resourceId(\"com.fluenthealth.app:id/titleTV\").text(\"Blood Pressure\")")).click();
        return this;
    }

    public boolean isBloodPressureListVisible() {
        return waitForVisible(contentRecyclerView);
    }

    public boolean isBloodPressureRecordPresent() {
        return isVisible(recordTitleCard);
    }

    public String getBloodPressureCardText() {
        String text = driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().resourceId(\"com.fluenthealth.app:id/titleTV\").instance(1)")).getText();
        log.info("Blood Pressure record card text: {}", text);
        return text;
    }

    public VitalsPage tapAddNewButton() {
        log.info("Tapping Add new button on Blood Pressure list");
        scrollToElementById("com.fluenthealth.app:id/button");
        tap(doneButton);
        return this;
    }

    public VitalsPage tapRecordCard() {
        log.info("Tapping Blood Pressure record card");
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().clickable(true).childSelector(new UiSelector().resourceId(\"com.fluenthealth.app:id/titleTV\"))")).click();
        return this;
    }

    // ═════════════════════════════════════════════════════════════════════════
    // Blood Pressure form
    // ═════════════════════════════════════════════════════════════════════════

    public boolean isFormVisible() {
        return waitForVisible(systolicInput);
    }

    public VitalsPage enterSystolic(String value) {
        log.info("Entering systolic value: {}", value);
        tap(systolicInput);
        systolicInput.sendKeys(value);
        return this;
    }

    public VitalsPage enterDiastolic(String value) {
        log.info("Entering diastolic value: {}", value);
        tap(diastolicInput);
        diastolicInput.sendKeys(value);
        return this;
    }

    public VitalsPage clearAndEnterSystolic(String value) {
        log.info("Updating systolic to: {}", value);
        tap(systolicInput);
        clearAndType(systolicInput, value);
        return this;
    }

    public VitalsPage clearAndEnterDiastolic(String value) {
        log.info("Updating diastolic to: {}", value);
        tap(diastolicInput);
        clearAndType(diastolicInput, value);
        return this;
    }

    public VitalsPage dismissKeyboard() {
        log.info("Dismissing keyboard");
        hideKeyboard();
        return this;
    }

    public String getSystolicText() {
        log.info("Getting systolic field text");
        return getText(systolicInput);
    }

    public String getDiastolicText() {
        log.info("Getting diastolic field text");
        return getText(diastolicInput);
    }

    public VitalsPage tapDateField() {
        log.info("Tapping date field");
        tap(dateField);
        return this;
    }

    public VitalsPage confirmDatePicker() {
        log.info("Confirming date picker");
        driver.findElement(AppiumBy.id("android:id/button1")).click();
        return this;
    }

    public VitalsPage tapTimeField() {
        log.info("Tapping time field");
        tap(timeField);
        return this;
    }

    public VitalsPage confirmTimePicker() {
        log.info("Confirming time picker");
        driver.findElement(AppiumBy.id("android:id/button1")).click();
        return this;
    }

    public boolean isDoneButtonEnabled() {
        return doneButton.isEnabled();
    }

    public VitalsPage tapDoneButton() {
        log.info("Tapping Done button");
        tap(doneButton);
        return this;
    }

    public VitalsPage tapDeleteButton() {
        log.info("Tapping delete (trash) icon");
        tap(deleteButton);
        return this;
    }

    public VitalsPage tapBackButton() {
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

    public VitalsPage confirmDelete() {
        log.info("Confirming delete – tapping Remove");
        tap(deleteConfirmRemoveBtn);
        return this;
    }

    public VitalsPage cancelDelete() {
        log.info("Cancelling delete – tapping Cancel");
        tap(deleteConfirmCancelBtn);
        return this;
    }

    // ═════════════════════════════════════════════════════════════════════════
    // Shared helper – record card text on any vital list screen
    // ═════════════════════════════════════════════════════════════════════════

    private String getVitalListCardText() {
        String text = driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().resourceId(\"com.fluenthealth.app:id/titleTV\").instance(1)")).getText();
        log.info("Vital record card text: {}", text);
        return text;
    }

    // ═════════════════════════════════════════════════════════════════════════
    // Body Temperature
    // ═════════════════════════════════════════════════════════════════════════

    public VitalsPage tapBodyTemperature() {
        log.info("Tapping Body Temperature card");
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().resourceId(\"com.fluenthealth.app:id/titleTV\").text(\"Body Temperature\")")).click();
        return this;
    }

    public boolean isBodyTemperatureListVisible() {
        return waitForVisible(contentRecyclerView);
    }

    public boolean isBodyTemperatureFormVisible() {
        return waitForVisible(temperatureInput);
    }

    public VitalsPage enterTemperature(String value) {
        log.info("Entering temperature: {}", value);
        tap(temperatureInput);
        temperatureInput.sendKeys(value);
        return this;
    }

    public VitalsPage clearAndEnterTemperature(String value) {
        log.info("Updating temperature to: {}", value);
        tap(temperatureInput);
        clearAndType(temperatureInput, value);
        return this;
    }

    public String getBodyTemperatureCardText() {
        return getVitalListCardText();
    }

    // ═════════════════════════════════════════════════════════════════════════
    // Oxygen Saturation Level
    // ═════════════════════════════════════════════════════════════════════════

    public VitalsPage tapOxygenSaturationLevel() {
        log.info("Tapping Oxygen Saturation Level card");
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().resourceId(\"com.fluenthealth.app:id/titleTV\").text(\"Oxygen Saturation Level\")")).click();
        return this;
    }

    public boolean isOxygenSaturationListVisible() {
        return waitForVisible(contentRecyclerView);
    }

    public boolean isOxygenSaturationFormVisible() {
        return waitForVisible(oxygenInput);
    }

    public VitalsPage enterOxygenLevel(String value) {
        log.info("Entering oxygen saturation level: {}", value);
        tap(oxygenInput);
        oxygenInput.sendKeys(value);
        return this;
    }

    public VitalsPage clearAndEnterOxygenLevel(String value) {
        log.info("Updating oxygen saturation to: {}", value);
        tap(oxygenInput);
        clearAndType(oxygenInput, value);
        return this;
    }

    public String getOxygenSaturationCardText() {
        return getVitalListCardText();
    }

    // ═════════════════════════════════════════════════════════════════════════
    // Pulse Rate
    // ═════════════════════════════════════════════════════════════════════════

    public VitalsPage tapPulseRate() {
        log.info("Tapping Pulse Rate card");
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().resourceId(\"com.fluenthealth.app:id/titleTV\").text(\"Pulse Rate\")")).click();
        return this;
    }

    public boolean isPulseRateListVisible() {
        return waitForVisible(contentRecyclerView);
    }

    public boolean isPulseRateFormVisible() {
        return waitForVisible(pulseRateInput);
    }

    public VitalsPage enterBeatsPerMinute(String value) {
        log.info("Entering pulse rate: {}", value);
        tap(pulseRateInput);
        pulseRateInput.sendKeys(value);
        return this;
    }

    public VitalsPage clearAndEnterBeatsPerMinute(String value) {
        log.info("Updating pulse rate to: {}", value);
        tap(pulseRateInput);
        clearAndType(pulseRateInput, value);
        return this;
    }

    public String getPulseRateCardText() {
        return getVitalListCardText();
    }

    // ═════════════════════════════════════════════════════════════════════════
    // Respiratory Rate
    // ═════════════════════════════════════════════════════════════════════════

    public VitalsPage tapRespiratoryRate() {
        log.info("Tapping Respiratory Rate card");
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().resourceId(\"com.fluenthealth.app:id/titleTV\").text(\"Respiratory Rate\")")).click();
        return this;
    }

    public boolean isRespiratoryRateListVisible() {
        return waitForVisible(contentRecyclerView);
    }

    public boolean isRespiratoryRateFormVisible() {
        return waitForVisible(respiratoryRateInput);
    }

    public VitalsPage enterBreathsPerMinute(String value) {
        log.info("Entering respiratory rate: {}", value);
        tap(respiratoryRateInput);
        respiratoryRateInput.sendKeys(value);
        return this;
    }

    public VitalsPage clearAndEnterBreathsPerMinute(String value) {
        log.info("Updating respiratory rate to: {}", value);
        tap(respiratoryRateInput);
        clearAndType(respiratoryRateInput, value);
        return this;
    }

    public String getRespiratoryRateCardText() {
        return getVitalListCardText();
    }
}
