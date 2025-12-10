package tests;

import io.qameta.allure.Allure;
import org.example.config.ConfigReader;
import org.example.driver.DriverFactory;
import org.example.utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.ByteArrayInputStream;

public class BaseTest {
    protected WebDriver driver;
    protected static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser"})
    public void setUp(@Optional("") String browser) {
        String browserToUse = getBrowserParam(browser);
        logger.info("Setting up test with browser: {}", browserToUse);
        driver = DriverFactory.initializeDriver(browserToUse);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            handleTestFailure(result);
        }
        DriverFactory.quitDriver();
    }

    protected String getBaseUrl() {
        return ConfigReader.getConfig().getApplication().getBaseUrl();
    }

    private String getBrowserParam(String browser) {
        return (browser != null && !browser.trim().isEmpty())
                ? browser.trim()
                : ConfigReader.getBrowser();
    }

    private void handleTestFailure(ITestResult result) {
        logger.error("Test failed: {}", result.getName());

        if (driver != null) {
            attachScreenshot(result.getName());
            attachErrorDetails(result.getThrowable());
        }
    }

    private void attachScreenshot(String testName) {
        byte[] screenshot = ScreenshotUtil.takeScreenshotAsBytes(driver);
        Allure.addAttachment("Failure Screenshot", "image/png",
                new ByteArrayInputStream(screenshot), "png");
        ScreenshotUtil.takeScreenshot(driver, testName);
    }

    private void attachErrorDetails(Throwable throwable) {
        if (throwable != null) {
            Allure.addAttachment("Error Details", throwable.getMessage());
        }
    }
}
