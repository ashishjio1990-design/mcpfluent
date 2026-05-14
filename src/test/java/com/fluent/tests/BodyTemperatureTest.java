package com.fluent.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

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

@Feature("Health Info – Vitals – Body Temperature")
@Execution(ExecutionMode.SAME_THREAD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BodyTemperatureTest extends AndroidBaseTest {

        //New set of lines getting commited over here

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
    @Story("Body Temperature – Add")
    @Description("Add a new Body Temperature record and verify it appears on the list screen")
    @Severity(SeverityLevel.CRITICAL)
    public void addBodyTemperature() {
        signIn(pages);

        navigateToVitals(pages);
        pages.vitalsPage().tapBodyTemperature();
        // assertTrue(pages.vitalsPage().isBodyTemperatureListVisible(),
        //         "Body Temperature list screen should be visible");

     //   pages.vitalsPage().tapAddNewButton();
        assertTrue(pages.vitalsPage().isBodyTemperatureFormVisible(),
                "Body Temperature form should be displayed after tapping Add new");

        fillBodyTemperatureMandatoryFields(pages, TestData.VIT_TEMPERATURE);

        assertTrue(pages.vitalsPage().isDoneButtonEnabled(),
                "Done button should be enabled when all mandatory fields are filled");
        pages.vitalsPage().tapDoneButton();

        navigateToVitals(pages);
        pages.vitalsPage().tapBodyTemperature();
        assertTrue(pages.vitalsPage().isBodyTemperatureListVisible(),
                "Body Temperature list should be visible after saving");
        assertTrue(pages.vitalsPage().getBodyTemperatureCardText().contains(TestData.VIT_TEMPERATURE),
                "Record card should display the saved temperature value");
    }

    // ═════════════════════════════════════════════════════════════════════════
    // READ / EDIT (Update)
    // ═════════════════════════════════════════════════════════════════════════

    @Test
    @Order(2)
    @SkipAppReset
    @RetryOnFailure
    @Tag("regression")
    @Story("Body Temperature – Edit")
    @Description("Edit an existing Body Temperature record and verify the changes are saved")
    @Severity(SeverityLevel.CRITICAL)
    public void editBodyTemperature() {
        signIn(pages);

        navigateToVitals(pages);
        pages.vitalsPage().tapBodyTemperature();
        pages.vitalsPage().tapRecordCard();
        assertTrue(pages.vitalsPage().isBodyTemperatureFormVisible(),
                "Body Temperature form should be visible for editing");

        pages.vitalsPage().clearAndEnterTemperature(TestData.VIT_UPDATED_TEMPERATURE);
        pages.vitalsPage().dismissKeyboard();

        assertTrue(pages.vitalsPage().isDoneButtonEnabled(),
                "Done button should be enabled after editing");
        pages.vitalsPage().tapDoneButton();

        navigateToVitals(pages);
        pages.vitalsPage().tapBodyTemperature();
        assertTrue(pages.vitalsPage().getBodyTemperatureCardText().contains(TestData.VIT_UPDATED_TEMPERATURE),
                "Record card should display the updated temperature value");
    }

    // ═════════════════════════════════════════════════════════════════════════
    // DELETE
    // ═════════════════════════════════════════════════════════════════════════

    @Test
    @Order(3)
    @RetryOnFailure
    @Tag("regression")
    @Story("Body Temperature – Delete")
    @Description("Delete an existing Body Temperature record and verify it is removed")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteBodyTemperature() {
        signIn(pages);

        navigateToVitals(pages);
        pages.vitalsPage().tapBodyTemperature();
        pages.vitalsPage().tapRecordCard();
        assertTrue(pages.vitalsPage().isBodyTemperatureFormVisible(),
                "Body Temperature form should be visible");

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
    @Story("Body Temperature – Required Field Validation")
    @Description("Done button disabled until all mandatory fields (temperature, date, time) are filled")
    @Severity(SeverityLevel.NORMAL)
    public void requiredFieldValidation_BodyTemperature() {
        signIn(pages);

        navigateToVitals(pages);
        pages.vitalsPage().tapBodyTemperature();
        pages.vitalsPage().tapAddNewButton();
        assertTrue(pages.vitalsPage().isBodyTemperatureFormVisible(),
                "Body Temperature form should be displayed");

        assertFalse(pages.vitalsPage().isDoneButtonEnabled(),
                "Done button should be disabled when all mandatory fields are empty");

        pages.vitalsPage().enterTemperature(TestData.VIT_TEMPERATURE);
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
