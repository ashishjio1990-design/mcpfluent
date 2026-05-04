package com.fluent.tests;

import com.fluent.pages.PageManager;
import com.fluent.testdata.TestData;
import com.fluent.utils.AndroidBaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import com.fluent.annotations.RetryOnFailure;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static org.junit.jupiter.api.Assertions.*;

@Feature("Sign In")
@Execution(ExecutionMode.SAME_THREAD)
public class SignInTest extends AndroidBaseTest {

    private PageManager pages;

    @BeforeEach
    public void initPages() {
        pages = new PageManager();
    }

    @Test
    @Tag("regression")
    @Story("Existing user sign in")
    @Description("Successful login with valid credentials")
    @Severity(SeverityLevel.CRITICAL)
    public void testExistingUserSignIn() throws InterruptedException {
        // Step 1: Welcome screen → tap Log in
        assertTrue(pages.createAccountPage().isDisplayed(), "Welcome screen should be displayed");
        pages.createAccountPage().tapLogIn();

        // Step 2: Sign In screen
        assertTrue(pages.loginPage().isDisplayed(), "Sign In screen should be displayed");
        assertFalse(pages.loginPage().isContinueButtonEnabled(),
                "Continue button should be disabled before entering phone number");

        pages.loginPage().enterPhoneNumber(TestData.SIGN_IN_MOBILE);

        assertTrue(pages.loginPage().isContinueButtonEnabled(),
                "Continue button should be enabled after entering phone number");
        pages.loginPage().tapContinue();

        // Step 4: Enter PIN (existing user)
        assertTrue(pages.enterPinPage().isDisplayed(), "Enter PIN screen should be displayed");
        pages.enterPinPage().enterPin(TestData.PIN);
        // Step 5: Home page
        assertTrue(pages.fluentHomePage().isDisplayed(), "Home page should be displayed after sign-in");

    }

   

@Test
    @RetryOnFailure
    @Tag("smoke")
    @Tag("regression")
    @Story("Existing user sign in")
    @Description("Login with incorrect PIN shows error")
    @Severity(SeverityLevel.CRITICAL)
    public void testExistingUserSignIn3() throws InterruptedException {
        // Step 1: Welcome screen → tap Log in
        assertTrue(pages.createAccountPage().isDisplayed(), "Welcome screen should be displayed");
        pages.createAccountPage().tapLogIn();

        // Step 2: Sign In screen
        assertTrue(pages.loginPage().isDisplayed(), "Sign In screen should be displayed");
        assertFalse(pages.loginPage().isContinueButtonEnabled(),
                "Continue button should be disabled before entering phone number");

        pages.loginPage().enterPhoneNumber(TestData.SIGN_IN_MOBILE);

        assertTrue(pages.loginPage().isContinueButtonEnabled(),
                "Continue button should be enabled after entering phone number");
        pages.loginPage().tapContinue();

        // Step 4: Enter wrong PIN
        assertTrue(pages.enterPinPage().isDisplayed(), "Enter PIN screen should be displayed");
        pages.enterPinPage().enterPin("000001");

        // Step 5: Error message should appear
        assertTrue(pages.enterPinPage().isErrorDisplayed(), "Error message should be displayed for incorrect PIN");
        String errorText = pages.enterPinPage().getErrorMessage();
        assertFalse(errorText.isEmpty(), "Error message text should not be empty");
        assertTrue(errorText.contains("Invalid PIN"), "Error should mention invalid PIN: " + errorText);

    }

    @Test
    @Tag("regression")
    @Story("Basic Info – Emergency Contact")
    @Description("Emergency Contact CRUD with mandatory field (*) validation")
    @Severity(SeverityLevel.NORMAL)
    public void basicinfo_field_validation() {
        // ── Sign in ──────────────────────────────────────────────────────────
        assertTrue(pages.createAccountPage().isDisplayed(), "Welcome screen should be displayed");
        pages.createAccountPage().tapLogIn();

        assertTrue(pages.loginPage().isDisplayed(), "Sign In screen should be displayed");
        assertFalse(pages.loginPage().isContinueButtonEnabled(),
                "Continue button should be disabled before entering phone number");
        pages.loginPage().enterPhoneNumber(TestData.SIGN_IN_MOBILE);
        assertTrue(pages.loginPage().isContinueButtonEnabled(),
                "Continue button should be enabled after entering phone number");
        pages.loginPage().tapContinue();

        assertTrue(pages.enterPinPage().isDisplayed(), "Enter PIN screen should be displayed");
        pages.enterPinPage().enterPin(TestData.PIN);
        assertTrue(pages.fluentHomePage().isDisplayed(), "Home page should be displayed after sign-in");

        // ── Navigate: My Health → Basic Info ─────────────────────────────────
        pages.fluentHomePage().tapMyHealthTab();
        pages.basicInfoPage().tapBasicInfoTab();

        // ── Scroll to Emergency Contact; clean up any pre-existing entry ──────
        pages.basicInfoPage().scrollToEmergencyContactSection();
        if (pages.basicInfoPage().isEmergencyContactPresent()) {
            pages.basicInfoPage().tapEditEmergencyContact();
            pages.basicInfoPage().tapDeleteECButton();
            assertTrue(pages.basicInfoPage().isDeleteConfirmationDialogVisible(),
                    "Delete confirmation dialog should appear during cleanup");
            pages.basicInfoPage().confirmDeleteEC();
            assertFalse(pages.basicInfoPage().isEmergencyContactPresent(),
                    "Pre-existing emergency contact should be removed before test");
        }

        // ── Open Add form ─────────────────────────────────────────────────────
        pages.basicInfoPage().tapEmergencyContactAddButton();
        assertTrue(pages.basicInfoPage().isAddEmergencyContactFormVisible(),
                "Add Emergency Contact form should be displayed");

        // ── Verify * fields are marked as mandatory ───────────────────────────
        assertTrue(pages.basicInfoPage().isFirstNameFieldMandatory(),
                "First name field should be marked mandatory (*)");
        assertTrue(pages.basicInfoPage().isLastNameFieldMandatory(),
                "Last name field should be marked mandatory (*)");
        assertTrue(pages.basicInfoPage().isContactNumberFieldMandatory(),
                "Contact number field should be marked mandatory (*)");

        // ── Save button disabled: all mandatory fields empty ──────────────────
        assertFalse(pages.basicInfoPage().isECSaveButtonEnabled(),
                "Save button should be disabled when all mandatory fields are empty");

        // ── Save button disabled: only First name* filled ─────────────────────
        pages.basicInfoPage().enterECFirstName(TestData.EC_FIRST_NAME);
        assertFalse(pages.basicInfoPage().isECSaveButtonEnabled(),
                "Save button should remain disabled when Last name* and Contact number* are empty");

        // ── Save button disabled: First name* + Last name* filled ─────────────
        pages.basicInfoPage().enterECLastName(TestData.EC_LAST_NAME);
        assertFalse(pages.basicInfoPage().isECSaveButtonEnabled(),
                "Save button should remain disabled when Contact number* is empty");

        // ── Save button enabled: all mandatory fields filled ──────────────────
        pages.basicInfoPage().enterECContactNumber(TestData.EC_CONTACT_NUMBER);
        assertTrue(pages.basicInfoPage().isECSaveButtonEnabled(),
                "Save button should be enabled once all mandatory (*) fields are filled");

        // ── CREATE: select optional Relationship, then Save ───────────────────
        //pages.basicInfoPage().selectECRelationship(TestData.EC_RELATIONSHIP);
        pages.basicInfoPage().tapSaveECForm();

        // ── READ: verify saved contact is displayed correctly ─────────────────
        assertFalse(pages.basicInfoPage().isAddEmergencyContactFormVisible(),
                "Form should close after all mandatory fields are filled and saved");
        assertTrue(pages.basicInfoPage().isEmergencyContactPresent(),
                "Saved emergency contact card should be visible");
        assertEquals(TestData.EC_FIRST_NAME + " " + TestData.EC_LAST_NAME,
                pages.basicInfoPage().getECSavedName(),
                "Saved contact name should match entered values");
        assertTrue(pages.basicInfoPage().getECSavedNumber().contains(TestData.EC_CONTACT_NUMBER),
                "Saved contact number should contain entered value");
        // assertEquals(TestData.EC_RELATIONSHIP,
        //         pages.basicInfoPage().getECSavedRelationship(),
        //         "Saved relationship should match selected value");

        // ── UPDATE: open Edit, change all fields, Save ────────────────────────
        pages.basicInfoPage().tapEditEmergencyContact();
        assertTrue(pages.basicInfoPage().isEditEmergencyContactFormVisible(),
                "Edit Emergency Contact form should be displayed");

        pages.basicInfoPage().clearAndEnterECFirstName(TestData.EC_UPDATED_FIRST_NAME);
        pages.basicInfoPage().clearAndEnterECLastName(TestData.EC_UPDATED_LAST_NAME);
        pages.basicInfoPage().clearAndEnterECContactNumber(TestData.EC_UPDATED_CONTACT);
        pages.basicInfoPage().tapSaveECForm();

        assertTrue(pages.basicInfoPage().isEmergencyContactPresent(),
                "Updated emergency contact should still be visible");
        assertEquals(TestData.EC_UPDATED_FIRST_NAME + " " + TestData.EC_UPDATED_LAST_NAME,
                pages.basicInfoPage().getECSavedName(),
                "Contact name should reflect updated values");
        assertTrue(pages.basicInfoPage().getECSavedNumber().contains(TestData.EC_UPDATED_CONTACT),
                "Contact number should reflect updated value");

        // ── DELETE: open Edit, tap delete icon, confirm in dialog ────────────
        pages.basicInfoPage().tapEditEmergencyContact();
        assertTrue(pages.basicInfoPage().isEditEmergencyContactFormVisible(),
                "Edit form should be open before delete");

        pages.basicInfoPage().tapDeleteECButton();
        assertTrue(pages.basicInfoPage().isDeleteConfirmationDialogVisible(),
                "Delete confirmation dialog should appear");
        pages.basicInfoPage().confirmDeleteEC();

        assertFalse(pages.basicInfoPage().isEmergencyContactPresent(),
                "Emergency contact should be removed after confirming delete");
    }

@Test
    @RetryOnFailure
    @Tag("regression")
    @Story("Basic Info Alternative Medical Decision Maker")
    @Description("Alternative Medical Decision Maker CRUD with mandatory field (*) validation")
    @Severity(SeverityLevel.NORMAL)
    public void amd_field_validation() {
        // // ── Sign in ──────────────────────────────────────────────────────────
        assertTrue(pages.createAccountPage().isDisplayed(), "Welcome screen should be displayed");
        pages.createAccountPage().tapLogIn();

        assertTrue(pages.loginPage().isDisplayed(), "Sign In screen should be displayed");
        assertFalse(pages.loginPage().isContinueButtonEnabled(),
                "Continue button should be disabled before entering phone number");
        pages.loginPage().enterPhoneNumber(TestData.SIGN_IN_MOBILE);
        assertTrue(pages.loginPage().isContinueButtonEnabled(),
                "Continue button should be enabled after entering phone number");
        pages.loginPage().tapContinue();

        assertTrue(pages.enterPinPage().isDisplayed(), "Enter PIN screen should be displayed");
        pages.enterPinPage().enterPin(TestData.PIN);
        assertTrue(pages.fluentHomePage().isDisplayed(), "Home page should be displayed after sign-in");

        // ── Navigate: My Health → Basic Info ─────────────────────────────────
        pages.fluentHomePage().tapMyHealthTab();
        pages.basicInfoPage().tapBasicInfoTab();

        // ── Scroll to AMD; clean up pre-existing ──────────────────────────────
        pages.basicInfoPage().scrollToAMDSection();
        if (pages.basicInfoPage().isAMDPresent()) {
            pages.basicInfoPage().tapEditAMD();
            pages.basicInfoPage().tapDeleteAMDButton();
            assertTrue(pages.basicInfoPage().isDeleteConfirmationDialogVisible(), "Delete dialog should appear");
            pages.basicInfoPage().confirmDeleteAMD();
            assertFalse(pages.basicInfoPage().isAMDPresent(), "Pre-existing AMD should be removed");
        }

        // ── Open Add form ─────────────────────────────────────────────────────
        pages.basicInfoPage().tapAMDAddButton();
        assertTrue(pages.basicInfoPage().isAddAMDFormVisible(), "Add AMD form should be displayed");



        // ── Save button states ────────────────────────────────────────────────
        assertFalse(pages.basicInfoPage().isAMDSaveButtonEnabled(), "Save disabled - empty fields");

        pages.basicInfoPage().enterAMDFirstName(TestData.AMD_FIRST_NAME);
        assertFalse(pages.basicInfoPage().isAMDSaveButtonEnabled(), "Save disabled - missing last/contact");

        pages.basicInfoPage().enterAMDLName(TestData.AMD_LAST_NAME);
        assertFalse(pages.basicInfoPage().isAMDSaveButtonEnabled(), "Save disabled - missing contact");

        pages.basicInfoPage().enterAMDContactNumber(TestData.AMD_CONTACT_NUMBER);
        assertTrue(pages.basicInfoPage().isAMDSaveButtonEnabled(), "Save enabled - all mandatory filled");

        // ── CREATE ────────────────────────────────────────────────────────────
        pages.basicInfoPage().tapSaveAMDForm();

        assertFalse(pages.basicInfoPage().isAddAMDFormVisible(), "Form closed after save");
        assertTrue(pages.basicInfoPage().isAMDPresent(), "AMD card visible");
        assertEquals(TestData.AMD_FIRST_NAME + " " + TestData.AMD_LAST_NAME, pages.basicInfoPage().getAMDSavedName(), "Name matches");
        assertTrue(pages.basicInfoPage().getAMDSavedNumber().contains(TestData.AMD_CONTACT_NUMBER), "Contact matches");

        // ── UPDATE ────────────────────────────────────────────────────────────
        pages.basicInfoPage().tapEditAMD();
        assertTrue(pages.basicInfoPage().isEditAMDFormVisible(), "Edit form visible");

        pages.basicInfoPage().clearAndEnterAMDFirstName(TestData.AMD_UPDATED_FIRST_NAME);
        pages.basicInfoPage().clearAndEnterAMDLName(TestData.AMD_UPDATED_LAST_NAME);
        pages.basicInfoPage().clearAndEnterAMDContactNumber(TestData.AMD_UPDATED_CONTACT);
        pages.basicInfoPage().tapSaveAMDForm();

        assertEquals(TestData.AMD_UPDATED_FIRST_NAME + " " + TestData.AMD_UPDATED_LAST_NAME, pages.basicInfoPage().getAMDSavedName(), "Updated name matches");
        assertTrue(pages.basicInfoPage().getAMDSavedNumber().contains(TestData.AMD_UPDATED_CONTACT), "Updated contact matches");

        // ── DELETE ────────────────────────────────────────────────────────────
        pages.basicInfoPage().tapEditAMD();
        pages.basicInfoPage().tapDeleteAMDButton();
        assertTrue(pages.basicInfoPage().isDeleteConfirmationDialogVisible(), "Delete dialog visible");
        pages.basicInfoPage().confirmDeleteAMD();

        assertFalse(pages.basicInfoPage().isAMDPresent(), "AMD removed after delete");
    }

    @Test
    @Tag("regression")
    @Story("Basic Info – Health Insurance")
    @Description("Health Insurance CRUD with mandatory field (*) validation")
    @Severity(SeverityLevel.NORMAL)
    public void healthInsurance_field_validation() {
      //  ── Navigate: My Health → Basic Info ─────────────────────────────────
        pages.fluentHomePage().tapMyHealthTab();
        pages.basicInfoPage().tapBasicInfoTab();

        // ── Scroll to HI; clean up pre-existing ──────────────────────────────
        pages.basicInfoPage().scrollToHealthInsuranceSection();
        if (pages.basicInfoPage().isHIPresent()) {
            pages.basicInfoPage().tapEditHI();
            pages.basicInfoPage().tapDeleteHIButton();
            assertTrue(pages.basicInfoPage().isDeleteConfirmationDialogVisible(), "Delete dialog should appear");
            pages.basicInfoPage().confirmDeleteAMD();  // reuse confirmDeleteAMD since same dialog
            assertFalse(pages.basicInfoPage().isHIPresent(), "Pre-existing HI should be removed");
        }

        // ── Open Add form ─────────────────────────────────────────────────────
        pages.basicInfoPage().tapHIAddButton();
        assertTrue(pages.basicInfoPage().isAddHIFormVisible(), "Add HI form should be displayed");

     

        // ── Save button states ────────────────────────────────────────────────
        assertFalse(pages.basicInfoPage().isHISaveButtonEnabled(), "Save disabled - empty fields");

        pages.basicInfoPage().tapHIInsuranceNameInput();
        pages.basicInfoPage().tapHIContactNumber();
        pages.basicInfoPage().enterHIPolicyNumber(TestData.HI_POLICY_NUMBER);
        assertTrue(pages.basicInfoPage().isHISaveButtonEnabled(), "Save enabled - all mandatory filled");

        // ── CREATE ────────────────────────────────────────────────────────────
        pages.basicInfoPage().tapSaveHIForm();
        pages.basicInfoPage().scrollDown();
        assertFalse(pages.basicInfoPage().isAddHIFormVisible(), "Form closed after save");
        pages.basicInfoPage().waitForHICard();
        assertTrue(pages.basicInfoPage().isHIPresent(), "HI card visible");
   
        // ── UPDATE ────────────────────────────────────────────────────────────
        pages.basicInfoPage().tapEditHI();
        assertTrue(pages.basicInfoPage().isEditHIFormVisible(), "Edit form visible");

   
        pages.basicInfoPage().clearAndEnterHIPolicyNumber(TestData.HI_UPDATED_POLICY_NUMBER);
   
        pages.basicInfoPage().tapSaveHIForm();

        // ── DELETE ────────────────────────────────────────────────────────────
        pages.basicInfoPage().tapEditHI();
        pages.basicInfoPage().tapDeleteHIButton();
        assertTrue(pages.basicInfoPage().isDeleteConfirmationDialogVisible(), "Delete dialog visible");
        pages.basicInfoPage().confirmDeleteAMD();  // reuse same dialog

        assertFalse(pages.basicInfoPage().isHIPresent(), "HI removed after delete");
    }
}

