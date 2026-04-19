package Automation.Pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import Automation.AbstractComponent.AbstractComponent;
import java.util.List;

public class HomePage extends AbstractComponent {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    private By searchBox = By.cssSelector("input[name='q'][type='search'], input[name='q']");
    private By suggestions = By.cssSelector("[data-type='typeahead-suggestion']");
    private By departments = By.xpath("//button[@data-dca-name='Departments']");
    private By toys = By.xpath("//span[text()='Toys & Outdoor Play']");
    private By allToys = By.xpath("//a[contains(text(),'All Toys & Outdoor Play')]");
    // overlay that blocks clicks on page load
    private By overlay = By.cssSelector(".OverlayScrim_scrim__x5LLJ, [class*='OverlayScrim']");

    public void goTo() {
        driver.get("https://www.walmart.com");
    }

    public void dismissOverlayIfPresent() {
        try {
            List<WebElement> overlays = driver.findElements(overlay);
            if (!overlays.isEmpty() && overlays.get(0).isDisplayed()) {
                // click outside the overlay to dismiss it
                ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();", overlays.get(0)
                );
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            // no overlay present, continue
        }
    }

    public void searchProduct(String product) throws InterruptedException {
        // Wait for page to settle and dismiss any overlay
        Thread.sleep(2000);
        dismissOverlayIfPresent();

        WebElement box = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
        box.clear();
        for (char c : product.toCharArray()) {
            box.sendKeys(String.valueOf(c));
            Thread.sleep(100);
        }
    }

    public void clickFirstSuggestion() {
        List<WebElement> list = wait.until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(suggestions)
        );
        if (list.isEmpty()) {
            throw new RuntimeException("No suggestions found");
        }
        list.get(0).click();
    }

    public void goToToysCategory() {
        // Wait for page to fully settle first
        try { Thread.sleep(3000); } catch (Exception e) {}

        dismissOverlayIfPresent();

        // Use JavaScript click to bypass any remaining overlay
        WebElement depBtn = wait.until(
            ExpectedConditions.presenceOfElementLocated(departments)
        );
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", depBtn);

        // Hover over Toys
        WebElement toysMenu = wait.until(
            ExpectedConditions.visibilityOfElementLocated(toys)
        );
        new Actions(driver).moveToElement(toysMenu).perform();

        // Click All Toys
        wait.until(ExpectedConditions.elementToBeClickable(allToys)).click();
    }
}