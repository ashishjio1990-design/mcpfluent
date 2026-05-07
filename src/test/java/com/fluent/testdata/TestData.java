package com.fluent.testdata;

public class TestData {

    // ── Shared (used across multiple tests) ──
    public static final String PIN = "000000";

    // ── testNewUserSignUp ──
    public static final String SIGN_UP_MOBILE = "9987741779";
    public static final String FIRST_NAME     = "Ashish";
    public static final String LAST_NAME      = "Taralkar";
    public static final String EMAIL          = "ashishtaralkar+1@gmail.com";
    public static final String DOB_DAY        = "10";
    public static final String DOB_MONTH      = "Aug";
    public static final String DOB_YEAR       = "1990";

    // ── testExistingUserSignIn ──
    public static final String SIGN_IN_MOBILE = "9773543188";

    // ── basicinfo_field_validation – Emergency Contact CRUD ──
    // ── AMD CRUD (Alternative Medical Decision Maker) ──
    public static final String AMD_FIRST_NAME          = "David";
    public static final String AMD_LAST_NAME           = "Wilson";
    public static final String AMD_CONTACT_NUMBER      = "9876543211";
    public static final String AMD_RELATIONSHIP        = "Guardian";
    public static final String AMD_UPDATED_FIRST_NAME  = "Sarah";
    public static final String AMD_UPDATED_LAST_NAME   = "Johnson";
    public static final String AMD_UPDATED_CONTACT     = "9123456780";

    // ── HI CRUD (Health Insurance) ──
    public static final String HI_INSURANCE_NAME       = "ABC Insurance";
    public static final String HI_POLICY_NUMBER        = "POL12345678";
    public static final String HI_INSURED_MEMBER_ID    = "MEM789012";
    public static final String HI_UPDATED_INSURANCE_NAME = "XYZ Insurance";
    public static final String HI_UPDATED_POLICY_NUMBER  = "POL87654321";
    public static final String HI_UPDATED_MEMBER_ID     = "MEM210987";

    // ── Surgeries and/or Procedures ──
    public static final String SP_SURGERY_NAME         = "Appendectomy";
    public static final String SP_STATUS               = "Completed";
    public static final String SP_UPDATED_STATUS       = "In-Progress";
    public static final String SP_NOTES                = "Post-surgery recovery going well. No complications observed.";
    public static final String SP_UPDATED_NOTES        = "Recovery completed. Patient cleared for normal activity.";
    public static final String SP_UPDATED_SURGERY_NAME = "Tonsillectomy";

    // ── Vitals – Blood Pressure ──
    public static final String VIT_SYSTOLIC          = "120";
    public static final String VIT_DIASTOLIC         = "80";
    public static final String VIT_UPDATED_SYSTOLIC  = "115";
    public static final String VIT_UPDATED_DIASTOLIC = "75";

    // ── Vitals – Body Temperature ──
    public static final String VIT_TEMPERATURE         = "37.0";
    public static final String VIT_UPDATED_TEMPERATURE = "36.5";

    // ── Vitals – Oxygen Saturation Level ──
    public static final String VIT_OXYGEN_LEVEL         = "98";
    public static final String VIT_UPDATED_OXYGEN_LEVEL = "97";

    // ── Vitals – Pulse Rate ──
    public static final String VIT_PULSE_RATE         = "72";
    public static final String VIT_UPDATED_PULSE_RATE = "75";

    // ── Vitals – Respiratory Rate ──
    public static final String VIT_RESPIRATORY_RATE         = "16";
    public static final String VIT_UPDATED_RESPIRATORY_RATE = "18";

    // ── Vaccines ──
    public static final String VAX_VACCINE_NAME        = "Flu Vaccine";
    public static final String VAX_DOSE                = "Dose 1";
    public static final String VAX_NOTES               = "Annual flu vaccine administered. No adverse reactions.";
    public static final String VAX_UPDATED_NOTES       = "Follow-up confirmed. Immunity response normal.";

    // ── Key Health Tests ──
    public static final String KHT_TEST_NAME          = "Blood Sugar Test";
    public static final String KHT_NOTES              = "Fasting blood sugar levels checked. Results within normal range.";
    public static final String KHT_UPDATED_NOTES      = "Follow-up test completed. All values normalized.";

    public static final String EC_FIRST_NAME          = "John";
    public static final String EC_LAST_NAME           = "Doe";
    public static final String EC_CONTACT_NUMBER      = "9876543210";
    public static final String EC_RELATIONSHIP        = "Spouse";
    public static final String EC_UPDATED_FIRST_NAME  = "Jane";
    public static final String EC_UPDATED_LAST_NAME   = "Smith";
    public static final String EC_UPDATED_CONTACT     = "9123456789";

}
