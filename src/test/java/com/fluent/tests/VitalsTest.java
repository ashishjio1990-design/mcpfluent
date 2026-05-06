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

@Feature("Health Info – Vitals")
@Execution(ExecutionMode.SAME_THREAD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VitalsTest extends AndroidBaseTest {

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
    @Story("Vitals – Add Blood Pressure")
    @Description("Add a new Blood Pressure vital record and verify it is saved successfully")
    @Severity(SeverityLevel.CRITICAL)
    public void addVital() {
        signIn(pages);

        navigateToVitals(pages);
       
        assertTrue(pages.vitalsPage().isBloodPressureCardPresent(),
                "Blood Pressure card should be present on the Vitals screen");

        pages.vitalsPage().tapBloodPressure();
        assertTrue(pages.vitalsPage().isFormVisible(),
                "Blood Pressure form should be displayed after tapping the card");

        fillBloodPressureMandatoryFields(pages, TestData.VIT_SYSTOLIC, TestData.VIT_DIASTOLIC);

        assertTrue(pages.vitalsPage().isDoneButtonEnabled(),
                "Done button should be enabled when all mandatory fields are filled");
        pages.vitalsPage().tapDoneButton();

        navigateToVitals(pages);
      

        pages.vitalsPage().tapBloodPressure();

        assertTrue(pages.vitalsPage().isBloodPressureListVisible(),
                "Blood Pressure list screen should be visible after tapping the card");
    }

    // ═════════════════════════════════════════════════════════════════════════
    // READ / EDIT (Update)
    // ═════════════════════════════════════════════════════════════════════════

    @Test
    @Order(2)
    @SkipAppReset
    @RetryOnFailure
    @Tag("regression")
    @Story("Vitals – Edit Blood Pressure")
    @Description("Edit an existing Blood Pressure vital record and verify the changes are saved")
    @Severity(SeverityLevel.CRITICAL)
    public void editVital() {
        signIn(pages);

        navigateToVitals(pages);
        pages.vitalsPage().tapBloodPressure();
        pages.vitalsPage().tapRecordCard();
        assertTrue(pages.vitalsPage().isFormVisible(),
                "Blood Pressure form should be visible for editing");

        pages.vitalsPage().clearAndEnterSystolic(TestData.VIT_UPDATED_SYSTOLIC);
        pages.vitalsPage().clearAndEnterDiastolic(TestData.VIT_UPDATED_DIASTOLIC);
        pages.vitalsPage().dismissKeyboard();
        assertTrue(pages.vitalsPage().isDoneButtonEnabled(),
                "Done button should be enabled after editing");
        pages.vitalsPage().tapDoneButton();

        navigateToVitals(pages);
        pages.vitalsPage().tapBloodPressure();
        assertTrue(pages.vitalsPage().getBloodPressureCardText().contains(TestData.VIT_UPDATED_SYSTOLIC),
                "Blood Pressure record card should display the updated systolic value");
    }

    // ═════════════════════════════════════════════════════════════════════════
    // DELETE
    // ═════════════════════════════════════════════════════════════════════════

    @Test
    @Order(3)
    @RetryOnFailure
    @Tag("regression")
    @Story("Vitals – Delete Blood Pressure")
    @Description("Delete an existing Blood Pressure vital record and verify it is removed")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteVital() {
        signIn(pages);

        navigateToVitals(pages);
        pages.vitalsPage().tapBloodPressure();
         assertTrue(pages.vitalsPage().isBloodPressureListVisible(),
                "Blood Pressure list screen should be visible after tapping the card");
        pages.vitalsPage().tapRecordCard();
        assertTrue(pages.vitalsPage().isFormVisible(),
                "Blood Pressure form should be visible");

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
    @Story("Vitals – Required Field Validation")
    @Description("Done button disabled until all mandatory fields (systolic, diastolic, date, time) are filled")
    @Severity(SeverityLevel.NORMAL)
    public void requiredFieldValidation_Vital() {
        signIn(pages);

        navigateToVitals(pages);
        pages.vitalsPage().tapBloodPressure();
        assertTrue(pages.vitalsPage().isFormVisible(),
                "Blood Pressure form should be displayed");

        assertFalse(pages.vitalsPage().isDoneButtonEnabled(),
                "Done button should be disabled when all mandatory fields are empty");

        pages.vitalsPage().enterSystolic(TestData.VIT_SYSTOLIC);
        pages.vitalsPage().dismissKeyboard();
        assertFalse(pages.vitalsPage().isDoneButtonEnabled(),
                "Done button should be disabled when diastolic and date/time are empty");

        pages.vitalsPage().enterDiastolic(TestData.VIT_DIASTOLIC);
        pages.vitalsPage().dismissKeyboard();
        assertFalse(pages.vitalsPage().isDoneButtonEnabled(),
                "Done button should be disabled when date and time are empty");

        pages.vitalsPage().tapDateField();
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
        pages.vitalsPage().confirmDatePicker();
        assertFalse(pages.vitalsPage().isDoneButtonEnabled(),
                "Done button should be disabled when time is empty");

        pages.vitalsPage().tapTimeField();
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
        pages.vitalsPage().confirmTimePicker();
        assertTrue(pages.vitalsPage().isDoneButtonEnabled(),
                "Done button should be enabled once all mandatory fields are filled");
    }
}
