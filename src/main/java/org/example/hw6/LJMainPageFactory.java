package org.example.hw6;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LJMainPageFactory {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = "a.s-header-item__link--login")
    private WebElement loginLink;

    @FindBy(id = "user")
    private WebElement inputUser;

    @FindBy(id = "lj_loginwidget_password")
    private WebElement inputPassword;

    @FindBy(css = "button.b-loginform-btn")
    private WebElement loginButton;

    @FindBy(css = "svg.flaticon--userhead")
    private WebElement userIcon;

    @FindBy(css = "svg.flaticon--logout")
    private WebElement logoutIcon;

    @FindBy(css = "a.s-nav-rootlink-shop")
    private WebElement linkShop;

    @FindBy(css = "h1.b-service-title")
    private WebElement serviceH1Title;

    @FindBy(css = "ul.categories__list")
    private WebElement categoryMenuList;

    @FindBy(css = "h1.mainpage__title")
    private WebElement pageH1Title;

    public LJMainPageFactory(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public void login(String userName, String userPassword) {
        loginLink.click();
        inputUser.sendKeys(userName);
        inputPassword.sendKeys(userPassword);
        loginButton.submit();
    }

    public void logout() {
        Actions actions = new Actions(driver);
        actions.moveToElement(userIcon).build().perform();

        wait.until(ExpectedConditions.elementToBeClickable(logoutIcon));
        logoutIcon.click();
    }

    public boolean isLogged() {
        boolean result;
        try {
            result =!loginLink.isDisplayed();
        } catch (NoSuchElementException e) {
            result = true;
        }
        return result;
    }

    public void goShop() {
        //Go to shop
        linkShop.click();
    }

    public String getH1ServiceTile() {
        return serviceH1Title.getText();
    }

    public String getH1PageTile() {
        return pageH1Title.getText();
    }
    public void clickFirstCategory() {
        categoryMenuList.findElement(By.cssSelector("li.categories__item")).click();
    }
}
