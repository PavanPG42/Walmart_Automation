package Automation.Pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import Automation.AbstractComponent.AbstractComponent;

public class ProductPage extends AbstractComponent {

    public ProductPage(org.openqa.selenium.WebDriver driver) {
        super(driver);
    }

    private By title = By.cssSelector("h1");
    private By addToCart = By.cssSelector("button[data-automation-id='add-to-cart-btn']");
    private By viewCart = By.xpath("//a[contains(@link-identifier,'viewCart')] | //button[contains(@data-automation-id,'cart')]");

    public void verifyPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(title));
    }

    public void addItemToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCart)).click();
    }

    public void openCart() {
        wait.until(ExpectedConditions.elementToBeClickable(viewCart)).click();
    }
}