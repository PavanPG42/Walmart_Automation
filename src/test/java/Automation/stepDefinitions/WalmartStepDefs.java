package Automation.stepDefinitions;

import Automation.Pageobjects.*;
import Automation.TestComponents.BaseTest;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.IOException;

public class WalmartStepDefs {

    private static final Logger log = LogManager.getLogger(WalmartStepDefs.class);

    private WebDriver driver;
    private HomePage home;
    private SearchResultsPage searchResultsPage;
    private ProductPage productPage;
    private CartPage cartPage;

    private final BaseTest baseTest = new BaseTest();

    @Before
    public void setUp() throws IOException {
        driver = baseTest.initializeDriver();
        home = new HomePage(driver);
        home.goTo();
        log.info("Browser launched and on Walmart homepage");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            log.info("Browser closed");
        }
    }

    @Given("I am on the Walmart homepage")
    public void i_am_on_walmart_homepage() {
        log.info("On Walmart homepage");
    }

    @When("I search for {string} in the search bar")
    public void i_search_for(String product) throws InterruptedException {
        log.info("Searching for: {}", product);
        home.searchProduct(product);
    }

    @When("I click on the first suggestion")
    public void i_click_first_suggestion() {
        home.clickFirstSuggestion();
        searchResultsPage = new SearchResultsPage(driver);
    }

    @Then("the search results page should be displayed")
    public void results_page_should_be_displayed() {
        searchResultsPage.verifyResultsPage();
        log.info("Results page is displayed");
    }

    @Then("the results should be relevant to {string}")
    public void results_should_be_relevant(String keyword) {
        searchResultsPage.validateResults(keyword);
        log.info("Results validated for keyword: {}", keyword);
    }

    @When("I navigate to Toys and Outdoor Play via Departments")
    public void navigate_to_toys() {
        log.info("Navigating to Toys & Outdoor Play");
        home.goToToysCategory();
        searchResultsPage = new SearchResultsPage(driver);
    }

    @When("I select the first product from the results")
    public void select_first_product() {
        searchResultsPage.clickFirstProduct();
        productPage = new ProductPage(driver);
    }

    @Then("I should be on the product details page")
    public void verify_product_page() {
        productPage.verifyPage();
        log.info("Product details page verified");
    }

    @When("I click Add to Cart")
    public void click_add_to_cart() {
        log.info("Clicking Add to Cart");
        productPage.addItemToCart();
        productPage.openCart();
        cartPage = new CartPage(driver);
    }

    @Then("the cart page should display the correct product")
    public void verify_cart_page() {
        cartPage.verifyCartPage();
        cartPage.validateItems();
    }

    @Then("the cart subtotal should be visible")
    public void verify_subtotal() {
        Assert.assertTrue(cartPage.isSubtotalVisible(), "Subtotal is not visible on cart page");
        log.info("Subtotal verified");
    }

    @Then("the estimated total should be visible")
    public void verify_estimated_total() {
        Assert.assertTrue(cartPage.isEstimatedTotalVisible(), "Estimated total is not visible");
        log.info("Estimated total verified");
    }

    @Then("the cart count in the header should be updated")
    public void verify_cart_count() {
        String count = cartPage.getCartCount();
        Assert.assertNotNull(count, "Cart count is null");
        Assert.assertFalse(count.isEmpty(), "Cart count is empty");
        log.info("Cart count in header: {}", count);
    }
}