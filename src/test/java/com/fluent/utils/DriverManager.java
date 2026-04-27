package com.fluent.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class DriverManager {

    private static final Logger log = LoggerFactory.getLogger(DriverManager.class);
    private static final ThreadLocal<AppiumDriver> driverThread = new ThreadLocal<>();

    public static AppiumDriver getDriver() {
        return driverThread.get();
    }

    public static void initDriver(DesiredCapabilities caps) {
        String appiumUrl = System.getProperty("appiumUrl", "").trim();
        if (appiumUrl.isEmpty()) appiumUrl = ConfigLoader.get("appium.url", "http://127.0.0.1:4723");
        String platform = caps.getCapability("platformName").toString().toLowerCase();

        try {
            URL url = new URL(appiumUrl);
            AppiumDriver driver = platform.equals("android")
                    ? new AndroidDriver(url, caps)
                    : new IOSDriver(url, caps);

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(
                    Long.parseLong(ConfigLoader.get("implicit.wait", "10"))
            ));
            driverThread.set(driver);
            log.info("Driver initialized for platform: {}", platform);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Appium URL: " + appiumUrl, e);
        }
    }

    public static void quitDriver() {
        AppiumDriver driver = driverThread.get();
        if (driver != null) {
            driver.quit();
            driverThread.remove();
            log.info("Driver quit and removed from thread.");
        }
    }
}
