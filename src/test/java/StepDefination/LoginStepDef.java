package StepDefination;

import Report.ExtentReportManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import Logger.LogManager;
import org.slf4j.Logger;

public class LoginStepDef {
    private static final Logger logger = LogManager.getLogger(LoginStepDef.class);
    private static ExtentReports extent = ExtentReportManager.getInstance();
    private static ExtentTest test;
    WebDriver driver;
    WebDriverWait wait;

    @Before
    public void setUp() {
        test = extent.createTest("Login Test");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        test.info("WebDriver setup and browser launched.");
    }

    @Given("I open the OrangeHRM login page")
    public void i_open_the_orangehrm_login_page() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        test.info("Navigated to OrangeHRM login page.");
    }

    @When("I enter valid username and password")
    public void i_enter_valid_username_and_password() {
        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        WebElement password = driver.findElement(By.name("password"));
        username.sendKeys("Admin");
        password.sendKeys("admin123");
        test.info("Entered valid username and password.");
    }

    @When("I enter invalid username or password")
    public void i_enter_invalid_username_or_password() {
        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        WebElement password = driver.findElement(By.name("password"));
        username.sendKeys("invalidUser");
        password.sendKeys("invalidPass");
        test.info("Entered invalid username and password.");
    }

    @And("I click the login button")
    public void i_click_the_login_button() {
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        loginBtn.click();
        test.info("Clicked the login button.");
    }

    @Then("I should be redirected to the OrangeHRM dashboard")
    public void i_should_be_redirected_to_the_orangehrm_dashboard() {
        wait.until(ExpectedConditions.urlContains("/dashboard"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/dashboard"));
        test.pass("Successfully redirected to the OrangeHRM dashboard.");
    }

    @SneakyThrows
    @Then("I should see an error message indicating invalid credentials")
    public void i_should_see_an_error_message_indicating_invalid_credentials() {
        try {
            WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement errorMsg = longWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(@class, 'oxd-alert-content-text') and text()='Invalid credentials']")));
            logger.info("Error message found. Verifying visibility...");
            Assert.assertTrue(errorMsg.isDisplayed());
            logger.info("Error message is displayed as expected.");
            test.pass("Error message displayed as expected.");
        } catch (TimeoutException e) {
            logger.error("Error message not found. Capturing page source for debugging.");
            logger.error("Page Source: " + driver.getPageSource());
            throw e;
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            test.info("WebDriver instance closed.");
        }
        extent.flush();
    }
}
