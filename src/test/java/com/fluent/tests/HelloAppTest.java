package com.fluent.tests;

import com.fluent.pages.PageManager;
import com.fluent.utils.BaseTest;
import com.fluent.utils.ConfigLoader;
import io.appium.java_client.android.options.UiAutomator2Options;
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
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.junit.jupiter.api.Assertions.*;

@Feature("Hello App")
@Execution(ExecutionMode.SAME_THREAD)
public class HelloAppTest extends BaseTest {

    private PageManager pages;

    @Override
    protected DesiredCapabilities buildCapabilities() {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        String deviceName = System.getProperty("deviceName", "").trim();
        if (deviceName.isEmpty()) deviceName = ConfigLoader.get("android.device.name", "emulator-5554");
        options.setDeviceName(deviceName);
        options.setAutomationName("UiAutomator2");
        options.setAppPackage("com.example.hello");
        options.setAppActivity(".MainActivity");
        options.setNoReset(true);
        options.setAutoGrantPermissions(true);
        return new DesiredCapabilities(options.asMap());
    }

    @BeforeEach
    public void initPages() {
        pages = new PageManager();
    }

    @Test
    @Tag("regression")
    @Tag("smoke")
    @Story("Hello screen interaction")
    @Description("Verify action bar shows correct version and tapping Hello World text works")
    @Severity(SeverityLevel.NORMAL)
    public void testHelloTextTapAndVersionCheck1() {
        assertTrue(pages.helloMainPage().isDisplayed(), "Hello World text should be visible on launch");

        String version = pages.helloMainPage().getActionBarTitle();
        assertEquals("HelloVersion", version, "Action bar title should display the app version");

        pages.helloMainPage().tapHelloText();

        assertTrue(pages.helloMainPage().isDisplayed(), "Hello World text should still be visible after tap");
    }

     @Test
    @Tag("regression")
    @Tag("smoke")
    @Story("Hello screen interaction")
    @Description("Verify action bar shows correct version and tapping Hello World text works")
    @Severity(SeverityLevel.NORMAL)
    public void testHelloTextTapAndVersionCheck2() {
        assertTrue(pages.helloMainPage().isDisplayed(), "Hello World text should be visible on launch");

        String version = pages.helloMainPage().getActionBarTitle();
        assertEquals("HelloVersion", version, "Action bar title should display the app version");

        pages.helloMainPage().tapHelloText();

        assertTrue(pages.helloMainPage().isDisplayed(), "Hello World text should still be visible after tap");
    }

     @Test
    @Tag("regression")
    @Tag("smoke")
    @Story("Hello screen interaction")
    @Description("Verify action bar shows correct version and tapping Hello World text works")
    @Severity(SeverityLevel.NORMAL)
    public void testHelloTextTapAndVersionCheck3() {
        assertTrue(pages.helloMainPage().isDisplayed(), "Hello World text should be visible on launch");

        String version = pages.helloMainPage().getActionBarTitle();
        assertEquals("HelloVersion", version, "Action bar title should display the app version");

        pages.helloMainPage().tapHelloText();

        assertTrue(pages.helloMainPage().isDisplayed(), "Hello World text should still be visible after tap");
    }

     @Test
    @Tag("regression")
    @Tag("smoke")
    @Story("Hello screen interaction")
    @Description("1Verify action bar shows correct version and tapping Hello World text works")
    @Severity(SeverityLevel.NORMAL)
    public void testHelloTextTapAndVersionCheck4() {
        assertTrue(pages.helloMainPage().isDisplayed(), "Hello World text should be visible on launch");

        String version = pages.helloMainPage().getActionBarTitle();
        assertEquals("HelloVersion", version, "Action bar title should display the app version");

        pages.helloMainPage().tapHelloText();

        assertTrue(pages.helloMainPage().isDisplayed(), "Hello World text should still be visible after tap");
    }
}
