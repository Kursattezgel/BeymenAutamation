package Beymen.Pages;

import Beymen.Tests.BaseTest;
import Beymen.Utilities.ElementHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class ProductListPage {

    WebDriver driver;
    WebDriverWait wait;
    ElementHelper elementHelper;


    @FindBy(xpath = "//*[contains(@class, 'o-productList__itemWrapper')]")
    public List<WebElement> productList;

    public ProductListPage() {
        this.driver = BaseTest.driver;
        this.wait = new WebDriverWait(driver, 10);
        PageFactory.initElements(driver, this);
        this.elementHelper = new ElementHelper(driver);
    }

    public void getRandomProduct() throws IOException {
        try {
            Random random = new Random();

            int productSize = productList.size();
            int count = random.nextInt(productSize);
            WebElement product = productList.get(count);
            elementHelper.scrollToElement(product);
            product.click();
            elementHelper.checkElementVisible(ProductDetailPage.productDescription);
            elementHelper.testCasePassed();
        } catch (Exception e) {
            elementHelper.testCaseFailed();
            throw new RuntimeException(e);
        }

    }

}