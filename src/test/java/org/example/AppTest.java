package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class AppTest {
    private static ChromeOptions chromeOptions;
    private WebDriver driver;
    private WebDriverWait wait;

    private static final Logger LOGGER = LoggerFactory.getLogger(AppTest.class);

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

        //Click login
        driver.findElement(By.cssSelector("a.s-header-item__link--login")).click();


        //Place credentials and click login button
        driver.findElement(By.id("user")).sendKeys("iayakimovich");
        driver.findElement(By.id("lj_loginwidget_password")).sendKeys("GB_pwd123!");
        driver.findElement(By.cssSelector("button.b-loginform-btn")).submit();

    }

    @AfterEach
    public void runAfterEach() throws InterruptedException {
        LOGGER.info("runAfterEach() - started");
        if (driver != null) {
            driver.close();
        }
        LOGGER.info("runAfterEach() - done");
    }

    @Test
    public void ljLogin() {
        LOGGER.info("ljLogin() - started");

        //Test if login link disappeared
        List<WebElement> elements = driver.findElements(By.cssSelector("a.s-header-item__link--login"));
        assertTrue( elements.size() == 0 );
        LOGGER.info("ljLogin() - done");
    }

    @Test
    public void ljLogOff() {
        LOGGER.info("ljLogOff() - started");

        Actions actions = new Actions(driver);
        WebElement userIcon = driver.findElement(By.cssSelector("svg.flaticon--userhead"));
        actions.moveToElement(userIcon).build().perform();

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("svg.flaticon--logout")));
        driver.findElement(By.cssSelector("svg.flaticon--logout")).click();

        String loginSelector = "a.s-header-item__link--login";

        //Check for Login link presence
        List<WebElement> elements;
        elements = driver.findElements(By.cssSelector(loginSelector));
        assertTrue(elements.size() > 0);

        LOGGER.info("ljLogOff() - done");
    }

    @Test
    public void shopLink() {
        LOGGER.info("shopLink - started");

        //Go to shop
        driver.findElement(By.cssSelector("a.s-nav-rootlink-shop")).click();

        //Check H1 Title text
        WebElement serviceTitle = driver.findElement(By.cssSelector("h1.b-service-title"));
        Assertions.assertEquals("Магазин", serviceTitle.getText());
        LOGGER.info("shopLink - finished");
    }

    @Test
    public void checkFirstCategory() {
        LOGGER.info("postBlogEntry() - started");

        WebElement categoryMenuList = driver.findElement(By.cssSelector("ul.categories__list"));
        categoryMenuList.findElement(By.cssSelector("li.categories__item")).click();

        WebElement h1Tile = driver.findElement(By.cssSelector("h1.mainpage__title"));

        assertEquals("КИНО", h1Tile.getText());

        LOGGER.info("postBlogEntry() - finished");

    }
}

