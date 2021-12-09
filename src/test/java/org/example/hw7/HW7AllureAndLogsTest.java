package org.example.hw7;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.example.hw6.LJMainPageFactory;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class HW7AllureAndLogsTest {
    private static ChromeOptions chromeOptions;
    private WebDriver driver;
    private WebDriverWait wait;
    private LJMainPageFactory mainPageFactory;

    private static final Logger LOGGER = LoggerFactory.getLogger(HW7AllureAndLogsTest.class);

    @BeforeAll
    public static void runBeforeAllTests() {
        LOGGER.info("runBeforeAllTests() - started");
        WebDriverManager.chromedriver().setup();

        chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--incognito");

        WebDriverManager.chromedriver().setup();
        LOGGER.info("runBeforeAllTests() - done");
    }

    @BeforeEach
    public void runBeforeEach() {
        LOGGER.info("runBeforeEach() - started");
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().setSize(new Dimension(1400, 900));

        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.get("https://www.livejournal.com/media");

        //Login
        mainPageFactory = new LJMainPageFactory(driver, wait);
        mainPageFactory.login("iayakimovich", "GB_pwd123!");
    }

    @AfterEach
    public void runAfterEach() throws InterruptedException {
        LOGGER.info("runAfterEach() - started");

        //Homework 7 - Browser logs
        // Вывод всех ошибок браузера после каждого теста
        LogEntries browserLogs = driver.manage().logs().get(LogType.BROWSER);
        List<LogEntry> allLogRows = browserLogs.getAll();

        if (allLogRows.size() > 0 ){
            LOGGER.error("Browser issues found! See logs: ");
        } else {
            LOGGER.error("No broswer issues found!");
        }

        //Log browser errors if any
        allLogRows.forEach(row -> LOGGER.error(row.getMessage()));

        if (driver != null) {
            driver.close();
        }
        LOGGER.info("runAfterEach() - done");
    }

    @Test
    @Epic("Logged User Experience")
    @Feature("Login form")
    @Description("Login")
    public void ljLogin() {
        LOGGER.info("ljLogin() - started");

        //Test if login link disappeared
        assertTrue( mainPageFactory.isLogged() );
        LOGGER.info("ljLogin() - done");
    }

    @Test
    @Epic("Logged User Experience")
    @Feature("Logoff menu")
    @Description("Logoff")
    public void ljLogOff() {
        LOGGER.info("ljLogOff() - started");

        mainPageFactory.logout();
        assertFalse( mainPageFactory.isLogged());

        LOGGER.info("ljLogOff() - done");
    }

    @Test
    @Epic("Shop functionality")
    @Feature("Entering Shop")
    @Description("Shop Link")
    public void shopLink() {
        LOGGER.info("shopLink - started");

        mainPageFactory.goShop();
        Assertions.assertEquals("Магазин", mainPageFactory.getH1ServiceTile());

        LOGGER.info("shopLink - finished");
    }

    @Test
    @Epic("Cinema functionality")
    @Feature("Entering Cinema functionality")
    @Description("Cinema Link")
    public void checkFirstCategory() {
        LOGGER.info("checkFirstCategory() - started");

        mainPageFactory.clickFirstCategory();;
        assertEquals("КИНО", mainPageFactory.getH1PageTile());

        LOGGER.info("checkFirstCategory() - finished");
    }
}

