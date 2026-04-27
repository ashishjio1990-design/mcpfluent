package com.fluent.tests;

import com.fluent.pages.PageManager;
import com.fluent.testdata.TestData;
import com.fluent.utils.AndroidBaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

public class SignUpTest extends AndroidBaseTest {
        private static final Logger log = LoggerFactory.getLogger(SignUpTest.class);

        private PageManager pages;

        @BeforeEach
        public void initPages() {
                pages = new PageManager();
        }

        @Test
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
                                "Continue button should be disabled before filling the form");

                pages.registrationPage()
                                .enterFirstName(TestData.FIRST_NAME)
                                .enterLastName(TestData.LAST_NAME)
                                .enterEmail(TestData.EMAIL)
                                .enterMobileNumber(TestData.SIGN_UP_MOBILE);
                Thread.sleep(2000);

                pages.registrationPage().scrollToContinueButton();

                assertTrue(pages.registrationPage().isContinueButtonEnabled(),
                                "Continue button should be enabled after all fields are filled");
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

                pages.fluentHomePage().tapHomeSection1();
                assertTrue(pages.settingPage().isDisplayed(), "Settings page should be displayed");

                // Step 10: Scroll to and tap Delete account and all personal data
                pages.settingPage().tapDeleteAccount();

                // Step 11: Tap Delete my account on confirmation screen
                pages.settingPage().tapDeleteMyAccount();

                // Step 12: Confirm deletion on the dialog
                assertTrue(pages.settingPage().isDeleteDialogDisplayed(),
                                "Delete confirmation dialog should be displayed");
                pages.settingPage().tapConfirmDeleteAccount();
                Thread.sleep(2000);
                // Step 13: Welcome screen should reappear after account deletion → tap Log in
                assertTrue(pages.createAccountPage().isDisplayed(),
                                "Welcome screen should be displayed after account deletion");
                pages.createAccountPage().tapLogIn();

                // Step 14: Enter deleted account's phone number and verify error message
                pages.loginPage().enterPhoneNumber(TestData.SIGN_UP_MOBILE);
                pages.loginPage().tapContinue();
                assertTrue(pages.loginPage().isPhoneErrorDisplayed(),
                                "Phone error should be displayed for deleted account");
                assertEquals("No user found. Please create an account first.",
                                pages.loginPage().getPhoneErrorText(),
                                "Error message should confirm account no longer exists");
        }
}
