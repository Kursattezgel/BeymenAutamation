package Beymen.Pages;

import Beymen.Tests.BaseTest;
import Beymen.Utilities.ElementHelper;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.IOException;
import java.util.List;


import static Beymen.Pages.CartPage.orderSummaryTitle;
import static Beymen.Pages.CartPage.productSalePrice;

public class ProductDetailPage {

    WebDriver driver;
    WebDriverWait wait;
    ElementHelper elementHelper;

    private String detailPrice;

    @FindBy(xpath = "//*[@class='o-productDetail__description']")
    public static WebElement productDescription;

    @FindBy(xpath = "//*[@class='m-colorsSlider__top']//label")
    public WebElement productColor;

    @FindBy(xpath = "//*[@id='priceNew']")
    public WebElement productPrice;

    @FindBy(xpath = "//*[contains(@class, 'm-variation__item')]")
    public List<WebElement> productSizes;

    @FindBy(xpath = "//*[@id='addBasket']")
    public WebElement addBasketBtn;

    @FindBy(xpath = "//*[@class='m-notification__title']")
    public WebElement addedToCartNotifTitle;

    @FindBy(xpath = "//*[@class='m-notification__button btn']")
    public WebElement addedToCartNotifBtn;


    public ProductDetailPage() {
        this.driver = BaseTest.driver;
        this.wait = new WebDriverWait(driver, 10);
        PageFactory.initElements(driver, this);
        this.elementHelper = new ElementHelper(driver);
    }

    public void writeProductInformation() throws IOException {
        try {
            elementHelper.writeToTxt(productDescription, productColor, productPrice);
            elementHelper.testCasePassed();
        } catch (Exception e) {
            elementHelper.testCaseFailed();
            throw new RuntimeException(e);
        }
    }

    public void addToBasket() throws IOException {
        try {
            detailPrice = elementHelper.getText(productPrice);
            int bodySizeCount = productSizes.size();
            for (int i = 0; i <= bodySizeCount; i++) {
                WebElement selectedBody = productSizes.get(i);
                if (!selectedBody.getAttribute("class").contains("-disabled")) {
                    selectedBody.click();
                    break;
                }
            }
            elementHelper.click(addBasketBtn);
            elementHelper.checkElementVisible(addedToCartNotifTitle);
            String actualText = elementHelper.getText(addedToCartNotifTitle);
            String expectedText = "Sepete Eklendi";
            Assert.assertEquals(expectedText, actualText);
            elementHelper.testCasePassed();

        } catch (Exception e) {
            elementHelper.testCaseFailed();
            throw new RuntimeException(e);
        }
    }

    public void checkPriceInBasket() throws IOException {
        try {
            elementHelper.click(addedToCartNotifBtn);
            elementHelper.checkElementVisible(orderSummaryTitle);
            String salePrice = elementHelper.getText(productSalePrice);
            Assert.assertEquals(detailPrice, salePrice);
            elementHelper.testCasePassed();

        } catch (Exception e) {
            elementHelper.testCaseFailed();
            throw new RuntimeException(e);
        }
    }

}