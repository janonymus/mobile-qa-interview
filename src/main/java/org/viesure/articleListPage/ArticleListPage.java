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

    /**
     * Returns the articles that are currently visible on the UI
     * @return The list of visible articles
     */
    public List<ArticleElement> getVisibleArticles(){
        return generateVisibleArticleList();
    }

    /**
     * Waits until there are at least 1 article loaded and is visible
     */
    private void waitUntilPageIsPopulated(){
        WebDriverWait wait = new WebDriverWait(this.driver, 10);
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//android.view.ViewGroup/android.view.View/android.view.View"),1));
    }

    /**
     * Scrolls until the bottom of the page (list)
     */
    public void scrollToBottom(){
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

    /**
     * Scrolls until the top of the page (list)
     */
    public void scrollToTop(){
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


    /**
     * Generates the list of visible ArticleElements
     * And removes the dummy element
     * @return the list of visible articles
     */
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
