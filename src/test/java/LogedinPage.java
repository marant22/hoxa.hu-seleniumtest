import org.junit.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;


class LogedInPage extends PageBase {

    private By logoutBtn = By.xpath("//a[@href='/kilepes']");

    public LogedInPage(WebDriver driver) {
        super(driver);
    }    

    public boolean isLogedIn() {
        return isElementPresent(logoutBtn);
    }

    public MainPage logout() {
        this.waitAndReturnElement(logoutBtn).click();

        return new MainPage(driver);

    }
           
}
