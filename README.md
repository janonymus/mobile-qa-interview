# mobile-qa-interview hand-in readme

## Dependences used for the project:

* java-client
* testng
* allure-testng
* cucumber-testng
* cucumber java
* cucumber picocontainer (For Dependency Injection)
* rest assured (to make GET request to the mock backend)
* gson (parsing and converting response from rest assured)
* commons io (for copying directories for allure report history)

## The assignment

### prologue
Running the tests (on remote selenium grid) requires a selenium hub and nodes 
configured and running the emulators (or real devices). 
I had both the hub and the 2 nodes I used configured on my local machine, 
although having it run on a remote machine differs only in the URL used to initalize the drivers.

#### Setting up selenium grid (wether remote or local)
* The HUB requires selenium (Grid) standalone server downloaded `https://www.selenium.dev/downloads/`
  * Starting the server:`java -jar selenium-server.jar -role hub`
  
* I have used 2 nodes, configured for 2 differend android emulators
  * Node1: Pixel XL API 30
  * Node2: Nexus S API 24
  * To register nodes run: `appium -p 4728 --nodeconfig nexusConfig.json` 
    (Example command for setting up the node for the Nexus device)
  * Example node config JSON-s are attached in src/main/resources/properties folder
      * nexusConfig.json
      * pixelConfig.json
  *config hubHost, hubPort, port ...etc properties have to be changed in respect of the server's IP/port and the actual configs used

#### Maven, TestNG, Properties and configurations

When we start running our tests `paralellTestng.xml`, through maven the following will happen. 
The maven-surefire-plugin, will execute the file that is given to it inside the `<suiteXmlFile>` tags. here, 
I did not set any other parameters ( like run paralell or threadCount). 
these are set inside the `paralellTestng.xml` file (so there's no conflict).
For the cucumber tests, I have set up global hooks in `GlobalHooks.java` 
and the code execution will start in the @Before method called setUp.
This method moves the Allure report history, into the results folder,
and also initalizes my drivers. The drivers are being initalized by the given device parameter in `paralellTestng.xml`
(also if the parameter happens to be missing for some reason, the code defaults to 'pixel' ).
After getting the device parameter or the default device, based on the parameter 
a property file will be loaded with that device's data and used for the capabilities, and setting up the remote URL.

When this is done, the execution moves to execute the runner(s) defined in `paralellTestng.xml`. 
The current runner is the `RegressionTestRunner.java` that runs scenarions tagged with @regression

#### Cucumber

My **cucumber feature files** can be found in `src/main/resources/pages`.
* **ArticleList.feature** - contains scenarios related mainly to the list page
* **ArticleDetailPage.feature** - contains scenarios related to the detail page

For the scenarios I have added different tags, so different runners / run modes can be made upon requirement.
* **@regression** (all tests have this tag)
* **@slow** (tests that do tests something on the whole list page are tagged with this)
* **@gmailLoggedIn** (tests that require the gmail app to be logged in)
* **@backend** (tests that compare data with backend results)
* **@showcase** (tests where I used some extra cucumber features as a 'showcase' of understanding)

The **step definitions** for thease features can be found in: `src/test/java/steps`. 
Although step definitions could be placed theoretically in one class, 
I've placed them in some different classes for better readibility and reusability.
* **ArticleDetailSteps** (step implementation for the article details)
* **ArticleListSteps** (step implementation for article list test)
* **WholeListSteps** (step implementations for tests that do use all list item in the list page)

Other classes in this package `src/test/java/steps`
* **CommonVerifyers** - Used in most step definition classes to verify elements
* **GlobalHooks** - Global cucumber hooks are defined here, along with driver initalization and paralell 'thread handling'

**Test Runners** are found under `src/test/java/org/viesure/testRunner`
* Runners found here are using the feature files and glues them to the step definitions.
* Each runner file run differently tagged scenarios (eg. RegressionTestRunner runs scenarios with @regression)
* If needed multiple tags could be used in a single testRunner, I just did not use it currently

#### POM pattern

The Page Object Model design pattern was used (as it is still the best practice).
the Page Objects created can be found in `src/main/java/org/viesure`.

Page objects were created for:
* **ArticleListPage**
* **ArticleDetailPage**
* **GmailPage** (just a very minimalistic one to be able to check recipient, subject and body)

#### Helper classes

Some helper classes were created in the `src/main/java/org/viesure/utils` package to manage allure, 
or networking needs.
* **Allurelogger** - Used for adding text or screenshot attachments to Allure reports
* **FileUtil** - Used for loading device properties from resources, and to move allure history data
* **Gestures** - Gestures that were needed for tests ( like scrolling) are implemented here
* **Networking** - A class that uses `rest-assured` to load data from backend, and also parses it to Article type

#### Other notes

* Due to **time limitations**, I did not create any login flow for the gmail application, 
  I have just created a test account and logged in with it on the emulator.

## Final Test Results

During the automation tests, the following issues were found by the test automation:
* **scenario**: Possible to click on every article element in the list
  * **Decentralized transitional moderator** could not be clicked
* **scenario**:  Every article, and detail shows the same information as it comes from the backend
  * **Decentralized transitional moderator** could not be clicked
* **scenario**:  Every shared article fills the recipient and subject correctly
  * **Up-sized reciprocal application** Article's share button does not navigate to gmail app
* **scenario**:  Same data shown on the list as it's coming from the server
  * We are displaying 59 articles, but backend sends 60 articles
  
The tests were run paralelly on 2 nodes with 2 different API level Android emaultors. 
both node found and reported these same issues

* Upon reviewing error screenshots in the report, it was also observed that on some small devices,
the article list's created date is often not visible ( so a ticket could be created for this one)
  
* Not sure if I should mention but also on API 24 device, the app has a default icon instead of viesure's

## Final Test Run Report Screenshots

### Allure report overview screen
* Also added history and Environment part so it's more pleasing for the eye 
  (and can be useful in real situation)
  
![Launcher screen](./resources/allure_overview.png)

### Allure report categories screen
* Shows the different issue categories

![Launcher screen](./resources/allure_categories.png)

### Allure report packages screen
![Launcher screen](./resources/allure_packages.png)

### Allure report List item click error
![Launcher screen](./resources/error_ListItemClick.png)

### Allure report Share Button error
![Launcher screen](./resources/error_shareButtonClick.png)

### Allure report Less Article Then Expected error
* Also showing the regression test list for the nexus emulator

![Launcher screen](./resources/error_lessArticleThenExpected.png)

### Allure report regression test list for pixel
* Shows the regression test list for the pixel emulator

![Launcher screen](./resources/regression_on_pixel.png)



