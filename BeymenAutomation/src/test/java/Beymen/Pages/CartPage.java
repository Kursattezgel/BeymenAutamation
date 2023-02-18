package Beymen.Pages;

import Beymen.Tests.BaseTest;
import Beymen.Utilities.ElementHelper;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.IOException;
import java.util.List;

import static Beymen.Tests.BaseTest.log4j;

public class CartPage {

    WebDriver driver;
    WebDriverWait wait;
    ElementHelper elementHelper;


    @FindBy(xpath = "//*[@class='m-orderSummary__title']")
    public static WebElement orderSummaryTitle;

    @FindBy(xpath = "//*[@class='m-productPrice__salePrice']")
    public static WebElement productSalePrice;

    @FindBy(xpath = "//*[@id='quantitySelect0-key-0']")
    public WebElement quantityDropdown;

    @FindBy(xpath = "//*[@id='removeCartItemBtn0-key-0']")
    public WebElement removeCartItemBtn;

    @FindBy(xpath = "//*[@id='notifyTitle']")
    public WebElement removeCartNotifTitle;

    @FindBy(xpath = "//*[@id='emtyCart']//div/strong")
    public WebElement emptyMessageTitle;

    public CartPage() {
        this.driver = BaseTest.driver;
        this.wait = new WebDriverWait(driver, 10);
        PageFactory.initElements(driver, this);
        this.elementHelper = new ElementHelper(driver);
    }

    public void increaseQuantity(String quantity) throws IOException {
        try {
            Select select = new Select(quantityDropdown);
            List<WebElement> options = select.getOptions();
            int optionSize = options.size();
            if (optionSize >= 2) {
                for (int i = 0; i < optionSize; i++) {
                    if (options.get(i).getText().contains(quantity)) {
                        select.selectByValue(quantity);
                        break;
                    }
                }
                String actualQuantity = select.getFirstSelectedOption().getText();
                Assert.assertEquals(quantity + " adet", actualQuantity);
                elementHelper.testCasePassed();
            } else {
                log4j.info("Üründen yalnızca 1 adet kalmıştır");
                Assert.assertTrue(true);
                elementHelper.testCasePassed();
            }

        } catch (Exception e) {
            elementHelper.testCaseFailed();
            throw new RuntimeException(e);
        }
    }

    public void deleteFromBasket() throws IOException {
        try {
            elementHelper.click(removeCartItemBtn);
            elementHelper.checkElementVisible(removeCartNotifTitle);
            elementHelper.checkElementVisible(emptyMessageTitle);
            String actualTitle = elementHelper.getText(emptyMessageTitle);
            String expectedTitle = "SEPETINIZDE ÜRÜN BULUNMAMAKTADIR";
            Assert.assertEquals(expectedTitle, actualTitle);

        } catch (Exception e) {
            elementHelper.testCaseFailed();
            throw new RuntimeException(e);
        }
    }


}