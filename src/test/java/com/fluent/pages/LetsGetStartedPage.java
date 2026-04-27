package com.fluent.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class LetsGetStartedPage extends BasePage {

    @AndroidFindBy(id = "com.fluenthealth.app:id/onboardingWelcomeLottieView")
    private WebElement welcomeAnimation;

    @AndroidFindBy(id = "com.fluenthealth.app:id/onboardingWelcomeAnimatedCircleIv")
    private WebElement animatedCircle;

    @AndroidFindBy(id = "com.fluenthealth.app:id/onboardingWelcomeDescriptionTv")
    private WebElement descriptionText;

    @AndroidFindBy(id = "com.fluenthealth.app:id/onboardingWelcomeGetStartedBtn")
    private WebElement createAccountButton;

    @AndroidFindBy(id = "com.fluenthealth.app:id/onboardingWelcomeSignInBtn")
    private WebElement logInButton;

    public String getDescriptionText() {
        return getText(descriptionText);
    }

    public boolean isCreateAccountButtonDisplayed() {
        return isVisible(createAccountButton);
    }

    public boolean isLogInButtonDisplayed() {
        return isVisible(logInButton);
    }

    public LoginPage tapLogIn() {
        log.info("Tapping Log in");
        tap(logInButton);
        return new LoginPage();
    }

    public boolean isDisplayed() {
        return isVisible(createAccountButton);
    }
}
