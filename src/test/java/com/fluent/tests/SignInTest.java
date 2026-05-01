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
}
