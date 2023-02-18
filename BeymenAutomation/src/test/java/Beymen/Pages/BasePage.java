package Beymen.Pages;

import Beymen.Tests.BaseTest;
import Beymen.Utilities.DriverManager;
import Beymen.Utilities.ElementHelper;
import Beymen.Utilities.ReadExcel;
import com.aventstack.extentreports.ExtentTest;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

public class BasePage {

    WebDriver driver;
    WebDriverWait wait;
    ElementHelper elementHelper;
    ExtentTest logger;


    @FindBy(xpath = "//*[@id='onetrust-accept-btn-handler']")
    public WebElement acceptBtn;

    @FindBy(xpath = "//*[@class='o-header__logo']")
    public WebElement logo;

    @FindBy(xpath = "//*[contains(@class,'default-input o-header__search--input')]")
    public WebElement searchInput;

    @FindBy(xpath = "//*[@class='o-modal__container']/div/button/*")
    public WebElement closePopup;


    public BasePage() {
        this.driver = BaseTest.driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, 10);
        this.elementHelper = new ElementHelper(driver);
        this.logger = BaseTest.logger;

    }


    public void openPage() throws IOException {
        try {

            DriverManager.initializeDriver();
            elementHelper.checkElementClickable(acceptBtn);
            elementHelper.click(acceptBtn); // Çerezler kabul edilir
            elementHelper.checkElementClickable(closePopup);
            elementHelper.click(closePopup); // Pop-up kapatılır


        } catch (Exception e) {
            elementHelper.testCaseFailed();
            throw new RuntimeException(e);
        }
    }

    public void checkPage() throws IOException {
        try {
            elementHelper.checkElementVisible(logo);
            String actualTitle = elementHelper.getAttribute(logo, "title");
            String expectedTitle = "Beymen";
            Assert.assertEquals(expectedTitle, actualTitle);
            elementHelper.testCasePassed();
        } catch (Exception e) {
            elementHelper.testCaseFailed();
            throw new RuntimeException(e);
        }
    }


    public void searchInInput(int column, String expectedText) throws IOException {
        try {
            elementHelper.sendKey(searchInput, ReadExcel.readExcel(column));
            String actualText = elementHelper.getAttribute(searchInput, "value");
            Assert.assertEquals(expectedText, actualText);
            elementHelper.testCasePassed();

        } catch (Exception e) {
            elementHelper.testCaseFailed();
            throw new RuntimeException(e);
        }
    }

    public void clearSearch() throws IOException {
        try {

            elementHelper.sendKey(searchInput, "" + Keys.SHIFT + Keys.ARROW_UP + Keys.DELETE);
            String actualText = elementHelper.getAttribute(searchInput, "value");
            Assert.assertEquals("", actualText);
        } catch (Exception e) {
            elementHelper.testCaseFailed();
            throw new RuntimeException(e);
        }
    }

    public void pressEnter() throws IOException {
        try {
            elementHelper.sendKey(searchInput, "" + Keys.ENTER);
            elementHelper.testCasePassed();
        } catch (Exception e) {
            elementHelper.testCaseFailed();
            throw new RuntimeException(e);
        }
    }

}