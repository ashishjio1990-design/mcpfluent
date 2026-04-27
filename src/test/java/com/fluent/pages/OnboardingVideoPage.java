package com.fluent.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class OnboardingVideoPage extends BasePage {

    @AndroidFindBy(id = "com.fluenthealth.app:id/seekBar")
    private WebElement seekBar;

    @AndroidFindBy(id = "com.fluenthealth.app:id/btnMute")
    private WebElement muteButton;

    @AndroidFindBy(id = "com.fluenthealth.app:id/onboardingVideoPV")
    private WebElement videoPlayer;

    @AndroidFindBy(id = "com.fluenthealth.app:id/btnBackToolbar")
    private WebElement backButton;

    @AndroidFindBy(id = "com.fluenthealth.app:id/btnSkipToolbar")
    private WebElement skipToolbarButton;

    @AndroidFindBy(id = "com.fluenthealth.app:id/btnSkip")
    private WebElement skipButton;

    public boolean isDisplayed() {
        return isVisible(videoPlayer);
    }

    public boolean isSeekBarDisplayed() {
        return isVisible(seekBar);
    }

    public boolean isMuteButtonDisplayed() {
        return isVisible(muteButton);
    }

    public boolean isSkipButtonDisplayed() {
        return isVisible(skipButton);
    }

    public boolean isSkipToolbarButtonDisplayed() {
        return isVisible(skipToolbarButton);
    }

    public OnboardingVideoPage tapMute() {
        log.info("Tapping Mute button");
        tap(muteButton);
        return this;
    }

    public OnboardingVideoPage tapVideo() {
        log.info("Tapping video player");
        tap(videoPlayer);
        return this;
    }

    public void tapBack() {
        log.info("Tapping Back button");
        tap(backButton);
    }

    public LetsGetStartedPage tapSkipToolbar() {
        log.info("Tapping Skip on toolbar");
        tap(skipToolbarButton);
        return new LetsGetStartedPage();
    }

    public RegistrationPage tapSkip() {
        log.info("Tapping Skip button");
        tap(skipButton);
        return new RegistrationPage();
    }
}
