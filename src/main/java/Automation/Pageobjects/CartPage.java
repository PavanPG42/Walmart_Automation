package Automation.Pageobjects;
import org.testng.Assert;
import Automation.AbstractComponent.AbstractComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class CartPage extends AbstractComponent {

    private static final Logger log = LogManager.getLogger(CartPage.class);

    // Using FindBy after PageFactory is initialized in AbstractComponent
    @FindBy(css = "[data-automation-id='cart-container']")
    private WebElement cartContainer;

    @FindBy(css = "[data-automation-id='cart-item']")
    private List<WebElement> cartItems;

    @FindBy(xpath = "//span[contains(@class,'subtotal') or contains(text(),'Subtotal')]")
    private WebElement subtotalElement;

    @FindBy(xpath = "//span[contains(text(),'Estimated total')]")
    private WebElement estimatedTotalElement;

    @FindBy(css = "[data-testid='cart-item-count']")
    private WebElement cartCountElement;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void verifyCartPage() {
        waitForWebElementToAppear(cartContainer);
        log.info("Cart page loaded successfully");
    }

    public void validateItems() {
        Assert.assertFalse(cartItems.isEmpty(), "Cart is empty — no items found");
        log.info("Items in cart: {}", cartItems.size());
    }

    public boolean isSubtotalVisible() {
        try {
            waitForWebElementToAppear(subtotalElement);
            return subtotalElement.isDisplayed();
        } catch (Exception e) {
            log.warn("Subtotal element not found: {}", e.getMessage());
            return false;
        }
    }

    public boolean isEstimatedTotalVisible() {
        try {
            waitForWebElementToAppear(estimatedTotalElement);
            return estimatedTotalElement.isDisplayed();
        } catch (Exception e) {
            log.warn("Estimated total element not found: {}", e.getMessage());
            return false;
        }
    }

    public String getCartCount() {
        try {
            waitForWebElementToAppear(cartCountElement);
            return cartCountElement.getText().trim();
        } catch (Exception e) {
            log.warn("Cart count element not found: {}", e.getMessage());
            return "";
        }
    }
}