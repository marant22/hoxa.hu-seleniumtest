# Selenium Test Project

This is a Selenium Test Project, which uses Selenium WebDriver to automate browser activities and perform UI testing on the website www.hoxa.hu.

## Project Structure

The key classes are:

1. **MainPage.java:** Represents the main page of the website.
2. **LoggedInPage.java:** Represents the page shown after a successful login.
3. **SearchResultPage.java:** Represents the page showing the search results.
4. **ProfilePage.java:** Represents the user's profile page.
5. **SeleniumTest.java:** Contains the Selenium WebDriver code to run tests.


## Tests Included

The Selenium tests include:

1. **Login Test:** Checks the login functionality.
2. **Logout Test:** Checks the logout functionality.
3. **Profile Settings Test:** Checks the user's profile settings functionality.
4. **Search Test:** Tests the search functionality of the website.
5. **Static Page Test:** Verifies the display of static pages.
6. **Browser Back Button Test:** Tests the browser's back button functionality.
7. **Multiple Static Pages Test:** Checks multiple static pages loading from a configuration file.

## Note

All tests are independent and can be run in any order. Each test uses its own WebDriver instance, and the browser is closed at the end of each test. In case of test failures, exceptions will be thrown which can be used to troubleshoot the issue.
