package org.viesure.base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    protected final AndroidDriver<AndroidElement> driver;

    public BasePage(AndroidDriver<AndroidElement> driver){
        this.driver = driver;
    }

    /**
     * Returns if an element is present within given timeframe
     * @param by the locator of the element
     * @param timeoutInSeconds the maximum waiting time
     * @return true if the element is visible
     */
    public boolean isElementPresent(By by, int timeoutInSeconds){
        try{
            WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
