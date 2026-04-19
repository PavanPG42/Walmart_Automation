@WalmartTests
Feature: Walmart Add to Cart
  As a user I want to add a product to my cart

  @Scenario2
  Scenario: Add a toy product to cart via Departments
    Given I am on the Walmart homepage
    When I navigate to Toys and Outdoor Play via Departments
    And I select the first product from the results
    Then I should be on the product details page
    When I click Add to Cart
    Then the cart page should display the correct product
    And the cart subtotal should be visible
    And the estimated total should be visible
    And the cart count in the header should be updated