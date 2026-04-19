package Automation.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SearchProd {

    public static void main(String[] args) throws InterruptedException {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {

            // open walmart
            driver.get("https://www.walmart.com");
            System.out.println("opened walmart homepage");

            // find the search box and type iphone slowly so suggestions pop up
            WebElement searchBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name("q"))
            );
            searchBox.clear();

            for (char c : "iphone".toCharArray()) {
                searchBox.sendKeys(String.valueOf(c));
                Thread.sleep(150);
            }
            System.out.println("typed iphone in the search bar");

            // wait for the dropdown suggestions to show up then grab them all
            List<WebElement> suggestions = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.cssSelector("[data-type='typeahead-suggestion']")
                )
            );

            System.out.println("suggestions loaded, total: " + suggestions.size());
            for (int i = 0; i < suggestions.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + suggestions.get(i).getText());
            }

            // click the first one
            String picked = suggestions.get(0).getText();
            suggestions.get(0).click();
            System.out.println("clicked first suggestion: " + picked);

            // make sure the results page actually loaded
            wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("[data-testid='search-result-listview-container']")
                )
            );

            String url = driver.getCurrentUrl();
            if (!url.contains("iphone")) {
                throw new AssertionError("URL doesn't look right: " + url);
            }
            System.out.println("results page loaded, url looks good");

            // check that the products showing up are actually iphone related
            List<WebElement> titles = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.cssSelector("span[data-automation-id='product-title']")
                )
            );

            System.out.println("total products on page: " + titles.size());

            int matched = 0;
            for (WebElement t : titles) {
                String text = t.getText().toLowerCase();
                if (text.contains("iphone") || text.contains("apple")) {
                    matched++;
                }
            }

            // print first 5 just to see what came back
            System.out.println("first 5 results:");
            for (int i = 0; i < Math.min(5, titles.size()); i++) {
                System.out.println("  - " + titles.get(i).getText());
            }

            System.out.println("iphone/apple related: " + matched + " out of " + titles.size());

            if (matched == 0) {
                throw new AssertionError("none of the results seem related to iphone, something is off");
            }

            System.out.println("test passed");

        } catch (AssertionError ae) {
            System.out.println("assertion failed: " + ae.getMessage());

        } catch (Exception e) {
            System.out.println("test failed: " + e.getMessage());
            e.printStackTrace();

        } finally {
            driver.quit();
            System.out.println("browser closed");
        }
    }
}