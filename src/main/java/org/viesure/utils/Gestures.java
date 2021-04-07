package org.viesure.utils;

import io.appium.java_client.TouchAction;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;

import java.time.Duration;


public class Gestures {

    /**
     * Scrolls down on any page
     * @param driver the driver of the page
     */
    public static void scrollDown(AndroidDriver<AndroidElement> driver){
        //The viewing size of the device
        Dimension size = driver.manage().window().getSize();

        //x position set to mid-screen horizontally
        int width = size.width / 2;

        //Starting y location set to 80% of the height (near bottom)
        int startPoint = (int) (size.getHeight() * 0.80);

        //Ending y location set to 20% of the height (near top)
        int endPoint = (int) (size.getHeight() * 0.20);

        (new TouchAction<>(driver)).press(PointOption.point(width, startPoint)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(width, endPoint)).release().perform();
    }

    /**
     * Scrolls up on any page
     * @param driver the driver of the page
     */
    public static void scrollUp(AndroidDriver<AndroidElement> driver){
        //The viewing size of the device
        Dimension size = driver.manage().window().getSize();

        //x position set to mid-screen horizontally
        int width = size.width / 2;

        //Ending y location set to 80% of the height (near bottom)
        int endPoint = (int) (size.getHeight() * 0.80);

        //Starting y location set to 20% of the height (near top)
        int startPoint = (int) (size.getHeight() * 0.20);

        (new TouchAction<>(driver)).press(PointOption.point(width, startPoint)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(width, endPoint)).release().perform();
    }
}
