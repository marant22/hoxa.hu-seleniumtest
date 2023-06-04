import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.LocalFileDetector;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;


import java.io.File;
import java.nio.file.Paths;

class ProfilePage extends PageBase {
    private By writeMottoBtn = By
            .xpath("//span[contains(@onclick, 'mottomodositas') and contains(@class, 'ikon32_49')]");
    private By mottoTextarea = By.xpath("//textarea[@name='motto']");
    private By editMottoBtn = By.id("fa_sbmt");
    private By mottoContent = By.xpath("//div[@id='adatlap']//em");
    private By fileUploadBtn = By.xpath("//a[contains(@onclick, 'adatlapkep.php')]");
    private By fileInput = By.xpath("//input[@type='file']");
    private By submitBtn = By.xpath("//button[@id='felhasz_kepek_sbmt']");
    private By descriptionInput = By.name("leiras");
    private By fileSelect = By.name("tipus");
    private By deleteBtn = By.xpath("//span[contains(@onclick,'adatlapkeptorles')]");

    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    public String WriteMotto(String text) {
        waitAndReturnElement(writeMottoBtn).click();
        WebElement textarea = waitAndReturnElement(mottoTextarea);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", textarea);
        textarea.clear();
        textarea.sendKeys(text);
        waitAndReturnElement(editMottoBtn).click();
        WebElement mottoElement = waitAndReturnElement(mottoContent);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", mottoElement);
        String motto = mottoElement.getText();

        return motto;
    }

    public void upload() {
        // Assume driver is a RemoteWebDriver instance.
        if (driver instanceof RemoteWebDriver) {
            ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
        }

        WebElement btn = waitAndReturnElement(fileUploadBtn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btn);
        btn.click();

        // Locate the textarea and fill it
        WebElement description = waitAndReturnElement(descriptionInput);
        description.sendKeys("This is a test comment");

        File file = new File("src/test/IMG_20220227_231658.jpg");
        String absolutePath = file.getAbsolutePath();

        if (!file.exists()) {
            System.out.println("File does not exist at " + absolutePath);
            return;
        }

        WebElement input = waitAndReturnElement(fileInput);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", input);
        input.sendKeys(absolutePath);

        Select dropdown = new Select(waitAndReturnElement(fileSelect));
        dropdown.selectByIndex(1);

        waitAndReturnElement(submitBtn).click();

        // Wait for 10 seconds to observe the result
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void deletePhoto() {
        // Find the delete button by XPath and click it
        WebElement btn = waitAndReturnElement(deleteBtn);
        btn.click();

       
        // Switch to the alert and accept it
        WebDriverWait wait = new WebDriverWait(driver, 10);
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();

        // Wait until the photo element is removed from the DOM
        wait.until(ExpectedConditions.stalenessOf(btn));
    }

}
