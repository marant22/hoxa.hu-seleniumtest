import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;


class PageBase {
    protected WebDriver driver;
    protected WebDriverWait wait;

    private static final By FOOTER_LOCATOR = By.id("siteinfo");
    private static final By BODY_LOCATOR = By.tagName("body");
    private static final By H1_LOCATOR = By.tagName("h1");

    public PageBase(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    /**
     * Waits for the element to be visible and then returns it.
     *
     * @param locator The locator of the element to find.
     * @return The web element found.
     */
    protected WebElement waitAndReturnElement(By locator) {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this.driver.findElement(locator);
    }

    /**
     * Returns the text of the body element.
     *
     * @return The text of the body element.
     */
    public String getBodyText() {
        return this.waitAndReturnElement(BODY_LOCATOR).getText();
    }

    /**
     * Returns the text of the footer element.
     *
     * @return The text of the footer element.
     */
    public String getFooterText() {
        return this.waitAndReturnElement(FOOTER_LOCATOR).getText();
    }

    /**
     * Returns the text of the first H1 element.
     *
     * @return The text of the first H1 element.
     */
    public String getH1Text() {
        return this.waitAndReturnElement(H1_LOCATOR).getText();
    }

    /**
     * Checks if an element is present in the current context (e.g., on the current page).
     *
     * @param locator The locator of the element to find.
     * @return true if the element is present, false otherwise.
     */
    protected boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
