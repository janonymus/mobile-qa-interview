package steps;

import io.qameta.allure.Step;
import org.testng.Assert;
import org.viesure.articleDetailPage.ArticleDetailPage;
import org.viesure.articleListPage.ArticleElement;
import org.viesure.common.Article;
import org.viesure.gmailPage.GmailPage;
import org.viesure.utils.AllureLogger;

import java.util.List;

public class CommonVerifyers {

    @Step("Verifying article list item content")
    public static void verifyArticleListItem(ArticleElement actualArticle, Article expectedArticle){
        Assert.assertEquals(actualArticle.getTitle(), expectedArticle.getTitle(),"Testing if title is correct");
        Assert.assertEquals(actualArticle.getRelease_date(), expectedArticle.getRelease_date(),"Testing if release date is correct");
        Assert.assertEquals(actualArticle.getAuthorEmail(), expectedArticle.getAuthor(),"Testing if author is correct");
    }

    @Step("Verifying article detail content")
    public static void verifyArticleDetail(ArticleDetailPage detailPage, Article expectedArticle){
        Assert.assertEquals(detailPage.getPageTitle(), expectedArticle.getTitle(),"Testing if page title is correct");
        Assert.assertEquals(detailPage.getTitle(), expectedArticle.getTitle(),"Testing if title is correct");
        Assert.assertEquals(detailPage.getCreatedTime(), expectedArticle.getRelease_date(),"Testing if release date is correct");
        Assert.assertEquals(detailPage.getDescription(), expectedArticle.getDescription(),"Testing if description is correct");
        Assert.assertEquals(detailPage.getAuthorEmail(), expectedArticle.getAuthor(),"Testing if author is correct");
    }

    @Step("Verifying filled email data")
    public static void verifyMailData(Article actualArticle, GmailPage gmailPage){
        verifyGmailFilledCorrectly(actualArticle.getAuthor(), actualArticle.getTitle(), gmailPage);
    }

    @Step("Verifying that no error has ben recorded during test")
    public static void verifyErrorListIsEmpty(List<Exception> errorList){
        //In case error list is not empty, logging all recorded errors to allure
        for(Exception exception: errorList){
            AllureLogger.saveTextLog(exception.getLocalizedMessage());
        }
        //Using equals so we can see in the logs how many errors happened
        Assert.assertEquals(errorList.size(),0,"The list of errors should be empty");
    }

    public static void verifyGmailFilledCorrectly(String expectedAuthor, String expectedTitle, GmailPage gmailPage){
        String actualRecipientText = "<" + expectedAuthor + ">, ";

        Assert.assertEquals(gmailPage.getRecipient(), actualRecipientText, "Testing if actual recipient equals expected recipient");
        Assert.assertEquals(gmailPage.getSubject(), expectedTitle, "Testing if subject equals to the title of the article");
        Assert.assertTrue(gmailPage.getBody().isEmpty(), "Testing if email body is empty");
    }
}
