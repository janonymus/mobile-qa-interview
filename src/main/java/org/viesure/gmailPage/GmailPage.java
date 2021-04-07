package org.viesure.gmailPage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.viesure.base.BasePage;

import java.util.Set;

public class GmailPage extends BasePage {

    private boolean composeMode;

    private WebElement recipient;
    private WebElement subject;
    private WebElement body;

    public GmailPage(AndroidDriver<AndroidElement> driver){
        super(driver);

        if (driver.currentActivity().equals(".ComposeActivityGmailExternal")||driver.currentActivity().equals(".ComposeActivityGmail")){
            composeMode = true;

            WebDriverWait wait = new WebDriverWait(driver,10);
            recipient = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.google.android.gm:id/to")));
            subject = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.google.android.gm:id/subject")));
            body = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.google.android.gm:id/body_wrapper")));
        } else {
            composeMode = false;
        }
    }


    /**
     * Returns the current gmail activity
     * @return the activity string
     */
    public String getCurrentActivity(){
        return driver.currentActivity();
    }

    /**
     * Returs the recepient text if the page is in compose mode
     * @return recipient text
     */
    public String getRecipient() {
        return recipient.getText();
    }

    /**
     * Returs the subject text if the page is in compose mode
     * @return subject text
     */
    public String getSubject() {
        return subject.getText();
    }

    /**
     * Returs the body text if the page is in compose mode
     * @return body text
     */
    public String getBody() {
        return body.getText();
    }

    //Currently not working, it's not able to change context to gmail app
    /**
     * Closes the gmail app
     */
    public void closeGmailApp(){
        switchContext(true);
        driver.closeApp();
        switchContext(false);
    }

    /**
     * Switches the context to either gmail or viesure
     * @param switchToGmail if true, switches the context to gmail, else switches to viesure
     */
    private void switchContext(boolean switchToGmail){
        Set<String> contextNames = driver.getContextHandles();
        if (switchToGmail){
            String gmailContext = null;
            if (contextNames.size() > 1){
                gmailContext = (String) contextNames.toArray()[1];
            }
            if (gmailContext !=null){
                driver.context(gmailContext);
            }
        } else {
            //since we did not open any other app, the viesure app's context name is NATIVE_APP
            driver.context("NATIVE_APP");
        }
    }
}
