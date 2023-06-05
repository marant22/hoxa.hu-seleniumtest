import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.openqa.selenium.By;

import java.net.URL;
import java.net.MalformedURLException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SeleniumTest {
    public WebDriver driver;
    private SimpleDateFormat dateFormat;

    @Before
    public void setup() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        driver.manage().window().maximize();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    /**
     * This utility function checks if the footer contains the expected text
     */
    private void checkFooter() {
        MainPage mainPage = new MainPage(this.driver);
        Assert.assertTrue(mainPage.getFooterText().contains("2005-2023, www.hoxa.hu"));
    }

    /**
     * This utility function logs in and returns the logged-in page
     */
    private LogedInPage login() {
        MainPage mainPage = new MainPage(this.driver);
        return mainPage.login("teszt98", "passwd");
    }

    @Test
    public void testLogin() {
        checkFooter();
        LogedInPage logedinPage = login();
        Assert.assertTrue(logedinPage.isLogedIn());
    }

    @Test
    public void testLogout() {
        checkFooter();
        LogedInPage logedinPage = login();
        MainPage mainPage = logedinPage.logout();
        Assert.assertTrue(mainPage.isLoggedOut());
    }

    @Test
    public void testProfileSetting() {
        checkFooter();
        LogedInPage logedinPage = login();
        WebElement profileLink = logedinPage.waitAndReturnElement(By.xpath("//a[text()='Profilod']"));
        profileLink.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.urlToBe("https://www.hoxa.hu/adatlap-578160"));
        ProfilePage profilePage = new ProfilePage(driver);
        String h1Text = profilePage.getH1Text();
        System.out.println(h1Text);
        Assert.assertTrue(h1Text.contains("Teszt98 adatlapja"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String formattedDate = dateFormat.format(new Date());
        String text = "This is a test message " + formattedDate;
        String returnedText = profilePage.writeMotto(text);
        profilePage = new ProfilePage(driver); // re-initialize ProfilePage after page refresh
        Assert.assertEquals(text.substring(0, 16), returnedText.substring(0, 16)); 
        profilePage.uploadPhoto();
        Assert.assertTrue(profilePage.isElementPresent(By.xpath("//span[contains(@class, 'ikon48_6') and contains(@onclick,'/jquery/profilkepnek.php?id=')]")));
        profilePage.deletePhoto();
    }

    @Test
    public void testSearch() {
        checkFooter();
        MainPage mainPage = new MainPage(this.driver);
        SearchResultPage searchResultPage = mainPage.search("Teszt");
        String bodyText = searchResultPage.getBodyText();
        System.out.println(bodyText);
        Assert.assertTrue(bodyText.contains("Teszt"));
    }

    @Test
    public void testStaticPage() {
        checkFooter();
        MainPage mainPage = new MainPage(this.driver);
        WebElement recipeLink = mainPage.waitAndReturnElement(By.xpath("//a[text()='Receptek']"));
        recipeLink.click();
        String h1Text = mainPage.getH1Text();
        System.out.println(h1Text);
        Assert.assertTrue(h1Text.contains("Receptek"));
    }

    @Test
    public void testBrowserBackButton() {
        checkFooter();
        MainPage mainPage = new MainPage(this.driver);
        String urlBeforeNavigation = driver.getCurrentUrl();
        WebElement recipeLink = mainPage.waitAndReturnElement(By.xpath("//a[text()='Receptek']"));
        recipeLink.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(urlBeforeNavigation)));
        driver.navigate().back();
        wait.until(ExpectedConditions.urlToBe(urlBeforeNavigation));
        String urlAfterNavigation = driver.getCurrentUrl();
        Assert.assertEquals(urlBeforeNavigation, urlAfterNavigation);
    }

    @Test
    public void testStaticPages() throws IOException {
        Properties config = loadConfigFile();

        for (String key : config.stringPropertyNames()) {
            String url = config.getProperty(key);
            driver.get(url);
            checkFooter();
            System.out.println("Page loaded successfully: " + url);
        }
    }

    private Properties loadConfigFile() throws IOException {
        Properties config = new Properties();
        FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
        config.load(fis);
        fis.close();
        return config;
    }

    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
