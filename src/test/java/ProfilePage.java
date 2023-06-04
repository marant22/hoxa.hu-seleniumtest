import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

class ProfilePage extends PageBase {
    private By writeMottoBtn = By.xpath("//span[contains(@onclick, 'mottomodositas') and contains(@class, 'ikon32_49')]");
    private By mottoTextarea = By.xpath("//textarea[@name='motto']");
    private By editMottoBtn = By.id("fa_sbmt");
    private By mottoContent = By.xpath("//div[@id='adatlap']//em");

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
}
