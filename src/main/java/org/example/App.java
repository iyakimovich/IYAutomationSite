package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        System.out.println( "HW3 - eBay Login" );

        WebDriverManager.chromedriver().setup();

        ChromeOptions chromeOptions = new ChromeOptions();
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver(chromeOptions);

        driver.manage().window().setSize(new Dimension(1400, 900));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        try {
            driver.get("https://lenta.ru/");


            String selectorLogin = "#js-sidebar__menu-auth > a";
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(selectorLogin)));
            WebElement enterLink = driver.findElement(By.cssSelector(selectorLogin));
            enterLink.click();

            WebElement iFrameLogin = driver.findElement(By.xpath("//div[@data-id-frame='own']/iframe"));
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iFrameLogin));
            WebDriver driverLoginiFrame = driver.switchTo().frame(iFrameLogin);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login")));
            WebElement email = driverLoginiFrame.findElement(By.id("login"));
            email.sendKeys("inna.yakimovich@gmail.com");

        } finally {
            //driver.quit();
        }
    }
}
