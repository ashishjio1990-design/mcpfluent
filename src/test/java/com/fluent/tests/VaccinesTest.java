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

@Feature("Health Info – Vaccines")
@Execution(ExecutionMode.SAME_THREAD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VaccinesTest extends AndroidBaseTest {

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
    @Story("Vaccines – Add")
    @Description("Add a new vaccine record and verify it appears on the list screen")
    @Severity(SeverityLevel.CRITICAL)
    public void addVaccine() {
        signIn(pages);

        navigateToVaccines(pages);
        pages.vaccinesPage().tapAddNewButton();
        assertTrue(pages.vaccinesPage().isAddFormVisible(),
                "Add form should be displayed after tapping Add new");

        fillVaccineMandatoryFields(pages, TestData.VAX_VACCINE_NAME, TestData.VAX_DOSE);
        pages.vaccinesPage().enterNotes(TestData.VAX_NOTES);

        assertTrue(pages.vaccinesPage().isDoneButtonEnabled(),
                "Done button should be enabled when all mandatory fields are filled");
        pages.vaccinesPage().tapDoneButton();

        navigateToVaccines(pages);

        assertTrue(pages.vaccinesPage().isVaccinesListVisible(),
                "Vaccines list screen should be visible after saving");
        assertTrue(pages.vaccinesPage().isVaccineCardPresent(),
                "Saved vaccine card should appear on the list");
    }

    // ═════════════════════════════════════════════════════════════════════════
    // READ / EDIT (Update)
    // ═════════════════════════════════════════════════════════════════════════

    @Test
    @Order(2)
    @SkipAppReset
    @RetryOnFailure
    @Tag("regression")
    @Story("Vaccines – Edit")
    @Description("Edit an existing vaccine record and verify the changes are saved")
    @Severity(SeverityLevel.CRITICAL)
    public void editVaccine() {
        signIn(pages);

        navigateToVaccines(pages);
        assertTrue(pages.vaccinesPage().isVaccineCardPresent(),
                "Vaccine card must be present before editing");

        pages.vaccinesPage().tapVaccineCard();

        pages.vaccinesPage().clearAndEnterNotes(TestData.VAX_UPDATED_NOTES);

        assertTrue(pages.vaccinesPage().isDoneButtonEnabled(),
                "Done button should be enabled after editing");
        pages.vaccinesPage().tapDoneButton();

        navigateToVaccines(pages);
        pages.vaccinesPage().tapVaccineCard();
        assertTrue(pages.vaccinesPage().getNotesText().contains(TestData.VAX_UPDATED_NOTES),
                "Notes field should contain updated notes");
    }

    // ═════════════════════════════════════════════════════════════════════════
    // DELETE
    // ═════════════════════════════════════════════════════════════════════════

    @Test
    @Order(3)
    @RetryOnFailure
    @Tag("regression")
    @Story("Vaccines – Delete")
    @Description("Delete an existing vaccine record and verify it is removed from the list")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteVaccine() {
        signIn(pages);

        navigateToVaccines(pages);

        pages.vaccinesPage().tapVaccineCard();
        pages.vaccinesPage().tapDeleteButton();

        assertTrue(pages.vaccinesPage().isDeleteConfirmationVisible(),
                "Delete confirmation dialog should be visible");

        pages.vaccinesPage().confirmDelete();
    }

    // ═════════════════════════════════════════════════════════════════════════
    // REQUIRED FIELD VALIDATION
    // ═════════════════════════════════════════════════════════════════════════

    @Test
    @Order(4)
    @Tag("regression")
    @Story("Vaccines – Required Field Validation")
    @Description("Done button disabled until all mandatory fields (vaccine, dose, date) are filled")
    @Severity(SeverityLevel.NORMAL)
    public void requiredFieldValidation_Vaccine() {
        signIn(pages);

        navigateToVaccines(pages);
        pages.vaccinesPage().tapAddNewButton();
        assertTrue(pages.vaccinesPage().isAddFormVisible(),
                "Add form should be displayed");

        assertFalse(pages.vaccinesPage().isDoneButtonEnabled(),
                "Done button should be disabled when all mandatory fields are empty");

        pages.vaccinesPage().tapVaccineSearchField();
        pages.vaccinesPage().selectVaccineFromCommonList(TestData.VAX_VACCINE_NAME);
        assertFalse(pages.vaccinesPage().isDoneButtonEnabled(),
                "Done button should be disabled when dose and date are empty");

        pages.vaccinesPage().tapDoseSelectorField();
        pages.vaccinesPage().selectDose(TestData.VAX_DOSE);
        assertFalse(pages.vaccinesPage().isDoneButtonEnabled(),
                "Done button should be disabled when date is empty");

        pages.vaccinesPage().tapDateField();
        pages.vaccinesPage().confirmDatePicker();
        assertTrue(pages.vaccinesPage().isDoneButtonEnabled(),
                "Done button should be enabled once all mandatory fields are filled");
    }
}
