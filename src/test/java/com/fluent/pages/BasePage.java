package com.fluent.pages;

import com.fluent.utils.DriverManager;
import com.fluent.utils.WaitUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public abstract class BasePage {

    protected final Logger log = LoggerFactory.getLogger(getClass());
    protected final AppiumDriver driver;
    protected final WaitUtils wait;

    protected BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WaitUtils(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    protected boolean waitForVisible(WebElement element) {
        try {
            wait.waitForVisible(element);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected String getText(WebElement element) {
        return element.getText();
    }

    protected boolean isVisible(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected void tap(WebElement element) {
        element.click();
    }

    protected void clearAndType(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
    }

    protected void hideKeyboard() {
        try {
            driver.executeScript("mobile: hideKeyboard");
        } catch (Exception e) {
            log.warn("Could not hide keyboard: {}", e.getMessage());
        }
    }

    protected void scrollToText(String text) {
        driver.findElement(AppiumBy.androidUIAutomator(
            "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"" + text + "\"))"));
    }

    protected void scrollToElementById(String resourceId) {
        driver.findElement(AppiumBy.androidUIAutomator(
            "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().resourceId(\"" + resourceId + "\"))"));
    }
}
