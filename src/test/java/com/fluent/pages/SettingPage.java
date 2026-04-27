package com.fluent.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class SettingPage extends BasePage {

    // Toolbar
    @AndroidFindBy(id = "com.fluenthealth.app:id/image_view_back")
    private WebElement backButton;

    @AndroidFindBy(id = "com.fluenthealth.app:id/text_view_toolbar_title")
    private WebElement toolbarTitle;

    // Scroll view
    @AndroidFindBy(id = "com.fluenthealth.app:id/scroll_view_profile_settings")
    private WebElement scrollView;

    // Account section
    @AndroidFindBy(id = "com.fluenthealth.app:id/ll_profile_settings_account_header")
    private WebElement accountSectionHeader;

    @AndroidFindBy(id = "com.fluenthealth.app:id/tv_account_settings")
    private WebElement accountSettingsTitle;

    @AndroidFindBy(id = "com.fluenthealth.app:id/cv_app_security")
    private WebElement appSecurityItem;

    @AndroidFindBy(id = "com.fluenthealth.app:id/cv_settings_measurement")
    private WebElement measurementPreferencesItem;

    @AndroidFindBy(id = "com.fluenthealth.app:id/cv_communication_preferences")
    private WebElement communicationPreferencesItem;

    @AndroidFindBy(id = "com.fluenthealth.app:id/cv_whatsapp_preferences")
    private WebElement whatsappPreferencesItem;

    // Data & Privacy section
    @AndroidFindBy(id = "com.fluenthealth.app:id/ll_profile_settings_data_privacy_header")
    private WebElement dataPrivacySectionHeader;

    @AndroidFindBy(id = "com.fluenthealth.app:id/tv_data_and_privacy")
    private WebElement dataPrivacyTitle;

    @AndroidFindBy(id = "com.fluenthealth.app:id/cv_legal")
    private WebElement legalItem;

    // Support section
    @AndroidFindBy(id = "com.fluenthealth.app:id/ll_profile_settings_support_header")
    private WebElement supportSectionHeader;

    @AndroidFindBy(id = "com.fluenthealth.app:id/tv_support")
    private WebElement supportTitle;

    @AndroidFindBy(id = "com.fluenthealth.app:id/cv_fa_qs")
    private WebElement faqsItem;

    @AndroidFindBy(id = "com.fluenthealth.app:id/cv_fluent_support")
    private WebElement emailUsItem;

    @AndroidFindBy(id = "com.fluenthealth.app:id/cv_app_tutorial")
    private WebElement appTutorialItem;

    // Sharing / Referrals section
    @AndroidFindBy(id = "com.fluenthealth.app:id/ll_profile_settings_referrals_header")
    private WebElement sharingSectionHeader;

    @AndroidFindBy(id = "com.fluenthealth.app:id/tv_sharing")
    private WebElement sharingTitle;

    @AndroidFindBy(id = "com.fluenthealth.app:id/cv_follow_us_on_social")
    private WebElement followUsOnSocialItem;

    @AndroidFindBy(id = "com.fluenthealth.app:id/cv_rate_us_on_the_app_store")
    private WebElement rateUsOnAppStoreItem;

    // Account actions
    @AndroidFindBy(id = "com.fluenthealth.app:id/text_view_profile_settings_logout")
    private WebElement logOutButton;

    @AndroidFindBy(id = "com.fluenthealth.app:id/text_view_profile_settings_delete_data_description")
    private WebElement deleteDataDescription;

    @AndroidFindBy(id = "com.fluenthealth.app:id/tv_delete_account_and_all_personal_data")
    private WebElement deleteAccountButton;

    @AndroidFindBy(id = "com.fluenthealth.app:id/text_view_profile_settings_version")
    private WebElement appVersionText;

    // Delete Personal Data and Account confirmation screen
    @AndroidFindBy(id = "com.fluenthealth.app:id/text_view_title")
    private WebElement requestDeletionTitle;

    @AndroidFindBy(id = "com.fluenthealth.app:id/text_view_sub_title")
    private WebElement requestDeletionSubTitle;

    @AndroidFindBy(xpath = "//android.widget.Button[@text=\"Delete my account\"]")
    private WebElement deleteMyAccountButton;

    // Delete account confirmation dialog
    @AndroidFindBy(id = "com.fluenthealth.app:id/iv_close")
    private WebElement dialogCloseButton;

    @AndroidFindBy(id = "com.fluenthealth.app:id/tv_title")
    private WebElement dialogTitle;

    @AndroidFindBy(id = "com.fluenthealth.app:id/tv_sub_title")
    private WebElement dialogSubTitle;

    @AndroidFindBy(id = "com.fluenthealth.app:id/btn_one")
    private WebElement noGoBackButton;

    @AndroidFindBy(id = "com.fluenthealth.app:id/btn_two")
    private WebElement confirmDeleteAccountButton;

    public boolean isDisplayed() {
        return isVisible(toolbarTitle);
    }

    public String getToolbarTitle() {
        return getText(toolbarTitle);
    }

    public void tapBack() {
        log.info("Tapping Back button");
        tap(backButton);
    }

    public void tapAppSecurity() {
        log.info("Tapping App Security");
        tap(appSecurityItem);
    }

    public void tapMeasurementPreferences() {
        log.info("Tapping Measurement Preferences");
        tap(measurementPreferencesItem);
    }

    public void tapCommunicationPreferences() {
        log.info("Tapping Communication Preferences");
        tap(communicationPreferencesItem);
    }

    public void tapWhatsappPreferences() {
        log.info("Tapping WhatsApp Preferences");
        tap(whatsappPreferencesItem);
    }

    public void tapLegal() {
        log.info("Tapping Legal");
        tap(legalItem);
    }

    public void tapFaqs() {
        log.info("Tapping FAQs");
        tap(faqsItem);
    }

    public void tapEmailUs() {
        log.info("Tapping Email us");
        tap(emailUsItem);
    }

    public void tapAppTutorial() {
        log.info("Tapping App Tutorial");
        tap(appTutorialItem);
    }

    public void tapSharing() {
        log.info("Tapping Sharing");
        tap(sharingSectionHeader);
    }

    public boolean isAppSecurityDisplayed() {
        return isVisible(appSecurityItem);
    }

    public boolean isMeasurementPreferencesDisplayed() {
        return isVisible(measurementPreferencesItem);
    }

    public boolean isCommunicationPreferencesDisplayed() {
        return isVisible(communicationPreferencesItem);
    }

    public boolean isWhatsappPreferencesDisplayed() {
        return isVisible(whatsappPreferencesItem);
    }

    public boolean isLegalDisplayed() {
        return isVisible(legalItem);
    }

    public boolean isFaqsDisplayed() {
        return isVisible(faqsItem);
    }

    public boolean isEmailUsDisplayed() {
        return isVisible(emailUsItem);
    }

    public boolean isAppTutorialDisplayed() {
        return isVisible(appTutorialItem);
    }

    public void tapFollowUsOnSocial() {
        log.info("Tapping Follow us on social");
        tap(followUsOnSocialItem);
    }

    public void tapRateUsOnAppStore() {
        log.info("Tapping Rate us on the App Store");
        tap(rateUsOnAppStoreItem);
    }

    public void tapLogOut() {
        log.info("Tapping Log Out");
        tap(logOutButton);
    }

    public void tapDeleteAccount() {
        log.info("Scrolling to and tapping Delete account and all personal data");
        scrollToText("Delete account and all personal data");
        tap(deleteAccountButton);
    }

    public String getAppVersion() {
        return getText(appVersionText);
    }

    public boolean isDeleteAccountButtonDisplayed() {
        return isVisible(deleteAccountButton);
    }

    public boolean isLogOutButtonDisplayed() {
        return isVisible(logOutButton);
    }

    // Delete Personal Data and Account confirmation screen
    public boolean isRequestDeletionScreenDisplayed() {
        return isVisible(requestDeletionTitle);
    }

    public String getRequestDeletionTitle() {
        return getText(requestDeletionTitle);
    }

    public String getRequestDeletionSubTitle() {
        return getText(requestDeletionSubTitle);
    }

    public void tapDeleteMyAccount() {
        log.info("Tapping Delete my account button");
        tap(deleteMyAccountButton);
    }

    public boolean isDeleteMyAccountButtonDisplayed() {
        return isVisible(deleteMyAccountButton);
    }

    // Delete account confirmation dialog
    public boolean isDeleteDialogDisplayed() {
        return isVisible(dialogTitle);
    }

    public String getDialogTitle() {
        return getText(dialogTitle);
    }

    public String getDialogSubTitle() {
        return getText(dialogSubTitle);
    }

    public void tapNoGoBack() {
        log.info("Tapping No, go back");
        tap(noGoBackButton);
    }

    public void tapConfirmDeleteAccount() {
        log.info("Tapping Delete account (confirm dialog)");
        tap(confirmDeleteAccountButton);
    }

    public void tapDialogClose() {
        log.info("Tapping Close on delete confirmation dialog");
        tap(dialogCloseButton);
    }
}
