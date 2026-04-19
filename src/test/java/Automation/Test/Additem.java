package Automation.Test;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Additem {

    public static void main(String[] args) {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        Actions actions = new Actions(driver);

        try {
            // Step 1: Open Walmart
            driver.get("https://www.walmart.com");
            System.out.println("Opened Walmart");

            // Step 2: Click Departments
            WebElement departments = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//button[contains(text(),'Departments')]")
                    )
            );
            departments.click();
            System.out.println("Clicked Departments");

            // Step 3: Hover on Toys & Outdoor Play
            WebElement toysMenu = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//span[text()='Toys & Outdoor Play']")
                    )
            );
            actions.moveToElement(toysMenu).perform();
            System.out.println("Hovered on Toys & Outdoor Play");

            // Step 4: Click All Toys & Outdoor Play
            WebElement allToys = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//a[contains(text(),'All Toys & Outdoor Play')]")
                    )
            );
            allToys.click();
            System.out.println("Opened Toys category");

            // Step 5: Select first product
            List<WebElement> products = wait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(
                            By.cssSelector("[data-testid='list-view'] a")
                    )
            );

            products.get(0).click();
            System.out.println("Opened first product");

            // Step 6: Validate product page
            WebElement productTitle = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector("h1")
                    )
            );
            System.out.println("On product page: " + productTitle.getText());

            // Step 7: Click Add to Cart
            WebElement addToCart = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.cssSelector("button[data-automation-id='add-to-cart-btn']")
                    )
            );
            addToCart.click();
            System.out.println("Clicked Add to Cart");

            // Step 8: Go to Cart
            WebElement viewCart = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//a[contains(@link-identifier,'viewCart')] | //button[contains(@data-automation-id,'cart')]")
                    )
            );
            viewCart.click();

            // Step 9: Validate cart page
            wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector("[data-automation-id='cart-container']")
                    )
            );

            // Validate cart items
            List<WebElement> cartItems = driver.findElements(
                    By.cssSelector("[data-automation-id='cart-item']")
            );

            if (cartItems.isEmpty()) {
                throw new AssertionError("Cart is empty");
            }

            System.out.println("Items in cart: " + cartItems.size());

            // Step 10: Validate subtotal & total (basic check)
            WebElement subtotal = driver.findElement(
                    By.xpath("//span[contains(text(),'Subtotal')]")
            );

            WebElement total = driver.findElement(
                    By.xpath("//span[contains(text(),'Estimated total')]")
            );

            System.out.println("Subtotal displayed: " + subtotal.isDisplayed());
            System.out.println("Estimated total displayed: " + total.isDisplayed());

            // Step 11: Validate cart icon count
            WebElement cartCount = driver.findElement(
                    By.cssSelector("[data-testid='cart-item-count']")
            );

            System.out.println("Cart count: " + cartCount.getText());

            System.out.println("Test PASSED");

        } catch (Exception e) {
            System.out.println("Test FAILED: " + e.getMessage());
            e.printStackTrace();

        } finally {
            driver.quit();
            System.out.println("Browser closed");
        }
    }
}