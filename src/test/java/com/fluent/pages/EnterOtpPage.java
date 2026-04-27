package com.fluent.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class EnterOtpPage extends BasePage {

    @AndroidFindBy(id = "com.fluenthealth.app:id/enterOtpTitleTv")
    private WebElement titleText;

    @AndroidFindBy(id = "com.fluenthealth.app:id/enterOtpDescriptionTv")
    private WebElement descriptionText;

    @AndroidFindBy(id = "com.fluenthealth.app:id/enterOtpEt")
    private WebElement otpInputField;

    @AndroidFindBy(id = "com.fluenthealth.app:id/enterOtpDigit1Et")
    private WebElement otpDigit1;

    @AndroidFindBy(id = "com.fluenthealth.app:id/enterOtpDigit2Et")
    private WebElement otpDigit2;

    @AndroidFindBy(id = "com.fluenthealth.app:id/enterOtpDigit3Et")
    private WebElement otpDigit3;

    @AndroidFindBy(id = "com.fluenthealth.app:id/enterOtpDigit4Et")
    private WebElement otpDigit4;

    @AndroidFindBy(id = "com.fluenthealth.app:id/enterOtpDigit5Et")
    private WebElement otpDigit5;

    @AndroidFindBy(id = "com.fluenthealth.app:id/enterOtpDigit6Et")
    private WebElement otpDigit6;

    @AndroidFindBy(id = "com.fluenthealth.app:id/enterOtpVerifyBtn")
    private WebElement verifyButton;

    @AndroidFindBy(id = "com.fluenthealth.app:id/enterOtpResendBtn")
    private WebElement resendOtpButton;

    @AndroidFindBy(id = "com.fluenthealth.app:id/enterOtpResendTimerTv")
    private WebElement resendTimerText;

    @AndroidFindBy(id = "com.fluenthealth.app:id/enterOtpErrorTv")
    private WebElement errorText;

    @AndroidFindBy(id = "com.fluenthealth.app:id/enterOtpBackBtn")
    private WebElement backButton;

    public boolean isDisplayed() {
        return isVisible(verifyButton);
    }

    public String getTitleText() {
        return getText(titleText);
    }

    public String getDescriptionText() {
        return getText(descriptionText);
    }

    public String getErrorText() {
        return getText(errorText);
    }

    public String getResendTimerText() {
        return getText(resendTimerText);
    }

    public boolean isVerifyButtonDisplayed() {
        return isVisible(verifyButton);
    }

    public boolean isResendOtpButtonDisplayed() {
        return isVisible(resendOtpButton);
    }

    public boolean isErrorDisplayed() {
        return isVisible(errorText);
    }

    public EnterOtpPage enterOtp(String otp) {
        log.info("Entering OTP: {}", otp);
        clearAndType(otpInputField, otp);
        return this;
    }

    public EnterOtpPage enterOtpDigits(String otp) {
        log.info("Entering OTP digits: {}", otp);
        if (otp.length() >= 1) clearAndType(otpDigit1, String.valueOf(otp.charAt(0)));
        if (otp.length() >= 2) clearAndType(otpDigit2, String.valueOf(otp.charAt(1)));
        if (otp.length() >= 3) clearAndType(otpDigit3, String.valueOf(otp.charAt(2)));
        if (otp.length() >= 4) clearAndType(otpDigit4, String.valueOf(otp.charAt(3)));
        if (otp.length() >= 5) clearAndType(otpDigit5, String.valueOf(otp.charAt(4)));
        if (otp.length() >= 6) clearAndType(otpDigit6, String.valueOf(otp.charAt(5)));
        return this;
    }

    public void tapVerify() {
        log.info("Tapping Verify button");
        tap(verifyButton);
    }

    public EnterOtpPage tapResendOtp() {
        log.info("Tapping Resend OTP");
        tap(resendOtpButton);
        return this;
    }

    public void tapBack() {
        log.info("Tapping Back button");
        tap(backButton);
    }
}
