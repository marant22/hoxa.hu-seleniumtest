import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

class MainPage extends PageBase {
    private static final int WAIT_TIME_IN_SECONDS = 30;

    private By loginBtn = By.id("blp_sbmt");
    private By userInput = By.name("nev");
    private By passwdInput = By.name("jelszo");
    private By searchBarBy = By.xpath("//input[@name='keres']");

    public MainPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://www.hoxa.hu/");
        driver.manage().timeouts().implicitlyWait(WAIT_TIME_IN_SECONDS, TimeUnit.SECONDS);
        clickOnCookieConsentButton();
    }

    // Method to log in the user with the provided email and password
    public LogedInPage login(String email, String passwd) {
        this.waitAndReturnElement(userInput).sendKeys(email);
        this.waitAndReturnElement(passwdInput).sendKeys(passwd);
        this.waitAndReturnElement(loginBtn).click();
        return new LogedInPage(driver);
    }

    // Check if the user is logged out by verifying the presence of the login button
    public boolean isLoggedOut() {
        return isElementPresent(loginBtn);
    }

    // Handle the cookie consent popup that appears when first visiting the site
    private void clickOnCookieConsentButton() {
        try {
            WebElement cookieConsentButton = driver.findElement(By.xpath("//button[contains(text(), 'Elfogadom')]"));
            cookieConsentButton.click();
        } catch (NoSuchElementException e) {
            // If the cookie consent button is not present, no action is required
        }
    }

    // Perform a search using the provided query and return the result page
    public SearchResultPage search(String searchQuery) {
        this.waitAndReturnElement(searchBarBy).sendKeys(searchQuery + "\n");
        return new SearchResultPage(this.driver);
    }
}
