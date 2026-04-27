package com.fluent.pages;

public class PageManager {

    private CreateAccountPage createAccountPage;
    private LetsGetStartedPage letsGetStartedPage;
    private LoginPage loginPage;
    private OnboardingVideoPage onboardingVideoPage;
    private RegistrationPage registrationPage;
    private EnterOtpPage enterOtpPage;
    private CreateFluentPin createFluentPin;
    private MoreDetailsPage moreDetailsPage;
    private NewUserWelcomeScreen newUserWelcomeScreen;
    private FluentHomePage fluentHomePage;
    private SettingPage settingPage;
    private EnterPinPage enterPinPage;

    public PageManager() {
        createAccountPage = new CreateAccountPage();
        letsGetStartedPage = new LetsGetStartedPage();
        loginPage = new LoginPage();
        onboardingVideoPage = new OnboardingVideoPage();
        registrationPage = new RegistrationPage();
        enterOtpPage = new EnterOtpPage();
        createFluentPin = new CreateFluentPin();
        moreDetailsPage = new MoreDetailsPage();
        newUserWelcomeScreen = new NewUserWelcomeScreen();
        fluentHomePage = new FluentHomePage();
        settingPage = new SettingPage();
        enterPinPage = new EnterPinPage();
    }

    public CreateAccountPage createAccountPage() {
        return createAccountPage;
    }

    public LetsGetStartedPage letsGetStartedPage() {
        return letsGetStartedPage;
    }

    public LoginPage loginPage() {
        return loginPage;
    }

    public OnboardingVideoPage onboardingVideoPage() {
        return onboardingVideoPage;
    }

    public RegistrationPage registrationPage() {
        return registrationPage;
    }

    public EnterOtpPage enterOtpPage() {
        return enterOtpPage;
    }

    public CreateFluentPin createFluentPin() {
        return createFluentPin;
    }

    public MoreDetailsPage moreDetailsPage() {
        return moreDetailsPage;
    }

    public NewUserWelcomeScreen newUserWelcomeScreen() {
        return newUserWelcomeScreen;
    }

    public FluentHomePage fluentHomePage() {
        return fluentHomePage;
    }

    public SettingPage settingPage() {
        return settingPage;
    }

    public EnterPinPage enterPinPage() {
        return enterPinPage;
    }
}
