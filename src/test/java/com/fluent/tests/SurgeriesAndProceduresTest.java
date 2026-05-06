package com.fluent.tests;

import com.fluent.annotations.RetryOnFailure;
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

@Feature("Health Info – Surgeries and/or Procedures")
@Execution(ExecutionMode.SAME_THREAD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SurgeriesAndProceduresTest extends AndroidBaseTest {

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
    @Story("Surgeries and/or Procedures – Add")
    @Description("Add a new surgery record and verify it appears on the list screen")
    @Severity(SeverityLevel.CRITICAL)
    public void addSurgeryAndProcedure() {
        signIn(pages);

        navigateToSurgeries(pages);
        assertTrue(pages.surgeriesAndProceduresPage().isAddFormVisible(),
                "Add form should be displayed on first entry (empty state)");

        fillSurgeryMandatoryFields(pages, TestData.SP_SURGERY_NAME, TestData.SP_STATUS);
        pages.surgeriesAndProceduresPage().enterNotes(TestData.SP_NOTES);

        assertTrue(pages.surgeriesAndProceduresPage().isDoneButtonEnabled(),
                "Done button should be enabled when all mandatory fields are filled");
        pages.surgeriesAndProceduresPage().tapDoneButton();

        navigateToSurgeries(pages);

        assertTrue(pages.surgeriesAndProceduresPage().isSurgeriesListVisible(),
                "Surgery list screen should be visible after saving");
        assertTrue(pages.surgeriesAndProceduresPage().isSurgeryCardPresent(),
                "Saved surgery card should appear on the list");
    }

    // ═════════════════════════════════════════════════════════════════════════
    // READ / EDIT (Update)
    // ═════════════════════════════════════════════════════════════════════════

    @Test
    @Order(2)
    @RetryOnFailure
    @Tag("regression")
    @Story("Surgeries and/or Procedures – Edit")
    @Description("Edit an existing surgery record and verify the changes are saved")
    @Severity(SeverityLevel.CRITICAL)
    public void editSurgeryAndProcedure() {
        assertTrue(pages.surgeriesAndProceduresPage().isSurgeryCardPresent(),
                "Surgery card must be present before editing");

        pages.surgeriesAndProceduresPage().tapSurgeryCard();

        pages.surgeriesAndProceduresPage().clearAndEnterNotes(TestData.SP_UPDATED_NOTES);

        assertTrue(pages.surgeriesAndProceduresPage().isDoneButtonEnabled(),
                "Done button should be enabled after editing");
        pages.surgeriesAndProceduresPage().tapDoneButton();

        navigateToSurgeries(pages);
        pages.surgeriesAndProceduresPage().tapSurgeryCard();
        assertTrue(pages.surgeriesAndProceduresPage().getNotesText().contains(TestData.SP_UPDATED_NOTES),
                "Notes field should contain updated notes");
    }

    // ═════════════════════════════════════════════════════════════════════════
    // DELETE
    // ═════════════════════════════════════════════════════════════════════════

    @Test
    @Order(3)
    @RetryOnFailure
    @Tag("regression")
    @Story("Surgeries and/or Procedures – Delete")
    @Description("Delete an existing surgery record and verify it is removed from the list")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteSurgeryAndProcedure() {
        signIn(pages);

        navigateToSurgeries(pages);

        pages.surgeriesAndProceduresPage().tapSurgeryCard();
        pages.surgeriesAndProceduresPage().tapDeleteButton();

        assertTrue(pages.surgeriesAndProceduresPage().isDeleteConfirmationVisible(),
                "Delete confirmation dialog should be visible");

        pages.surgeriesAndProceduresPage().confirmDelete();
    }

    // ═════════════════════════════════════════════════════════════════════════
    // REQUIRED FIELD VALIDATION
    // ═════════════════════════════════════════════════════════════════════════

    @Test
    @Order(4)
    @Tag("regression")
    @Story("Surgeries and/or Procedures – Required Field Validation")
    @Description("Done button disabled until all mandatory fields (surgery, status, date) are filled")
    @Severity(SeverityLevel.NORMAL)
    public void requiredFieldValidation_SurgeryAndProcedure() {
        signIn(pages);

        navigateToSurgeries(pages);
        assertTrue(pages.surgeriesAndProceduresPage().isAddFormVisible(),
                "Add form should be displayed");

        assertFalse(pages.surgeriesAndProceduresPage().isDoneButtonEnabled(),
                "Done button should be disabled when all mandatory fields are empty");

        pages.surgeriesAndProceduresPage().tapSurgerySearchField();
        pages.surgeriesAndProceduresPage().selectSurgeryFromCommonList(TestData.SP_SURGERY_NAME);
        assertFalse(pages.surgeriesAndProceduresPage().isDoneButtonEnabled(),
                "Done button should be disabled when status and date are empty");

        pages.surgeriesAndProceduresPage().tapStatusField();
        pages.surgeriesAndProceduresPage().tapDateField();
        assertFalse(pages.surgeriesAndProceduresPage().isDoneButtonEnabled(),
                "Done button should be disabled when date is empty");

        pages.surgeriesAndProceduresPage().tapDateField();
        pages.surgeriesAndProceduresPage().confirmDatePicker();
        assertTrue(pages.surgeriesAndProceduresPage().isDoneButtonEnabled(),
                "Done button should be enabled once all mandatory fields are filled");
    }
}
