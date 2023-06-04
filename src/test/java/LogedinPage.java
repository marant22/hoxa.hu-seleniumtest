import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;


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
