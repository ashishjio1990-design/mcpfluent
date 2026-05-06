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
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static org.junit.jupiter.api.Assertions.*;

@Feature("Required Field Validation on Save")
@Execution(ExecutionMode.SAME_THREAD)
public class BasicInfoValidationTest extends AndroidBaseTest {

    private PageManager pages;

    @BeforeEach
    public void initPages() {
        pages = new PageManager();
    }

    @Test
    @Tag("regression")
    @Story("Basic Info – Emergency Contact")
    @Description("Required field validation on save (Emergency Contact)")
    @Severity(SeverityLevel.NORMAL)
    public void requiredFieldValidationOnSave_BasicInfo() {
        // Sign in
        signIn(pages);
        // Navigate: My Health → Basic Info
        pages.fluentHomePage().tapMyHealthTab();
        pages.basicInfoPage().tapBasicInfoTab();

        // Open Add Emergency Contact form
        pages.basicInfoPage().scrollToEmergencyContactSection();
        pages.basicInfoPage().tapEmergencyContactAddButton();
        assertTrue(pages.basicInfoPage().isAddEmergencyContactFormVisible(),
                "Add Emergency Contact form should be displayed");

        // Verify mandatory (*) markers
        assertTrue(pages.basicInfoPage().isFirstNameFieldMandatory(), "First Name field should be marked mandatory (*)");
        assertTrue(pages.basicInfoPage().isLastNameFieldMandatory(), "Last Name field should be marked mandatory (*)");
        assertTrue(pages.basicInfoPage().isContactNumberFieldMandatory(), "Contact Number field should be marked mandatory (*)");

        // Save disabled: all fields empty
        assertFalse(pages.basicInfoPage().isECSaveButtonEnabled(),
                "Save button should be disabled when all mandatory fields are empty");

        // Save disabled: only First Name filled
        pages.basicInfoPage().enterECFirstName(TestData.EC_FIRST_NAME);
        assertFalse(pages.basicInfoPage().isECSaveButtonEnabled(),
                "Save button should be disabled when only First Name is filled");

        // Save disabled: First Name + Last Name filled
        pages.basicInfoPage().enterECLastName(TestData.EC_LAST_NAME);
        assertFalse(pages.basicInfoPage().isECSaveButtonEnabled(),
                "Save button should be disabled when Contact Number is empty");

        // Save enabled: all mandatory fields filled
        pages.basicInfoPage().enterECContactNumber(TestData.EC_CONTACT_NUMBER);
        assertTrue(pages.basicInfoPage().isECSaveButtonEnabled(),
                "Save button should be enabled once all mandatory fields are filled");
        signOut(pages);
        }

    @Test
    @RetryOnFailure
    @Tag("regression")
    @Story("Basic Info – Alternative Medical Decision Maker")
    @Description("Required field validation on save (AMD)")
    @Severity(SeverityLevel.NORMAL)
    public void requiredFieldValidationOnSave_AMD() {
        // Sign in
          signIn(pages);
        // Navigate: My Health → Basic Info
        pages.fluentHomePage().tapMyHealthTab();
        pages.basicInfoPage().tapBasicInfoTab();

        // Open Add AMD form
        pages.basicInfoPage().scrollToAMDSection();
        pages.basicInfoPage().tapAMDAddButton();
        assertTrue(pages.basicInfoPage().isAddAMDFormVisible(), "Add AMD form should be displayed");

        // Save disabled: all fields empty
        assertFalse(pages.basicInfoPage().isAMDSaveButtonEnabled(),
                "Save button should be disabled when all mandatory fields are empty");

        // Save disabled: only First Name filled
        pages.basicInfoPage().enterAMDFirstName(TestData.AMD_FIRST_NAME);
        assertFalse(pages.basicInfoPage().isAMDSaveButtonEnabled(),
                "Save button should be disabled when only First Name is filled");

        // Save disabled: First Name + Last Name filled
        pages.basicInfoPage().enterAMDLName(TestData.AMD_LAST_NAME);
        assertFalse(pages.basicInfoPage().isAMDSaveButtonEnabled(),
                "Save button should be disabled when Contact Number is empty");

        // Save enabled: all mandatory fields filled
        pages.basicInfoPage().enterAMDContactNumber(TestData.AMD_CONTACT_NUMBER);
        assertTrue(pages.basicInfoPage().isAMDSaveButtonEnabled(),
                "Save button should be enabled once all mandatory fields are filled");
        signOut(pages);
    }

    @Test
    @Tag("regression")
    @Story("Basic Info – Health Insurance")
    @Description("Required field validation on save (Health Insurance)")
    @Severity(SeverityLevel.NORMAL)
    public void requiredFieldValidationOnSave_HealthInsurance() {
        // Sign in
         signIn(pages);
        // Navigate: My Health → Basic Info
        pages.fluentHomePage().tapMyHealthTab();
        pages.basicInfoPage().tapBasicInfoTab();

        // Open Add Health Insurance form
        pages.basicInfoPage().scrollToHealthInsuranceSection();
        pages.basicInfoPage().tapHIAddButton();
        assertTrue(pages.basicInfoPage().isAddHIFormVisible(), "Add Health Insurance form should be displayed");

        // Save disabled: all fields empty
        assertFalse(pages.basicInfoPage().isHISaveButtonEnabled(),
                "Save button should be disabled when all mandatory fields are empty");

        // Fill mandatory fields one by one
        pages.basicInfoPage().tapHIInsuranceNameInput();
        pages.basicInfoPage().tapHIContactNumber();
        pages.basicInfoPage().enterHIPolicyNumber(TestData.HI_POLICY_NUMBER);

        // Save enabled: all mandatory fields filled
        assertTrue(pages.basicInfoPage().isHISaveButtonEnabled(),
                "Save button should be enabled once all mandatory fields are filled");
        signOut(pages);
    }
}
