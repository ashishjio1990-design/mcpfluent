package com.fluent.tests;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

@Feature("Sign Up")
@Execution(ExecutionMode.SAME_THREAD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SignUpTest extends AndroidBaseTest {
        private static final Logger log = LoggerFactory.getLogger(SignUpTest.class);

        private PageManager pages;

        @BeforeEach
        public void initPages() {
                pages = new PageManager();
        }

        @Test
        @Order(1)
        @Tag("smoke")
        @Tag("regression")
        @Story("New user sign up")
        @Description("New user registration, PIN setup and profile completion")
        @Severity(SeverityLevel.CRITICAL)
        public void testNewUserSignUp() throws InterruptedException {
                // Step 1: Welcome screen
                assertTrue(pages.createAccountPage().isDisplayed(), "Welcome screen should be displayed");
                pages.createAccountPage().tapCreateAccount();

                // Step 2: Onboarding video → skip
                assertTrue(pages.onboardingVideoPage().isDisplayed(), "Onboarding video should be displayed");
                pages.onboardingVideoPage().tapSkip();

                // Step 3: Registration form
                assertTrue(pages.registrationPage().isDisplayed(), "Registration page should be displayed");
                assertFalse(pages.registrationPage().isContinueButtonEnabled(),
                                "Continue button should be disabled when all fields are empty");

                pages.registrationPage().enterFirstName(TestData.FIRST_NAME);
                assertFalse(pages.registrationPage().isContinueButtonEnabled(),
                                "Continue button should be disabled when only First Name is filled");

                pages.registrationPage().enterLastName(TestData.LAST_NAME);
                assertFalse(pages.registrationPage().isContinueButtonEnabled(),
                                "Continue button should be disabled when only First Name and Last Name are filled");

                pages.registrationPage().enterEmail(TestData.EMAIL);
                assertFalse(pages.registrationPage().isContinueButtonEnabled(),
                                "Continue button should be disabled when First Name, Last Name and Email are filled");

                pages.registrationPage().enterMobileNumber(TestData.SIGN_UP_MOBILE);
                Thread.sleep(2000);

                pages.registrationPage().scrollToContinueButton();

                assertTrue(pages.registrationPage().isContinueButtonEnabled(),
                                "Continue button should be enabled after all four fields are filled");
                pages.registrationPage().tapContinue();

                // Step 4: Handle OTP screen (may be auto verified/skipped)
                log.info("Waiting for PIN screen after registration (OTP may be auto-filled)");
                Thread.sleep(5000); // Wait for OTP handling or skip

                // Step 5: Create Fluent PIN (MPIN)
                assertTrue(pages.createFluentPin().isDisplayed(), "Create PIN screen should be displayed");
                assertFalse(pages.createFluentPin().isContinueButtonEnabled(),
                                "Continue button should be disabled before entering PIN");
                Thread.sleep(2000);
                pages.createFluentPin().enterPin(TestData.PIN);
                Thread.sleep(2000);
                pages.createFluentPin().enterConfirmPin(TestData.PIN);

                Thread.sleep(2000);
                // assertTrue(pages.createFluentPin().isContinueButtonEnabled(),
                // "Continue button should be enabled after entering PIN and confirm PIN");
                // pages.createFluentPin().tapContinue();

                // Step 6: More details
                assertTrue(pages.moreDetailsPage().isDisplayed(), "More Details screen should be displayed");

                pages.moreDetailsPage().tapDateOfBirth();
                pages.moreDetailsPage().setDateOfBirth(TestData.DOB_YEAR, TestData.DOB_DAY, TestData.DOB_MONTH);
                pages.moreDetailsPage().tapDatePickerOk();

                pages.moreDetailsPage().tapGender();
                pages.moreDetailsPage().tapSexAssignedAtBirth();
                Thread.sleep(2000);
                pages.moreDetailsPage().tapSexAssignedAtBirth();
                pages.moreDetailsPage().tapWhyWeAsk();
                pages.moreDetailsPage().tapFinishSetup();
                Thread.sleep(2000);

                // Step 7: New user welcome screen
                assertTrue(pages.newUserWelcomeScreen().isDisplayed(), "New User Welcome screen should be displayed");
                assertTrue(pages.newUserWelcomeScreen().isExploreOptionDisplayed(), "Explore option should be visible");
                assertTrue(pages.newUserWelcomeScreen().isUploadOptionDisplayed(), "Upload option should be visible");
                pages.newUserWelcomeScreen().tapExplore();

                // Step 8: Home page
                assertTrue(pages.fluentHomePage().isDisplayed(), "Home page should be displayed after sign-up");
        }

        @Test
        @Order(2)
        @Tag("regression")
        @Story("New user sign up")
        @Description("Registration data is displayed correctly in Basic Info after sign up")
        @Severity(SeverityLevel.NORMAL)
        public void testVerifyRegistrationDataInBasicInfo() throws InterruptedException {
                // Navigate: Home → My Health → Basic Info
                pages.fluentHomePage().tapMyHealthTab();
                assertTrue(pages.basicInfoPage().isDisplayed(), "Basic Info tab should be visible");
                pages.basicInfoPage().tapBasicInfoTab();

                // Verify all 4 registration fields display correct values
                assertEquals(TestData.FIRST_NAME, pages.basicInfoPage().getFirstName(),
                                "First name should match registration input");
                assertEquals(TestData.LAST_NAME, pages.basicInfoPage().getLastName(),
                                "Last name should match registration input");
                assertEquals(TestData.EMAIL, pages.basicInfoPage().getEmail(),
                                "Email should match registration input");
                assertTrue(pages.basicInfoPage().getMobileNumber().contains(TestData.SIGN_UP_MOBILE),
                                "Mobile number should contain registration mobile");

                // Verify all 4 fields are non-editable (read-only)
                assertFalse(pages.basicInfoPage().isFirstNameEditable(), "First Name field should not be editable");
                assertFalse(pages.basicInfoPage().isLastNameEditable(), "Last Name field should not be editable");
                assertFalse(pages.basicInfoPage().isEmailEditable(), "Email field should not be editable");
                assertFalse(pages.basicInfoPage().isMobileEditable(), "Mobile Number field should not be editable");

                // Clean up: delete account
                pages.fluentHomePage().tapHomeTab();
                pages.fluentHomePage().tapHomeSection1();
                assertTrue(pages.settingPage().isDisplayed(), "Settings page should be displayed");

                pages.settingPage().tapDeleteAccount();
                pages.settingPage().tapDeleteMyAccount();

                assertTrue(pages.settingPage().isDeleteDialogDisplayed(),
                                "Delete confirmation dialog should be displayed");
                pages.settingPage().tapConfirmDeleteAccount();
                Thread.sleep(2000);

                // Verify account is deleted
                assertTrue(pages.createAccountPage().isDisplayed(),
                                "Welcome screen should be displayed after account deletion");
                pages.createAccountPage().tapLogIn();
                pages.loginPage().enterPhoneNumber(TestData.SIGN_UP_MOBILE);
                pages.loginPage().tapContinue();
                assertTrue(pages.loginPage().isPhoneErrorDisplayed(),
                                "Phone error should be displayed for deleted account");
                assertEquals("No user found. Please create an account first.",
                                pages.loginPage().getPhoneErrorText(),
                                "Error message should confirm account no longer exists");
        }
}
