# Walmart Test Automation Framework

A Hybrid Test Automation Framework built for automating Walmart.com 
web application using Selenium, Cucumber (BDD), TestNG, and Maven.

---

## Tech Stack

| Tool | Purpose |
|------|---------|
| Java 11 | Programming Language |
| Selenium 4.18.1 | Browser Automation |
| Cucumber 7.15.0 | BDD Framework |
| TestNG 7.9.0 | Test Execution Engine |
| Maven | Build & Dependency Management |
| WebDriverManager 5.8.0 | Auto Browser Driver Management |
| ExtentReports 5.1.1 | HTML Test Execution Reports |
| Log4j2 2.23.1 | Logging Framework |
| Jackson Databind | JSON Data Reading |
| Apache Commons IO | File Handling & Screenshots |

---

## Framework Architecture
```
Automation_Framework/
│── pom.xml → Maven config & dependencies
│── testSuites/
│   └── testng.xml → Suite runner with listeners

├── src/main/java/
│   └── Automation/
│       ├── AbstractComponent/
│       │   └── AbstractComponent.java → Base class for all pages
│       ├── Pageobjects/
│       │   ├── HomePage.java → Walmart homepage interactions
│       │   ├── SearchResultsPage.java → Search results interactions
│       │   ├── ProductPage.java → Product page interactions
│       │   └── CartPage.java → Cart interactions
│       └── resources/
│           └── GlobalData.properties → Configuration file

├── src/main/resources/
│   └── log4j2.xml → Logging configuration

├── src/test/java/
│   └── Automation/
│       ├── cucumber/
│       │   ├── WalmartSearch.feature → Scenario 1 in Gherkin
│       │   ├── WalmartCart.feature → Scenario 2 in Gherkin
│       │   ├── TestNGTestRunner.java → Cucumber + TestNG bridge
│       │   ├── data/
│       │   │   ├── DataReader.java → JSON reader utility
│       │   │   └── WalmartTestData.json → External test data
│       │   ├── stepDefinitions/
│       │   │   └── WalmartStepDefs.java → Step definitions
│       │   └── Test/
│       │       ├── SearchProd.java → Standalone test (Scenario 1)
│       │       ├── AddItem.java → Standalone test (Scenario 2)
│       │       ├── Scenario_1.java → Data-driven test
│       │       └── Scenario_2.java → POM-based test

├── TestComponents/
│   ├── BaseTest.java → Driver setup & utilities
│   └── Listeners.java → ExtentReports & screenshots

├── reports/ → ExtentReports HTML generated here
└── logs/ → Log4j2 execution logs
```

---

## Design Patterns Used

### 1. Page Object Model (POM)
Each page of Walmart has its own dedicated class containing 
locators and actions for that page only. This ensures:
- Single place to update if UI changes
- Clean separation of test logic and page logic
- Reusability across multiple tests

### 2. BDD (Behavior Driven Development)
Test scenarios written in plain English using Gherkin syntax.
Non-technical stakeholders can read and validate test scenarios
without understanding Java code.

### 3. Hybrid Framework
Combination of POM + BDD + Data Driven Testing for maximum
maintainability and scalability.

---

## OOP Concepts Implemented

| Concept | Where Used |
|---------|-----------|
| Encapsulation | Page classes — locators declared as private |
| Inheritance | All page classes extend AbstractComponent |
| Abstraction | BaseTest hides browser setup complexity |
| Polymorphism | WebDriver variable holds ChromeDriver or EdgeDriver |

---

## Test Scenarios Automated

### Scenario 1 — Product Search
1. Navigate to Walmart homepage
2. Search for iPhone in search bar
3. Click on first suggestion
4. Verify results page is displayed
5. Validate results are relevant to iPhone

### Scenario 2 — Add Product to Cart
1. Navigate to Walmart homepage
2. Click Departments dropdown
3. Hover on Toys & Outdoor Play
4. Click All Toys & Outdoor Play
5. Select first product
6. Verify product details page
7. Click Add to Cart
8. Verify correct product added to cart
9. Validate subtotal is visible
10. Validate estimated total is visible
11. Validate cart count in header is updated

---

## Cross Browser Support

Browser is controlled via `GlobalData.properties`:

```properties
browser=chrome   # change to 'edge' for Microsoft Edge
```

Or override via Maven command line:
```bash
mvn clean test -Dbrowser=edge
```

Supported browsers:
- Google Chrome
- Microsoft Edge

---

## How to Run

### Prerequisites
- Java 11 or higher installed
- Maven installed
- Chrome or Edge browser installed
- Internet connection

### Run via Maven
```bash
# Clone the repository
git clone https://github.com/PavanPG42/Walmart_Automation.git

# Navigate to project
cd Walmart_Automation

# Run all tests
mvn clean test

# Run with Edge browser
mvn clean test -Dbrowser=edge
```

### Run via Eclipse
1. Right click on `testSuites/testng.xml`
2. Select Run As → TestNG Suite

---

## Reports & Logs

### Execution Report
After running tests, open:
reports/index.html
Open in any browser to view the ExtentReports HTML report with:
- Test pass/fail status
- Execution timestamps
- Error details
- Screenshots on failure

### Logs
Execution logs available at:
logs/automation.log
---

## Data Driven Testing

Test data is externalized in JSON format:
src/test/java/Automation/data/WalmartTestData.json

```json
[
  {
    "searchKeyword": "iphone",
    "expectedResultKeyword": "iphone"
  },
  {
    "searchKeyword": "samsung",
    "expectedResultKeyword": "samsung"
  }
]
```

Add more rows to run the same test with different data automatically.

---

## Note on Test Execution

Walmart.com uses advanced bot detection (Kasada/PerimeterX) that 
identifies and blocks automated browser sessions with CAPTCHA 
challenges. The framework correctly implements all automation logic — 
failures occur only due to this third-party anti-bot protection, 
not framework defects. This is a known challenge with production 
e-commerce sites and is documented here for transparency.

---

## Project Evolution

This framework was built in phases:

**Phase 1 — Standalone Tests**
`SearchProd.java` and `Additem.java` — raw Selenium scripts to 
validate application flow and locators.

**Phase 2 — Page Object Model**
Refactored into page classes — `HomePage`, `SearchResultsPage`, 
`ProductPage`, `CartPage` — all extending `AbstractComponent`.

**Phase 3 — BDD Layer**
Added Cucumber feature files, step definitions, and TestNG runner.

**Phase 4 — Reporting & Logging**
Added ExtentReports via Listeners, Log4j2 logging, screenshot on failure.

**Phase 5 — Data Driven Testing**
Added `DataReader.java` and `WalmartTestData.json` for external 
test data management.

---

## Repository

GitHub: https://github.com/PavanPG42/Walmart_Automation