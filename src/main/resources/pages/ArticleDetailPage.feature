Feature: Article Detail page features

  Background:
    Given user opens viesure application


  @regression @showcase
  Scenario Outline: All selected articles can navigate to gmail app
    When user clicks on article with "<title>"
    Then article detail page opens
    When user checks the name of the author and the title
    And user clicks on the share button
    Then the app should switch to gmail
    And user navigates back to viesure application

    Examples:
      | title                       |
      | Sharable maximized analyzer |
      | Focused mobile alliance     |
      | Realigned multimedia framework |

    @regression @gmailLoggedIn
    Scenario: Sharing the article fills the recipient and address fields in the gmail application
    When user clicks on a visible article
    Then article detail page opens
    When user checks the name of the author and the title
    And user clicks on the share button
    Then the app should switch to gmail
    And the recipient and subject fields are filled
    And user navigates back to viesure application

