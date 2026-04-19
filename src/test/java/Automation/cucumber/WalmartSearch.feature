@WalmartTests
Feature: Walmart Product Search
  As a user I want to search for products on Walmart

  @Scenario1
  Scenario: Search for iPhone using search bar
    Given I am on the Walmart homepage
    When I search for "iphone" in the search bar
    And I click on the first suggestion
    Then the search results page should be displayed
    And the results should be relevant to "iphone"