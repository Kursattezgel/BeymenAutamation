package Beymen.Tests;

import Beymen.Utilities.ConfigurationReader;
import Beymen.Utilities.DriverManager;
import com.aventstack.extentreports.ExtentTest;
import org.apache.log4j.Logger;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import java.util.Properties;

public class BaseTest {

    public static WebDriver driver;
    Properties properties;
    public static ExtentTest logger;
    public final static Logger log4j = Logger.getLogger(BeymenTest.class);

    @Before
    public void before() {
        log4j.info("Test started.");
        properties = ConfigurationReader.initializeProperties();
        driver = DriverManager.initializeDriver();
    }

    @After
    public void after() {
        DriverManager.terminateDriver();
        log4j.info("Test DONE.");
    }

}