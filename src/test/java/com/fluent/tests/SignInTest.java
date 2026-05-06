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
        signIn(pages);
        signOut(pages);
    }

   

@Test
    @RetryOnFailure
    @Tag("smoke")
    @Tag("regression")
    @Story("Existing user sign in")
    @Description("Login with incorrect PIN shows error")
    @Severity(SeverityLevel.CRITICAL)
    public void testExistingUserSignIn3() throws InterruptedException {
        // Navigate to Enter PIN screen
        if (pages.settingPage().isPinBackButtonDisplayed()) {
            log.info("PIN back button detected before welcome screen — tapping to dismiss");
            pages.settingPage().tapPinBackButton();
        }
        assertTrue(pages.createAccountPage().isDisplayed(), "Welcome screen should be displayed");
        pages.createAccountPage().tapLogIn();

        assertTrue(pages.loginPage().isDisplayed(), "Sign In screen should be displayed");
        assertFalse(pages.loginPage().isContinueButtonEnabled(),
                "Continue button should be disabled before entering phone number");
        pages.loginPage().enterPhoneNumber(TestData.SIGN_IN_MOBILE);
        assertTrue(pages.loginPage().isContinueButtonEnabled(),
                "Continue button should be enabled after entering phone number");
        pages.loginPage().tapContinue();

        // Enter wrong PIN
        assertTrue(pages.enterPinPage().isDisplayed(), "Enter PIN screen should be displayed");
        pages.enterPinPage().enterPin("000001");

        // Step 5: Error message should appear
        assertTrue(pages.enterPinPage().isErrorDisplayed(), "Error message should be displayed for incorrect PIN");
        String errorText = pages.enterPinPage().getErrorMessage();
        assertFalse(errorText.isEmpty(), "Error message text should not be empty");
        assertTrue(errorText.contains("Invalid PIN"), "Error should mention invalid PIN: " + errorText);

    }

    
}

