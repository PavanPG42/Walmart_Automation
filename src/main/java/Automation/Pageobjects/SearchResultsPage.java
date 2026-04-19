package Automation.Pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import Automation.AbstractComponent.AbstractComponent;

import java.util.List;

public class SearchResultsPage extends AbstractComponent {

    public SearchResultsPage(org.openqa.selenium.WebDriver driver) {
        super(driver);
    }

    private By results = By.cssSelector("[data-testid='search-result-listview-container']");
    private By titles = By.cssSelector("span[data-automation-id='product-title']");
    private By products = By.cssSelector("[data-testid='list-view'] a");

    public void verifyResultsPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(results));
    }

    public void validateResults(String keyword) {
        List<WebElement> list = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(titles)
        );

        int match = 0;

        for (WebElement e : list) {
            String text = e.getText().toLowerCase();
            if (text.contains(keyword.toLowerCase()) || text.contains("apple")) {
                match++;
            }
        }

        if (match == 0) {
            throw new AssertionError("No relevant results found");
        }

        System.out.println("Matched results: " + match + "/" + list.size());
    }

    public void clickFirstProduct() {
        List<WebElement> list = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(products)
        );

        list.get(0).click();
    }
}