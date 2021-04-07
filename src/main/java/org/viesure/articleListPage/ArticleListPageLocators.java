package org.viesure.articleListPage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.viesure.base.BaseLocator;

import java.util.List;

public class ArticleListPageLocators extends BaseLocator {

    @AndroidFindBy(id = "io.viesure.qa:id/action_bar_root")
    WebElement action_bar_root;

    @AndroidFindBy(id = "android:id/content")
    WebElement content;

    @AndroidFindBy(xpath = "//android.view.ViewGroup/android.view.View/android.view.View")
    List<WebElement> articles;

    public ArticleListPageLocators(AndroidDriver<AndroidElement> driver){
        super(driver);
    }
}
