package Beymen.Utilities;

import Beymen.Tests.BaseTest;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


import static Beymen.Tests.BaseTest.log4j;

public class ElementHelper {

    WebDriver driver;
    WebDriverWait wait;
    Actions action;
    ExtentTest logger;

    public ElementHelper(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
        this.action = new Actions(driver);
        this.logger = BaseTest.logger;
    }

    public void click(WebElement key) {
        key.click();
    }

    public void sendKey(WebElement key, String text) {
        key.sendKeys(text);
    }

    public void checkElementClickable(WebElement key) {

        wait.until(ExpectedConditions.elementToBeClickable(key));
    }

    public void checkElementVisible(WebElement key) {
        wait.until(ExpectedConditions.visibilityOf(key));
    }

    public String getText(WebElement key) {
        return key.getText();
    }

    public String getAttribute(WebElement key, String attr) {
        return key.getAttribute(attr);
    }


    public void scrollToElement(WebElement element) {

        String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                + "var elementTop = arguments[0].getBoundingClientRect().top;"
                + "window.scrollBy(0, elementTop-(viewPortHeight/2));";
        ((JavascriptExecutor) driver).executeScript(scrollElementIntoMiddle, element);
    }

    public void writeToTxt(WebElement description, WebElement color, WebElement price) {
        try {
            String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
            FileWriter fileWriter = new FileWriter("./Reports/productInfos-" + dateName + ".txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println("Ürün Açıklaması : " + getText(description));
            printWriter.println("Ürün Rengi : " + getText(color));
            printWriter.println("Ürün Fiyatı : " + getText(price));
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void testCaseFailed() throws IOException {
        log4j.info("Test Case Step FAILED");
    }

    public void testCasePassed() {
        log4j.info("Test Case Step PASSED");
    }


}