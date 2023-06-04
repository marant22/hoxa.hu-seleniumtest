import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.io.File;

/**
 * Represents the profile page for a user, providing methods to interact with various page elements.
 * It extends the PageBase class, which contains shared functionality for all pages.
 */
class ProfilePage extends PageBase {

    // Locators for various page elements
    private By writeMottoBtn = By.xpath("//span[contains(@onclick, 'mottomodositas') and contains(@class, 'ikon32_49')]");
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

    /**
     * This method allows a user to write a motto on their profile page.
     * @param text The motto text to be entered
     * @return The motto text that was entered
     */
    public String writeMotto(String text) {
        // Navigate to the motto area and enter the new motto
        waitAndReturnElement(writeMottoBtn).click();
        WebElement textarea = waitAndReturnElement(mottoTextarea);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", textarea);
        textarea.clear();
        textarea.sendKeys(text);

        // Submit the new motto and return the displayed text
        waitAndReturnElement(editMottoBtn).click();
        WebElement mottoElement = waitAndReturnElement(mottoContent);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", mottoElement);
        return mottoElement.getText();
    }

    /**
     * This method allows a user to upload a photo to their profile.
     */
    public void uploadPhoto() {
        // Enable file detection for RemoteWebDriver instances
        if (driver instanceof RemoteWebDriver) {
            ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
        }

        // Navigate to the file upload button and click it
        WebElement uploadButton = waitAndReturnElement(fileUploadBtn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", uploadButton);
        uploadButton.click();

        // Populate the file input and submit the form
        WebElement descriptionField = waitAndReturnElement(descriptionInput);
        descriptionField.sendKeys("This is a test comment");
        File file = new File("src/test/IMG_20220227_231658.jpg");
        if (!file.exists()) {
            System.out.println("File does not exist at " + file.getAbsolutePath());
            return;
        }
        WebElement inputFile = waitAndReturnElement(fileInput);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", inputFile);
        inputFile.sendKeys(file.getAbsolutePath());
        Select dropdown = new Select(waitAndReturnElement(fileSelect));
        dropdown.selectByIndex(1);
        waitAndReturnElement(submitBtn).click();
    }

    /**
     * This method allows a user to delete a photo from their profile.
     */
    public void deletePhoto() {
        // Navigate to the delete button and click it
        WebElement deleteButton = waitAndReturnElement(deleteBtn);
        deleteButton.click();

        // Handle the confirmation alert
        WebDriverWait wait = new WebDriverWait(driver, 10);
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();

        // Wait until the photo element is removed from the DOM
        wait.until(ExpectedConditions.stalenessOf(deleteButton));
    }
}
