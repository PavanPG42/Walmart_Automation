package Automation.Test;

import org.testng.Assert;
import org.testng.annotations.Test;
import Automation.Pageobjects.*;
import Automation.TestComponents.BaseTest;

public class Scenario_2 extends BaseTest {

    @Test
    public void addToCartTest() {

        SearchResultsPage results = new SearchResultsPage(driver);
        ProductPage product = new ProductPage(driver);
        CartPage cart = new CartPage(driver);

        home.goToToysCategory();

        results.clickFirstProduct();

        product.verifyPage();
        product.addItemToCart();
        product.openCart();

        cart.verifyCartPage();
        cart.validateItems();

        Assert.assertTrue(cart.isSubtotalVisible(), "Subtotal not visible");
        Assert.assertTrue(cart.isEstimatedTotalVisible(), "Estimated total not visible");

        String count = cart.getCartCount();
        Assert.assertFalse(count.isEmpty(), "Cart count is empty");

        System.out.println("Scenario 2 Passed");
    }
}