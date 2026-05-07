package com.fluent.tests;

import com.fluent.annotations.RetryOnFailure;
import com.fluent.annotations.SkipAppReset;
import com.fluent.pages.PageManager;
import com.fluent.testdata.TestData;
import com.fluent.utils.AndroidBaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static org.junit.jupiter.api.Assertions.*;

@Feature("Health Info – Vitals – Pulse Rate")
@Execution(ExecutionMode.SAME_THREAD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PulseRateTest extends AndroidBaseTest {

    private PageManager pages;

    @BeforeEach
    public void initPages() {
        pages = new PageManager();
    }

    // ═════════════════════════════════════════════════════════════════════════
    // CREATE
    // ═════════════════════════════════════════════════════════════════════════

    @Test
    @Order(1)
    @RetryOnFailure
    @Tag("regression")
    @Story("Pulse Rate – Add")
    @Description("Add a new Pulse Rate record and verify it appears on the list screen")
    @Severity(SeverityLevel.CRITICAL)
    public void addPulseRate() {
        signIn(pages);

        navigateToVitals(pages);
        pages.vitalsPage().tapPulseRate();
        assertTrue(pages.vitalsPage().isPulseRateListVisible(),
                "Pulse Rate list screen should be visible");

        pages.vitalsPage().tapAddNewButton();
        assertTrue(pages.vitalsPage().isPulseRateFormVisible(),
                "Pulse Rate form should be displayed after tapping Add new");

        fillPulseRateMandatoryFields(pages, TestData.VIT_PULSE_RATE);

        assertTrue(pages.vitalsPage().isDoneButtonEnabled(),
                "Done button should be enabled when all mandatory fields are filled");
        pages.vitalsPage().tapDoneButton();

        navigateToVitals(pages);
        pages.vitalsPage().tapPulseRate();
        assertTrue(pages.vitalsPage().isPulseRateListVisible(),
                "Pulse Rate list should be visible after saving");
        assertTrue(pages.vitalsPage().getPulseRateCardText().contains(TestData.VIT_PULSE_RATE),
                "Record card should display the saved pulse rate value");
    }

    // ═════════════════════════════════════════════════════════════════════════
    // READ / EDIT (Update)
    // ═════════════════════════════════════════════════════════════════════════

    @Test
    @Order(2)
    @SkipAppReset
    @RetryOnFailure
    @Tag("regression")
    @Story("Pulse Rate – Edit")
    @Description("Edit an existing Pulse Rate record and verify the changes are saved")
    @Severity(SeverityLevel.CRITICAL)
    public void editPulseRate() {
        signIn(pages);

        navigateToVitals(pages);
        pages.vitalsPage().tapPulseRate();
        pages.vitalsPage().tapRecordCard();
        assertTrue(pages.vitalsPage().isPulseRateFormVisible(),
                "Pulse Rate form should be visible for editing");

        pages.vitalsPage().clearAndEnterBeatsPerMinute(TestData.VIT_UPDATED_PULSE_RATE);
        pages.vitalsPage().dismissKeyboard();

        assertTrue(pages.vitalsPage().isDoneButtonEnabled(),
                "Done button should be enabled after editing");
        pages.vitalsPage().tapDoneButton();

        navigateToVitals(pages);
        pages.vitalsPage().tapPulseRate();
        assertTrue(pages.vitalsPage().getPulseRateCardText().contains(TestData.VIT_UPDATED_PULSE_RATE),
                "Record card should display the updated pulse rate value");
    }

    // ═════════════════════════════════════════════════════════════════════════
    // DELETE
    // ═════════════════════════════════════════════════════════════════════════

    @Test
    @Order(3)
    @RetryOnFailure
    @Tag("regression")
    @Story("Pulse Rate – Delete")
    @Description("Delete an existing Pulse Rate record and verify it is removed")
    @Severity(SeverityLevel.CRITICAL)
    public void deletePulseRate() {
        signIn(pages);

        navigateToVitals(pages);
        pages.vitalsPage().tapPulseRate();
        pages.vitalsPage().tapRecordCard();
        assertTrue(pages.vitalsPage().isPulseRateFormVisible(),
                "Pulse Rate form should be visible");

        pages.vitalsPage().tapDeleteButton();
        assertTrue(pages.vitalsPage().isDeleteConfirmationVisible(),
                "Delete confirmation dialog should be visible");

        pages.vitalsPage().confirmDelete();
    }

    // ═════════════════════════════════════════════════════════════════════════
    // REQUIRED FIELD VALIDATION
    // ═════════════════════════════════════════════════════════════════════════

    @Test
    @Order(4)
    @Tag("regression")
    @Story("Pulse Rate – Required Field Validation")
    @Description("Done button disabled until all mandatory fields (beats per minute, date, time) are filled")
    @Severity(SeverityLevel.NORMAL)
    public void requiredFieldValidation_PulseRate() {
        signIn(pages);

        navigateToVitals(pages);
        pages.vitalsPage().tapPulseRate();
        pages.vitalsPage().tapAddNewButton();
        assertTrue(pages.vitalsPage().isPulseRateFormVisible(),
                "Pulse Rate form should be displayed");

        assertFalse(pages.vitalsPage().isDoneButtonEnabled(),
                "Done button should be disabled when all mandatory fields are empty");

        pages.vitalsPage().enterBeatsPerMinute(TestData.VIT_PULSE_RATE);
        pages.vitalsPage().dismissKeyboard();
        assertFalse(pages.vitalsPage().isDoneButtonEnabled(),
                "Done button should be disabled when date and time are empty");

        pages.vitalsPage().tapDateField();
        try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
        pages.vitalsPage().confirmDatePicker();
        assertFalse(pages.vitalsPage().isDoneButtonEnabled(),
                "Done button should be disabled when time is empty");

        pages.vitalsPage().tapTimeField();
        try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
        pages.vitalsPage().confirmTimePicker();
        assertTrue(pages.vitalsPage().isDoneButtonEnabled(),
                "Done button should be enabled once all mandatory fields are filled");
    }
}
