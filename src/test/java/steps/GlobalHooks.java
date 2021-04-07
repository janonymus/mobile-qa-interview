package steps;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Step;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;
import org.viesure.utils.AllureLogger;
import org.viesure.utils.FileUtil;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class GlobalHooks {
    private File app = new File(System.getProperty("user.dir") + "/app/android/" + "qa-interview.apk");

    private static ThreadLocal<AndroidDriver<AndroidElement>> threadedDriver = new ThreadLocal<>();


    @Before(order = 0)
    @Step("Initalizing Android driver")
    public void setUp(){
        //Moving Allure history
        FileUtil.moveAllureReportHistory();
        initalizeAndroidDriver();
    }

    @AfterStep
    public void afterStep(Scenario scenario){
        if (scenario.isFailed()){
            AllureLogger.saveTextLog("Saved screenshot for failed step: " + scenario.getName());
            AllureLogger.saveScreenshotPNG(threadedDriver.get());
        }
    }

    @After(order = 1)
    @Step("Quitting android driver")
    public void tearDown(){
        threadedDriver.get().quit();
    }

    /**
     * Returns the current thread's driver
     * @return the driver
     */
    public AndroidDriver<AndroidElement> getDriver(){
        return threadedDriver.get();
    }

    @Step("Navigating back using android system's back button")
    public void navigateBack(){
        threadedDriver.get().navigate().back();
    }

    private void initalizeAndroidDriver(){
        //Getting the device parameter from testNG
        String device = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("device");

        //If we dont get a device name for some reason, we are defaulting for pixel for now
        if (device == null){
            device = "pixel";
        }

        initalizeDriverFor(device);
    }

    private void initalizeDriverFor(String device){
        Properties properties = FileUtil.loadDeviceProperty(device);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        //Common capabilities
        capabilities.setCapability(MobileCapabilityType.APP,app.getAbsolutePath());
        capabilities.setCapability("appPackage","io.viesure.qa");
        capabilities.setCapability("appActivity","io.viesure.qa.views.MainActivity");
        //Capabilities from the property file
        capabilities.setCapability("deviceName",properties.getProperty("deviceName"));
        capabilities.setCapability("udid",properties.getProperty("udid"));
        capabilities.setCapability("platformName",properties.getProperty("platformName"));

        try {
            threadedDriver.set(new AndroidDriver<>(new URL(properties.getProperty("remoteUrl")), capabilities));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
