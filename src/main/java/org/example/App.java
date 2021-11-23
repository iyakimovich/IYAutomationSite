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
        chromeOptions.addArguments("--incognito");
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver(chromeOptions);

        driver.manage().window().setSize(new Dimension(1400, 900));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        try {
            driver.get("https://www.ebay.com/");

            //Click on sign-in
            driver.findElement(By.id("gh-ug")).click();

            //Log in
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userid")));
            driver.findElement(By.id("userid")).sendKeys("inna.yakimovich@gmail.com");

            //Continue button
            driver.findElement(By.id("signin-continue-btn")).click();

            //Password
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("pass")));
            driver.findElement(By.id("pass")).sendKeys("EB_pwd123!");

            //Sign in button
            driver.findElement(By.id("sgnBt")).click();

        } finally {
            //driver.quit();
        }
    }
}
