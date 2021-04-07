package org.viesure.articleDetailPage;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.viesure.base.BaseLocator;


public class ArticleDetailPageLocators extends BaseLocator {

    @AndroidFindBy(id = "io.viesure.qa:id/action_bar_root")
    WebElement action_bar_root;

    @AndroidFindBy(id = "android:id/content")
    WebElement content;

    @AndroidFindBy(accessibility = "Back")
    MobileElement backButton;

    @AndroidFindBy(accessibility = "Share")
    MobileElement shareButton;

    @AndroidFindBy(xpath = "//android.view.ViewGroup/android.view.View/android.view.View")
    WebElement pageTitle;

    @AndroidFindBy(xpath = "//android.widget.ScrollView/android.view.View[1]")
    WebElement createdTime;

    @AndroidFindBy(xpath = "//android.widget.ScrollView/android.view.View[2]")
    WebElement articleTitle;

    @AndroidFindBy(xpath = "//android.widget.ScrollView/android.view.View[3]")
    WebElement articleDescription;

    @AndroidFindBy(xpath = "//android.widget.ScrollView/android.view.View[4]")
    WebElement articleAuthor;

    public ArticleDetailPageLocators(AndroidDriver<AndroidElement> driver){
        super(driver);
    }
}
