package steps;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.viesure.articleDetailPage.ArticleDetailPage;
import org.viesure.articleListPage.ArticleElement;
import org.viesure.articleListPage.ArticleListPage;
import org.viesure.common.Article;
import org.viesure.utils.Networking;

import java.util.List;

public class ArticleListSteps {

    private AndroidDriver<AndroidElement> driver;

    ArticleListPage articleListPage;
    ArticleDetailPage selectedDetailPage;

    public ArticleListSteps(GlobalHooks globalHooks){
        this.driver = globalHooks.getDriver();
    }

    @Given("user opens viesure application")
    @Step("user opens viesure application")
    public void user_opens_viesure_application() {
        articleListPage = new ArticleListPage(driver);

        articleListPage.getVisibleArticles();
    }

    @Then("user can verify the visible articles")
    @Step("User can verify the visible articles")
    public void user_can_verify_the_visible_articles() {
        System.out.println("verify articles");
        List<Article> expectedArticles = Networking.getDummyData();
        List<ArticleElement> actualVisibleArticles = articleListPage.getVisibleArticles();

        for (int i=0; i< actualVisibleArticles.size(); i++){
            CommonVerifyers.verifyArticleListItem(actualVisibleArticles.get(i), expectedArticles.get(i));
        }

    }

    @When("user scrolls to the bottom")
    @Step("user scrolls to the bottom")
    public void userScrollsToTheBottom() {
        articleListPage.scrollToBottom();
    }

    @When("user scrolls to the top")
    @Step("user scrolls to the top")
    public void userScrollsToTheTop() {
        articleListPage.scrollToTop();
    }

    @Then("user can see the articles list")
    @Step("user can see the articles list")
    public void userCanSeeTheArticlesList() {
        Assert.assertFalse(articleListPage.getVisibleArticles().isEmpty());
    }

    @When("user clicks on a visible article")
    @Step("user clicks on a visible article")
    public void userClicksOnAVisibleArticle() {
        selectedDetailPage = articleListPage.getVisibleArticles().get(0).clickOnArticle();
    }

    @Then("article detail page opens")
    @Step("article detail page opens")
    public void articleDetailPageOpens() {
        Assert.assertNotNull(selectedDetailPage);
    }

    @When("user clicks on the detail page back button")
    @Step("user clicks on the detail page back button")
    public void userClicksOnTheDetailPageBackButton() {
        articleListPage= selectedDetailPage.clickBackButton();
        selectedDetailPage=null;

    }

    @When("user clicks on the hardware back button")
    @Step("user clicks on the hardware back button")
    public void userClicksOnTheHardwareBackButton() {
        driver.navigate().back();
        articleListPage = new ArticleListPage(driver);
        selectedDetailPage=null;
    }

    @When("user clicks on article with {string}")
    @Step("user clicks on article with {string}")
    public void userClicksOnArticleWith(String title) {
        for (ArticleElement article: articleListPage.getVisibleArticles()){
            if (article.getTitle().equals(title)){
                selectedDetailPage = article.clickOnArticle();
            }
        }
    }

    @And("title of the page is the same as the clicked {string}")
    @Step("title of the page is the same as the clicked {string}")
    public void titleOfThePageIsTheSameAsTheClicked(String title) {
        Assert.assertEquals(selectedDetailPage.getTitle(), title);
        Assert.assertEquals(selectedDetailPage.getPageTitle(), title);
    }

}
