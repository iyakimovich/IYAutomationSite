package org.example.hw6;

import io.github.bonigarcia.wdm.WebDriverManager;
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


public class AppTestWithPageFactoryTest {
    private static ChromeOptions chromeOptions;
    private WebDriver driver;
    private WebDriverWait wait;
    private LJMainPageFactory mainPageFactory;

    private static final Logger LOGGER = LoggerFactory.getLogger(AppTestWithPageFactoryTest.class);

    @Deprecated
    @BeforeAll
    public static void runBeforeAllTests() {
        LOGGER.info("runBeforeAllTests() - started");
        WebDriverManager.chromedriver().setup();

        chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--incognito");

        WebDriverManager.chromedriver().setup();
        LOGGER.info("runBeforeAllTests() - done");
    }

    @Deprecated
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

    @Deprecated
    @AfterEach
    public void runAfterEach() throws InterruptedException {
        LOGGER.info("runAfterEach() - started");

        if (driver != null) {
            driver.close();
        }
        LOGGER.info("runAfterEach() - done");
    }

    @Deprecated
    @Test
    public void ljLogin() {
        LOGGER.info("ljLogin() - started");

        //Test if login link disappeared
        assertTrue( mainPageFactory.isLogged() );
        LOGGER.info("ljLogin() - done");
    }

    @Deprecated
    @Test
    public void ljLogOff() {
        LOGGER.info("ljLogOff() - started");

        mainPageFactory.logout();
        assertFalse( mainPageFactory.isLogged());

        LOGGER.info("ljLogOff() - done");
    }

    @Deprecated
    @Test
    public void shopLink() {
        LOGGER.info("shopLink - started");

        mainPageFactory.goShop();
        Assertions.assertEquals("??????????????", mainPageFactory.getH1ServiceTile());

        LOGGER.info("shopLink - finished");
    }

    @Deprecated
    @Test
    public void checkFirstCategory() {
        LOGGER.info("checkFirstCategory() - started");

        mainPageFactory.clickFirstCategory();;
        assertEquals("????????", mainPageFactory.getH1PageTile());

        LOGGER.info("checkFirstCategory() - finished");
    }
}

