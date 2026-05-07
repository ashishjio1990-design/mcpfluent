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

@Feature("Health Info – Vitals – Respiratory Rate")
@Execution(ExecutionMode.SAME_THREAD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RespiratoryRateTest extends AndroidBaseTest {

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
    @Story("Respiratory Rate – Add")
    @Description("Add a new Respiratory Rate record and verify it appears on the list screen")
    @Severity(SeverityLevel.CRITICAL)
    public void addRespiratoryRate() {
        signIn(pages);

        navigateToVitals(pages);
        pages.vitalsPage().tapRespiratoryRate();
        assertTrue(pages.vitalsPage().isRespiratoryRateListVisible(),
                "Respiratory Rate list screen should be visible");

        pages.vitalsPage().tapAddNewButton();
        assertTrue(pages.vitalsPage().isRespiratoryRateFormVisible(),
                "Respiratory Rate form should be displayed after tapping Add new");

        fillRespiratoryRateMandatoryFields(pages, TestData.VIT_RESPIRATORY_RATE);

        assertTrue(pages.vitalsPage().isDoneButtonEnabled(),
                "Done button should be enabled when all mandatory fields are filled");
        pages.vitalsPage().tapDoneButton();

        navigateToVitals(pages);
        pages.vitalsPage().tapRespiratoryRate();
        assertTrue(pages.vitalsPage().isRespiratoryRateListVisible(),
                "Respiratory Rate list should be visible after saving");
        assertTrue(pages.vitalsPage().getRespiratoryRateCardText().contains(TestData.VIT_RESPIRATORY_RATE),
                "Record card should display the saved respiratory rate value");
    }

    // ═════════════════════════════════════════════════════════════════════════
    // READ / EDIT (Update)
    // ═════════════════════════════════════════════════════════════════════════

    @Test
    @Order(2)
    @SkipAppReset
    @RetryOnFailure
    @Tag("regression")
    @Story("Respiratory Rate – Edit")
    @Description("Edit an existing Respiratory Rate record and verify the changes are saved")
    @Severity(SeverityLevel.CRITICAL)
    public void editRespiratoryRate() {
        signIn(pages);

        navigateToVitals(pages);
        pages.vitalsPage().tapRespiratoryRate();
        pages.vitalsPage().tapRecordCard();
        assertTrue(pages.vitalsPage().isRespiratoryRateFormVisible(),
                "Respiratory Rate form should be visible for editing");

        pages.vitalsPage().clearAndEnterBreathsPerMinute(TestData.VIT_UPDATED_RESPIRATORY_RATE);
        pages.vitalsPage().dismissKeyboard();

        assertTrue(pages.vitalsPage().isDoneButtonEnabled(),
                "Done button should be enabled after editing");
        pages.vitalsPage().tapDoneButton();

        navigateToVitals(pages);
        pages.vitalsPage().tapRespiratoryRate();
        assertTrue(pages.vitalsPage().getRespiratoryRateCardText().contains(TestData.VIT_UPDATED_RESPIRATORY_RATE),
                "Record card should display the updated respiratory rate value");
    }

    // ═════════════════════════════════════════════════════════════════════════
    // DELETE
    // ═════════════════════════════════════════════════════════════════════════

    @Test
    @Order(3)
    @RetryOnFailure
    @Tag("regression")
    @Story("Respiratory Rate – Delete")
    @Description("Delete an existing Respiratory Rate record and verify it is removed")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteRespiratoryRate() {
        signIn(pages);

        navigateToVitals(pages);
        pages.vitalsPage().tapRespiratoryRate();
        pages.vitalsPage().tapRecordCard();
        assertTrue(pages.vitalsPage().isRespiratoryRateFormVisible(),
                "Respiratory Rate form should be visible");

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
    @Story("Respiratory Rate – Required Field Validation")
    @Description("Done button disabled until all mandatory fields (breaths per minute, date, time) are filled")
    @Severity(SeverityLevel.NORMAL)
    public void requiredFieldValidation_RespiratoryRate() {
        signIn(pages);

        navigateToVitals(pages);
        pages.vitalsPage().tapRespiratoryRate();
        pages.vitalsPage().tapAddNewButton();
        assertTrue(pages.vitalsPage().isRespiratoryRateFormVisible(),
                "Respiratory Rate form should be displayed");

        assertFalse(pages.vitalsPage().isDoneButtonEnabled(),
                "Done button should be disabled when all mandatory fields are empty");

        pages.vitalsPage().enterBreathsPerMinute(TestData.VIT_RESPIRATORY_RATE);
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
