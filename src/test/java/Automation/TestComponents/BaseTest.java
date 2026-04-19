package Automation.TestComponents;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import Automation.Pageobjects.HomePage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

    public WebDriver driver;
    public HomePage home;
    private static final Logger log = LogManager.getLogger(BaseTest.class);

    public WebDriver initializeDriver() throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(
            System.getProperty("user.dir")
            + "/src/main/java/Automation/resources/GlobalData.properties"
        );
        prop.load(fis);

        // Allow browser to be overridden via -Dbrowser=edge from command line
        String browserName = System.getProperty("browser") != null
                ? System.getProperty("browser")
                : prop.getProperty("browser");

        log.info("Launching browser: {}", browserName);

        if (browserName.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-notifications");
            driver = new ChromeDriver(options);

        } else if (browserName.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--start-maximized");
            driver = new EdgeDriver(options);

        } else {
            throw new IllegalArgumentException("Browser not supported: " + browserName);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver;
    }

    public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
        String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {});
    }

    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/reports/" + testCaseName + ".png";
        FileUtils.copyFile(source, new File(path));
        log.info("Screenshot saved: {}", path);
        return path;
    }

    @BeforeMethod(alwaysRun = true)
    public HomePage launchApplication() throws IOException {
        driver = initializeDriver();
        home = new HomePage(driver);
        home.goTo();
        log.info("Navigated to Walmart homepage");
        return home;
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            log.info("Closing browser");
            driver.quit();
        }
    }
}