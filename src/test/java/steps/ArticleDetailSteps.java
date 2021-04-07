package steps;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.viesure.articleDetailPage.ArticleDetailPage;
import org.viesure.gmailPage.GmailPage;

public class ArticleDetailSteps {
    private AndroidDriver<AndroidElement> driver;

    ArticleDetailPage selectedDetailPage;
    GmailPage gmailPage;

    String authorEmail;
    String articleTitle;

    public ArticleDetailSteps(GlobalHooks globalHooks){
        this.driver = globalHooks.getDriver();

        selectedDetailPage = new ArticleDetailPage(driver);
    }

    @When("user clicks on the share button")
    @Step("user clicks on the share button")
    public void userClicksOnTheShareButton() {
        gmailPage = selectedDetailPage.clickShareButton();
    }

    @Then("the app should switch to gmail")
    @Step("the app should switch to gmail")
    public void theAppShouldSwitchToGmail() {
        String composeActivity = ".ComposeActivityGmail";
        String composeActivityExternal = ".ComposeActivityGmailExternal";

        String currentActivity = gmailPage.getCurrentActivity();

        Assert.assertTrue( currentActivity.equals(composeActivityExternal)|| currentActivity.equals(composeActivity), "Current app activity is not a compose activity" + driver.currentActivity());
    }

    @When("user checks the name of the author and the title")
    @Step("user checks the name of the author and the title")
    public void userChecksTheNameOfTheAuthorAndTheTitle() {
        this.articleTitle = selectedDetailPage.getTitle();
        this.authorEmail = selectedDetailPage.getAuthorEmail();
    }

    @And("the recipient and subject fields are filled")
    @Step("the recipient and subject fields are filled")
    public void theRecipientAndSubjectFieldsAreFilled() {
        CommonVerifyers.verifyGmailFilledCorrectly(authorEmail,articleTitle, gmailPage);
    }

    @And("user navigates back to viesure application")
    @Step("user navigates back to viesure application")
    public void userNavigatesBackToViesureApplication() {
        //First one closes the keyboard, second navigates back
        driver.navigate().back();
        driver.navigate().back();
    }
}
