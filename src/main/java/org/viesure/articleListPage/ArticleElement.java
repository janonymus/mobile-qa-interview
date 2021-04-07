package org.viesure.articleListPage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.viesure.articleDetailPage.ArticleDetailPage;

public class ArticleElement implements Comparable<ArticleElement>{
    String title;
    String author;
    String release_date;

    WebElement element;
    AndroidDriver<AndroidElement> driver;

    public ArticleElement(WebElement element, AndroidDriver<AndroidElement> driver){
        this.element = element;
        this.driver = driver;

        initProperties();
    }

    /**
     * Splits the text shown on the UI
     * into title, author and release date
     */
    public void initProperties(){
        String elementText= element.getText();
        String[] split = elementText.split(",");
        title = split[0].trim();
        author = split[1];
        release_date = split[2].trim();
    }

    public String getTitle() {
        return title;
    }

    /**
     * Returns the author text as shown on the UI
     * @return the Author text
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Returns only the email part of the author
     * @return the author email
     */
    public String getAuthorEmail() {
        return author.replace("author:","").trim();
    }

    public String getRelease_date() {
        return release_date;
    }

    /**
     * Returns the raw webelement of the article
     * @return article WebElement
     */
    public WebElement getElement() {
        return element;
    }

    /**
     * Clicks on the article and gets navigated to the
     * Article Detail page
     * @return the navigated ArticleDetailPage
     */
    public ArticleDetailPage clickOnArticle(){
        this.element.click();
        return new ArticleDetailPage(driver);
    }

    @Override
    public String toString(){
        return "Title:\t" + this.title +
                "\nAuthor:\t" + this.author +
                "\nRelease Date:\t" + this.release_date;
    }

    @Override
    public int compareTo(ArticleElement articleElement) {
        //comparing the getText as it returns a concatenated string unique to the element
        return this.element.getText().compareTo(articleElement.element.getText());
    }
}
