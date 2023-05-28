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


    private By loginBtn = By.xpath("//a[@id='header-login-button']");
    private By userInput = By.xpath("//input[@name='username']");
    private By passwdInput = By.xpath("//input[@name='password']");
    private By loginSubmitBtn = By.xpath("//button[@data-auth-target='loginSubmitButton']");
    



    
    public MainPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://ingatlan.com/");
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.findElement(By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll")).click();
        driver.findElement(By.id("interstitial-close-button")).click();
    }    

    public void login(String email, String passwd){
        this.waitAndReturnElement(loginBtn).click();
        this.waitAndReturnElement(userInput).sendKeys(email);
        this.waitAndReturnElement(passwdInput).sendKeys(passwd);
        this.waitAndReturnElement(loginSubmitBtn).click();
        

    }
}
