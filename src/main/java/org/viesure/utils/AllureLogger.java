package org.viesure.utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class AllureLogger {

    /**
     * Logs a screenshot into the report
     * @param driver the current driver to take screenshot with
     * @return the screenshot for the attachment
     */
    @Attachment(value = "Click to open issue screenshot", type = "image/png")
    public static byte[] saveScreenshotPNG (AndroidDriver<AndroidElement> driver) {
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }

    /**
     * Saves a text log into the report
     * @param message the message to attach into the report
     * @return the message to attach
     */
    @Attachment(value = "{0}", type = "text/plain")
    public static String saveTextLog (String message) {
        return message;
    }
}
