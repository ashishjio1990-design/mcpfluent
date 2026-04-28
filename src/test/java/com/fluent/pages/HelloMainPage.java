package com.fluent.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class HelloMainPage extends BasePage {

    @AndroidFindBy(id = "com.example.hello:id/helloText")
    private WebElement helloText;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='HelloVersion']")
    private WebElement actionBarTitle;

    public boolean isDisplayed() {
        return isVisible(helloText);
    }

    public String getHelloText() {
        return getText(helloText);
    }

    public void tapHelloText() {
        log.info("Tapping Hello World text");
        tap(helloText);
    }

    public String getActionBarTitle() {
        return getText(actionBarTitle);
    }
}
