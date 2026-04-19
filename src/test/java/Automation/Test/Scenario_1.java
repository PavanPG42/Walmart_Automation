package Automation.Test;

import Automation.Pageobjects.SearchResultsPage;
import Automation.TestComponents.BaseTest;
import Automation.Data.DataReader;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Scenario_1 extends BaseTest {

    // DataProvider reads from JSON and feeds data to the test
    @DataProvider
    public Object[][] getSearchData() throws IOException {
        DataReader reader = new DataReader();
        List<HashMap<String, String>> data = reader.getTestData();

        Object[][] result = new Object[data.size()][1];
        for (int i = 0; i < data.size(); i++) {
            result[i][0] = data.get(i);
        }
        return result;
    }

    // Test runs once for each row in the JSON file
    @Test(dataProvider = "getSearchData")
    public void searchProductTest(HashMap<String, String> testData) throws Exception {

        String keyword = testData.get("searchKeyword");
        String expected = testData.get("expectedResultKeyword");

        SearchResultsPage results = new SearchResultsPage(driver);

        home.searchProduct(keyword);
        home.clickFirstSuggestion();

        results.verifyResultsPage();
        results.validateResults(expected);

        System.out.println("Data driven test passed for: " + keyword);
    }
}