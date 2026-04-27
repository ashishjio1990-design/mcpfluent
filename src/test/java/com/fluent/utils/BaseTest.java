package com.fluent.utils;

import io.appium.java_client.AppiumDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseTest {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @BeforeEach
    public void setUp() {
        DesiredCapabilities caps = buildCapabilities();
        DriverManager.initDriver(caps);
    }

    @AfterEach
    public void tearDown() {
        DriverManager.quitDriver();
    }

    protected AppiumDriver getDriver() {
        return DriverManager.getDriver();
    }

    protected abstract DesiredCapabilities buildCapabilities();
}
