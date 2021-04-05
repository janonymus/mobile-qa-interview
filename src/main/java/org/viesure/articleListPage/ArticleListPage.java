package org.viesure.articleListPage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.viesure.base.BasePage;
import org.viesure.utils.Gestures;

import java.util.ArrayList;
import java.util.List;


public class ArticleListPage extends BasePage {

    private ArticleListPageLocators locators;
    private List<ArticleElement> visibleArticleList;

    public ArticleListPage(AndroidDriver<AndroidElement> driver){
        super(driver);

        locators = new ArticleListPageLocators(driver);

        waitUntilPageIsPopulated();
        generateVisibleArticleList();
    }

    @Step("Getting list of currently visible articles")
    public List<ArticleElement> getVisibleArticles(){
        return generateVisibleArticleList();
    }

    @Step("Waiting until List page is populated")
    private void waitUntilPageIsPopulated(){
        WebDriverWait wait = new WebDriverWait(this.driver, 10);
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//android.view.ViewGroup/android.view.View/android.view.View"),1));
    }

    public void scrollUntilBottomOfPage(){
        List<ArticleElement> currentlyVisibleList = generateVisibleArticleList();

        ArticleElement currentLastElement = currentlyVisibleList.get(currentlyVisibleList.size()-1);
        //They have to differ when started
        ArticleElement scrolledLastElement = currentlyVisibleList.get(0);


        while (currentLastElement.compareTo(scrolledLastElement) != 0){
            currentlyVisibleList = generateVisibleArticleList();
            currentLastElement = currentlyVisibleList.get(currentlyVisibleList.size()-1);

            Gestures.scrollDown(driver);

            currentlyVisibleList = generateVisibleArticleList();
            scrolledLastElement = currentlyVisibleList.get(currentlyVisibleList.size()-1);
        }
    }

    public void scrollUntilTopOfPage(){
        List<ArticleElement> currentlyVisibleList = generateVisibleArticleList();

        ArticleElement currentFirstElement = currentlyVisibleList.get(0);
        //They have to differ when started
        ArticleElement scrolledFirstElement = currentlyVisibleList.get(currentlyVisibleList.size()-1);


        while (currentFirstElement.compareTo(scrolledFirstElement) != 0){
            currentlyVisibleList = generateVisibleArticleList();
            currentFirstElement = currentlyVisibleList.get(0);

            Gestures.scrollUp(driver);

            currentlyVisibleList = generateVisibleArticleList();
            scrolledFirstElement = currentlyVisibleList.get(0);
        }
    }


    private List<ArticleElement> generateVisibleArticleList(){
        visibleArticleList = new ArrayList<>();
        for (WebElement article: locators.articles){
            if (!article.getText().contains("Dummy articles")){
                this.visibleArticleList.add(new ArticleElement(article, driver));
            }
        }
        return visibleArticleList;
    }
}