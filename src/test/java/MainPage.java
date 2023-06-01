import java.util.concurrent.TimeUnit;

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


class MainPage extends PageBase {


    private By loginBtn = By.id("blp_sbmt");
    private By userInput = By.name("nev");
    private By passwdInput = By.name("jelszo");
 
    
    public MainPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://www.hoxa.hu/");
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        clickOnCookieConsentButton();
    }
    
    public LogedInPage login(String email, String passwd){
        this.waitAndReturnElement(userInput).sendKeys(email);
        this.waitAndReturnElement(passwdInput).sendKeys(passwd);
        this.waitAndReturnElement(loginBtn).click();
      
        return new LogedInPage(driver);
    }

    public boolean isLogedOut() {
        return isElementPresent(loginBtn);
    }


    
    private void clickOnCookieConsentButton() {
        try {
            WebElement cookieConsentButton = driver.findElement(By.xpath("//button[contains(text(), 'Elfogadom')]"));            ;
            cookieConsentButton.click();
        } catch (NoSuchElementException e) {
            // Element not found, no action needed
        }
    }
    

}
