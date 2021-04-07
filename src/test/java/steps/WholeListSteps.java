package steps;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.cucumber.java.en.Then;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.viesure.articleDetailPage.ArticleDetailPage;
import org.viesure.articleListPage.ArticleElement;
import org.viesure.articleListPage.ArticleListPage;
import org.viesure.common.Article;
import org.viesure.gmailPage.GmailPage;
import org.viesure.utils.AllureLogger;
import org.viesure.utils.Gestures;
import org.viesure.utils.Networking;

import java.util.ArrayList;
import java.util.List;

public class WholeListSteps {
    private AndroidDriver<AndroidElement> driver;

    ArticleListPage articleListPage;

    public WholeListSteps(GlobalHooks globalHooks){
        this.driver = globalHooks.getDriver();

        this.articleListPage = new ArticleListPage(driver);
    }

    @Then("user can click on every article")
    @Step("user can click on every article")
    public void userCanClickOnEveryArticle() {
        List<String> clickedArticleTitles= new ArrayList<>();
        List<ArticleElement> currentArticles;

        boolean articleSizeIncreased = true;

        List<Exception> exceptions = new ArrayList<>();

        do {
            int currentArticleSize = clickedArticleTitles.size();

            currentArticles = articleListPage.getVisibleArticles();
            for (ArticleElement article: currentArticles){
                if (!clickedArticleTitles.contains(article.getTitle())){
                    try{
                        ArticleDetailPage detailPage = article.clickOnArticle();
                        detailPage.clickBackButton();
                    } catch (Exception ex){
                        AllureLogger.saveTextLog("Exception happened on article: " + article.getTitle() + "\n" + ex.getLocalizedMessage());
                        AllureLogger.saveScreenshotPNG(driver);
                        exceptions.add(ex);
                    }
                    clickedArticleTitles.add(article.getTitle());
                }
            }
            Gestures.scrollDown(driver);

            if (clickedArticleTitles.size() == currentArticleSize){
                articleSizeIncreased = false;
            }
        } while (articleSizeIncreased);

        CommonVerifyers.verifyErrorListIsEmpty(exceptions);
    }

    @Then("every article and detail page information is correct")
    @Step("every article and detail page information is correct")
    public void everyArticleAndDetailPageInformationIsCorrect() {
        List<Article> expectedList = Networking.getDummyData();
        List<String> clickedArticleTitles= new ArrayList<>();
        List<ArticleElement> currentArticles;

        boolean articleSizeIncreased = true;
        int articleIndex = 0;

        List<Exception> exceptions = new ArrayList<>();

        do {
            int currentArticleSize = clickedArticleTitles.size();

            currentArticles = articleListPage.getVisibleArticles();
            for (ArticleElement article: currentArticles){
                if (!clickedArticleTitles.contains(article.getTitle())){
                    CommonVerifyers.verifyArticleListItem(article, expectedList.get(articleIndex));

                    try{
                        ArticleDetailPage detailPage = article.clickOnArticle();
                        CommonVerifyers.verifyArticleDetail(detailPage, expectedList.get(articleIndex));
                        detailPage.clickBackButton();
                    } catch (Exception ex){
                        AllureLogger.saveTextLog("Exception happened on article: " + article.getTitle() + "\n" + ex.getLocalizedMessage());
                        AllureLogger.saveScreenshotPNG(driver);
                        exceptions.add(ex);
                    }

                    clickedArticleTitles.add(article.getTitle());
                    //Incrementing article index after adding it to clicked article lsit
                    articleIndex++;
                }
            }
            Gestures.scrollDown(driver);

            //Check if the size of clicked articles have not increased increased
            if (clickedArticleTitles.size() == currentArticleSize){
                //Give exit condition
                articleSizeIncreased = false;
            }
        } while (articleSizeIncreased);

        CommonVerifyers.verifyErrorListIsEmpty(exceptions);
    }

    @Then("every shared article fills mail correctly")
    @Step("every shared article fills mail correctly")
    public void everySharedArticleFillsMailCorrectly() {
        List<Article> expectedList = Networking.getDummyData();
        List<String> clickedArticleTitles= new ArrayList<>();
        List<ArticleElement> currentArticles;

        boolean articleSizeIncreased = true;
        int articleIndex = 0;

        List<Exception> exceptions = new ArrayList<>();

        do {
            int currentArticleSize = clickedArticleTitles.size();

            currentArticles = articleListPage.getVisibleArticles();
            for (ArticleElement article: currentArticles){
                if (!clickedArticleTitles.contains(article.getTitle())){
                    try{
                        ArticleDetailPage detailPage = article.clickOnArticle();
                        GmailPage gmailPage = detailPage.clickShareButton();

                        CommonVerifyers.verifyMailData(expectedList.get(articleIndex), gmailPage);

                        //Close the keyboard and navigate back to viesure
                        driver.navigate().back();
                        driver.navigate().back();

                        detailPage.clickBackButton();
                    } catch (Exception ex){
                        AllureLogger.saveTextLog("Exception happened on article: " + article.getTitle() + "\n" + ex.getLocalizedMessage());
                        AllureLogger.saveScreenshotPNG(driver);
                        exceptions.add(ex);
                    }

                    clickedArticleTitles.add(article.getTitle());
                    //Incrementing article index after adding it to clicked article lsit
                    articleIndex++;
                }
            }
            Gestures.scrollDown(driver);

            //Check if the size of clicked articles have not increased increased
            if (clickedArticleTitles.size() == currentArticleSize){
                //Give exit condition
                articleSizeIncreased = false;
            }
        } while (articleSizeIncreased);

        CommonVerifyers.verifyErrorListIsEmpty(exceptions);

    }

    @Then("every article from backend are displayed")
    @Step("every article from backend are displayed")
    public void everyArticleFromBackendAreDisplayed() {
        List<Article> expectedList = Networking.getDummyData();
        List<String> observedArticleTitles= new ArrayList<>();
        List<ArticleElement> currentArticles;

        boolean articleSizeIncreased = true;
        int articleIndex = 0;

        do {
            int currentArticleSize = observedArticleTitles.size();

            currentArticles = articleListPage.getVisibleArticles();
            for (ArticleElement article: currentArticles){
                if (!observedArticleTitles.contains(article.getTitle())){

                    CommonVerifyers.verifyArticleListItem(article, expectedList.get(articleIndex));

                    observedArticleTitles.add(article.getTitle());
                    articleIndex++;
                }
            }
            Gestures.scrollDown(driver);

            if (observedArticleTitles.size() == currentArticleSize){
                //Give exit condition
                articleSizeIncreased = false;
            }
        } while (articleSizeIncreased);

        //After observing every displayed article, asserting if there was the same number of articles observed
        //as it comes from the backend
        Assert.assertEquals(observedArticleTitles.size(), expectedList.size(), "the number of displayed articles are not the same as it comes from backend");
    }
}
