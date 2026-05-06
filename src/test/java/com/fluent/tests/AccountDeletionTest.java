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

@Feature("Account Deletion")
@Execution(ExecutionMode.SAME_THREAD)
public class AccountDeletionTest extends AndroidBaseTest {

    private PageManager pages;

    @BeforeEach
    public void initPages() {
        pages = new PageManager();
    }

    @Test
    @RetryOnFailure
    @Tag("smoke")
    @Tag("regression")
    @Story("Account deletion confirmation dialog")
    @Description("Tapping 'Delete my account' shows a confirmation dialog before proceeding with deletion")
    @Severity(SeverityLevel.CRITICAL)
    public void testAccountDeletionConfirmationDialogIsShown() {
        signIn(pages);

        pages.fluentHomePage().tapHome();
        pages.fluentHomePage().tapHomeSection1();
        assertTrue(pages.settingPage().isDisplayed(), "Settings screen should be displayed");

        pages.settingPage().tapDeleteAccount();
        assertTrue(pages.settingPage().isRequestDeletionScreenDisplayed(),
                "Request deletion screen should be displayed after tapping Delete Account");

        pages.settingPage().tapDeleteMyAccount();
        assertTrue(pages.settingPage().isDeleteDialogDisplayed(),
                "Confirmation dialog should appear before account deletion proceeds");

        String dialogTitle = pages.settingPage().getDialogTitle();
        assertFalse(dialogTitle.isEmpty(), "Confirmation dialog title should not be empty");

        String dialogSubTitle = pages.settingPage().getDialogSubTitle();
        assertFalse(dialogSubTitle.isEmpty(), "Confirmation dialog subtitle should not be empty");
    }

    
    @Test
    @RetryOnFailure
    @Tag("regression")
    @Story("Account deletion")
    @Description("Account deletion proceeds and dialog is dismissed when user confirms via 'Delete account' button")
    @Severity(SeverityLevel.CRITICAL)
    public void testAccountDeletion() {
        signIn(pages);

        pages.fluentHomePage().tapHome();
        pages.fluentHomePage().tapHomeSection1();
        assertTrue(pages.settingPage().isDisplayed(), "Settings screen should be displayed");

        pages.settingPage().tapDeleteAccount();
        assertTrue(pages.settingPage().isRequestDeletionScreenDisplayed(),
                "Request deletion screen should be displayed after tapping Delete Account");

        pages.settingPage().tapDeleteMyAccount();
        assertTrue(pages.settingPage().isDeleteDialogDisplayed(),
                "Confirmation dialog should appear before account deletion proceeds");

        pages.settingPage().tapConfirmDeleteAccount();
        assertFalse(pages.settingPage().isDeleteDialogDisplayed(),
                "Confirmation dialog should be dismissed after confirming deletion");
        pages.createAccountPage().tapLogIn();
        assertTrue(pages.loginPage().isDisplayed(), "Sign In screen should be displayed after account deletion");
        pages.loginPage().enterPhoneNumber(TestData.SIGN_IN_MOBILE);
        pages.loginPage().tapContinue();
        assertTrue(pages.loginPage().isPhoneErrorDisplayed(),
                "No user found error should be displayed for the deleted account");
        assertTrue(pages.loginPage().getPhoneErrorText().contains("No user found"),
                "Error text should indicate no user was found: " + pages.loginPage().getPhoneErrorText());
    }
}
