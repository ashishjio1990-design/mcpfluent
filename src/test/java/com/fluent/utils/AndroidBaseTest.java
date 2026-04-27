package com.fluent.utils;

import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.remote.DesiredCapabilities;

public abstract class AndroidBaseTest extends BaseTest {

    @Override
    protected DesiredCapabilities buildCapabilities() {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        String deviceName = System.getProperty("deviceName", "").trim();
        if (deviceName.isEmpty()) deviceName = ConfigLoader.get("android.device.name", "emulator-5554");
        options.setDeviceName(deviceName);
        options.setAutomationName("UiAutomator2");

        String appPath = System.getProperty("app", "").trim();
        if (appPath.isEmpty()) appPath = ConfigLoader.get("android.app.path", "");
        if (!appPath.isEmpty()) options.setApp(appPath);

        String appPackage = ConfigLoader.get("android.app.package", "");
        String appActivity = ConfigLoader.get("android.app.activity", "");
        if (!appPackage.isEmpty()) options.setAppPackage(appPackage);
        if (!appActivity.isEmpty()) options.setAppActivity(appActivity);

        options.setNoReset(Boolean.parseBoolean(ConfigLoader.get("no.reset", "false")));
        options.setAutoGrantPermissions(true);
        return new DesiredCapabilities(options.asMap());
    }
}
