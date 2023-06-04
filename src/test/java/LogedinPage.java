import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;

/**
 * This class represents the page that a user sees after they have logged in.
 * It extends from the PageBase class, which contains shared functionality for all pages.
 */
class LogedInPage extends PageBase {
    // This locator is used to find the logout button on the page.
    private By logoutBtn = By.xpath("//a[@href='/kilepes']");

    /**
     * The constructor method for this class.
     * It calls the constructor of the superclass (PageBase) to set up the WebDriver.
     */
    public LogedInPage(WebDriver driver) {
        super(driver);
    }

    /**
     * This method checks if the user is logged in.
     * It checks for the presence of the logout button to determine this.
     * 
     * @return true if the logout button is present (i.e., the user is logged in), false otherwise.
     */
    public boolean isLogedIn() {
        return isElementPresent(logoutBtn);
    }

    /**
     * This method performs the logout action.
     * It first waits for the logout button to be clickable, clicks on it, and then returns a new instance of the MainPage.
     * 
     * @return a new MainPage instance.
     */
    public MainPage logout() {
        this.waitAndReturnElement(logoutBtn).click();
        return new MainPage(driver);
    }
}
