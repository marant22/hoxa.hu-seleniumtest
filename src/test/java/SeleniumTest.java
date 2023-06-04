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

    // @Test
    // public void testLogin() {
    //     // Test login functionality
    //     MainPage mainPage = new MainPage(this.driver);
    //     Assert.assertTrue(mainPage.getFooterText().contains("2005-2023, www.hoxa.hu"));
    //     LogedInPage logedinPage = mainPage.login("teszt98", "passwd");
    //     Assert.assertTrue(logedinPage.isLogedIn());
    // }

    // @Test
    // public void testLogout() {
    //     // Test logout functionality
    //     MainPage mainPage = new MainPage(this.driver);
    //     Assert.assertTrue(mainPage.getFooterText().contains("2005-2023, www.hoxa.hu"));
    //     LogedInPage logedinPage = mainPage.login("teszt98", "passwd");
    //     mainPage = logedinPage.logout();
    //     Assert.assertTrue(mainPage.isLogedOut());
    // }
    @Test
    public void testProfileSetting() {
        MainPage mainPage = new MainPage(this.driver);
        Assert.assertTrue(mainPage.getFooterText().contains("2005-2023, www.hoxa.hu"));
        LogedInPage logedinPage = mainPage.login("teszt98", "passwd");
        WebElement profileLink = logedinPage.waitAndReturnElement(By.xpath("//a[text()='Profilod']"));
        profileLink.click();
        WebDriverWait wait = new WebDriverWait(driver, 10); // Create WebDriverWait instance to wait for URL to change
        wait.until(ExpectedConditions.urlToBe("https://www.hoxa.hu/adatlap-578160")); // Wait until the URL changes to the profile page URL
        ProfilePage profilePage = new ProfilePage(driver);
        String h1Text = profilePage.getH1Text();
        System.out.println(h1Text);
        Assert.assertTrue(h1Text.contains("Teszt98 adatlapja"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String formattedDate = dateFormat.format(new Date());
        String text = "This is a test message " + formattedDate;
        String returnedText = profilePage.WriteMotto(text);
        Assert.assertEquals(text.substring(0, 16), returnedText.substring(0, 16)); // compare up to the minute
        profilePage.upload();
        Assert.assertTrue(profilePage.isElementPresent(By.xpath("//span[contains(@class, 'ikon48_6') and contains(@onclick,'/jquery/profilkepnek.php?id=')]")));
        profilePage.deletePhoto();
    }
    
    

    // @Test
    // public void testSearch() {
    //     // Test search functionality
    //     MainPage mainPage = new MainPage(this.driver);
    //     Assert.assertTrue(mainPage.getFooterText().contains("2005-2023, www.hoxa.hu"));
    //     SearchResultPage searchResultPage = mainPage.search("Teszt");
    //     String bodyText = searchResultPage.getBodyText();
    //     System.out.println(bodyText);
    //     Assert.assertTrue(bodyText.contains("Teszt"));
    // }

    // @Test
    // public void testStaticPage() {
    //     // Test static page navigation
    //     MainPage mainPage = new MainPage(this.driver);
    //     Assert.assertTrue(mainPage.getFooterText().contains("2005-2023, www.hoxa.hu"));
    //     WebElement recipeLink = mainPage.waitAndReturnElement(By.xpath("//a[text()='Receptek']"));
    //     recipeLink.click();
    //     String h1Text = mainPage.getH1Text();
    //     System.out.println(h1Text);
    //     Assert.assertTrue(h1Text.contains("Receptek"));
    // }

    // @Test
    // public void testStaticPages() throws IOException {
    //     // Test loading multiple static pages from a configuration file
    //     Properties config = loadConfigFile();

    //     for (String key : config.stringPropertyNames()) {
    //         String url = config.getProperty(key);
    //         driver.get(url);
    //         MainPage mainPage = new MainPage(driver);
    //         Assert.assertTrue("Footer should contain expected text",
    //                 mainPage.getFooterText().contains("2005-2023, www.hoxa.hu"));
    //         System.out.println("Page loaded successfully: " + url);
    //     }
    // }

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
