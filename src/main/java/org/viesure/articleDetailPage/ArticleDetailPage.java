package org.viesure.articleDetailPage;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.viesure.articleListPage.ArticleListPage;
import org.viesure.base.BasePage;
import org.viesure.gmailPage.GmailPage;
import org.viesure.utils.Gestures;


public class ArticleDetailPage extends BasePage {

    ArticleDetailPageLocators locators;

    public ArticleDetailPage(AndroidDriver<AndroidElement> driver){
        super(driver);

        waitUntilPageLoaded();

        locators = new ArticleDetailPageLocators(driver);
    }

    /**
     * Clicks on the top back button
     * @return the ArticleListPage
     */
    public ArticleListPage clickBackButton(){
        locators.backButton.click();
        return new ArticleListPage(driver);
    }

    /**
     * Clicks on the share button
     */
    @Step("Clicking on share button")
    public GmailPage clickShareButton(){
        locators.shareButton.click();
        return new GmailPage(driver);
    }

    public String getPageTitle(){
        return locators.pageTitle.getText();
    }

    public String getCreatedTime(){
        return locators.createdTime.getText();
    }

    public String getTitle(){
        return locators.articleTitle.getText();
    }

    public String getDescription(){
        return locators.articleDescription.getText();
    }

    /**
     * Returns the author as shown on the ui
     * @return the author
     */
    @Step("Trying to get author of the detail page")
    public String getAuthor(){
        MobileElement element;
        try{
                element = driver.findElement(MobileBy.AndroidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true))" +
                            ".scrollIntoView(new UiSelector().textContains(\"author\"))"));
        } catch (NoSuchElementException ex){
            //If it fails try once more with a manual scrolldown
            Gestures.scrollDown(driver);
            element = driver.findElement(MobileBy.AndroidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true))" +
                            ".scrollIntoView(new UiSelector().textContains(\"author\"))"));
        }

        return element.getText();
    }

    /**
     * Returns only the email part of the author
     * @return the email of the author
     */
    public String getAuthorEmail(){
        return getAuthor().replace("author: ", "");
    }

    /**
     * Waits until the description element is visible
     */
    private void waitUntilPageLoaded(){
        WebDriverWait wait = new WebDriverWait(this.driver, 2);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.ScrollView/android.view.View[3]")));
    }
}
