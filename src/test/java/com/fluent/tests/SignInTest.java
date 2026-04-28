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

import static org.junit.jupiter.api.Assertions.*;

@Feature("Sign In")
public class SignInTest extends AndroidBaseTest {

    private PageManager pages;

    @BeforeEach
    public void initPages() {
        pages = new PageManager();
    }

    @Test
    @Story("Existing user sign in")
    @Description("Verify existing user can sign in with valid phone number and PIN")
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
    @Tag("regression")
    @Story("Existing user sign in")
    @Description("Regression: verify existing user sign in flow - scenario 1")
    @Severity(SeverityLevel.CRITICAL)
    public void testExistingUserSignIn1() throws InterruptedException {
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
    @Tag("regression")
    @Story("Existing user sign in")
    @Description("Regression: verify existing user sign in flow - scenario 2")
    @Severity(SeverityLevel.CRITICAL)
    public void testExistingUserSignIn2() throws InterruptedException {
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
}
