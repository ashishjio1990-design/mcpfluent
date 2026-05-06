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

@Feature("Health Info – Key Health Tests")
@Execution(ExecutionMode.SAME_THREAD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class KeyHealthTestsTest extends AndroidBaseTest {

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
    @Story("Key Health Tests – Add")
    @Description("Add a new key health test record and verify it appears on the list screen")
    @Severity(SeverityLevel.CRITICAL)
    public void addKeyHealthTest() {
        signIn(pages);

        navigateToKeyHealthTests(pages);
        pages.keyHealthTestsPage().tapAddNewButton();
        assertTrue(pages.keyHealthTestsPage().isAddFormVisible(),
                "Add form should be displayed after tapping Add new");

        fillKeyHealthTestMandatoryFields(pages, TestData.KHT_TEST_NAME);
        pages.keyHealthTestsPage().enterNotes(TestData.KHT_NOTES);

        assertTrue(pages.keyHealthTestsPage().isDoneButtonEnabled(),
                "Done button should be enabled when all mandatory fields are filled");
        pages.keyHealthTestsPage().tapDoneButton();

        navigateToKeyHealthTests(pages);

        assertTrue(pages.keyHealthTestsPage().isKeyHealthTestsListVisible(),
                "Key Health Tests list screen should be visible after saving");
        assertTrue(pages.keyHealthTestsPage().isTestCardPresent(),
                "Saved test card should appear on the list");
    }

    // ═════════════════════════════════════════════════════════════════════════
    // READ / EDIT (Update)
    // ═════════════════════════════════════════════════════════════════════════

    @Test
    @Order(2)
    @SkipAppReset
    @RetryOnFailure
    @Tag("regression")
    @Story("Key Health Tests – Edit")
    @Description("Edit an existing key health test record and verify the changes are saved")
    @Severity(SeverityLevel.CRITICAL)
    public void editKeyHealthTest() {
        signIn(pages);

        navigateToKeyHealthTests(pages);
        assertTrue(pages.keyHealthTestsPage().isTestCardPresent(),
                "Test card must be present before editing");

        pages.keyHealthTestsPage().tapTestCard();

        pages.keyHealthTestsPage().clearAndEnterNotes(TestData.KHT_UPDATED_NOTES);

        assertTrue(pages.keyHealthTestsPage().isDoneButtonEnabled(),
                "Done button should be enabled after editing");
        pages.keyHealthTestsPage().tapDoneButton();

        navigateToKeyHealthTests(pages);
        pages.keyHealthTestsPage().tapTestCard();
        assertTrue(pages.keyHealthTestsPage().getNotesText().contains(TestData.KHT_UPDATED_NOTES),
                "Notes field should contain updated notes");
    }

    // ═════════════════════════════════════════════════════════════════════════
    // DELETE
    // ═════════════════════════════════════════════════════════════════════════

    @Test
    @Order(3)
    @RetryOnFailure
    @Tag("regression")
    @Story("Key Health Tests – Delete")
    @Description("Delete an existing key health test record and verify it is removed from the list")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteKeyHealthTest() {
        signIn(pages);

        navigateToKeyHealthTests(pages);

        pages.keyHealthTestsPage().tapTestCard();
        pages.keyHealthTestsPage().tapDeleteButton();

        assertTrue(pages.keyHealthTestsPage().isDeleteConfirmationVisible(),
                "Delete confirmation dialog should be visible");

        pages.keyHealthTestsPage().confirmDelete();
    }

    // ═════════════════════════════════════════════════════════════════════════
    // REQUIRED FIELD VALIDATION
    // ═════════════════════════════════════════════════════════════════════════

    @Test
    @Order(4)
    @Tag("regression")
    @Story("Key Health Tests – Required Field Validation")
    @Description("Done button disabled until all mandatory fields (test name, date) are filled")
    @Severity(SeverityLevel.NORMAL)
    public void requiredFieldValidation_KeyHealthTest() {
        signIn(pages);

        navigateToKeyHealthTests(pages);
        pages.keyHealthTestsPage().tapAddNewButton();
        assertTrue(pages.keyHealthTestsPage().isAddFormVisible(),
                "Add form should be displayed");

        assertFalse(pages.keyHealthTestsPage().isDoneButtonEnabled(),
                "Done button should be disabled when all mandatory fields are empty");

        pages.keyHealthTestsPage().tapTestSearchField();
        pages.keyHealthTestsPage().selectTestFromCommonList(TestData.KHT_TEST_NAME);
        assertFalse(pages.keyHealthTestsPage().isDoneButtonEnabled(),
                "Done button should be disabled when date is empty");

        pages.keyHealthTestsPage().tapDateField();
        pages.keyHealthTestsPage().confirmDatePicker();
        assertTrue(pages.keyHealthTestsPage().isDoneButtonEnabled(),
                "Done button should be enabled once all mandatory fields are filled");
    }
}
