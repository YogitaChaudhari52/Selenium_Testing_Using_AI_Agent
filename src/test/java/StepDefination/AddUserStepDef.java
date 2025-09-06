package StepDefination;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
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

public class AddUserStepDef {
    WebDriver driver;
    WebDriverWait wait;

    @Given("I am logged into the OrangeHRM dashboard")
    public void i_am_logged_into_the_orangehrm_dashboard() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        WebElement password = driver.findElement(By.name("password"));
        WebElement loginBtn = driver.findElement(By.cssSelector("button[type='submit']"));

        username.sendKeys("Admin");
        password.sendKeys("admin123");
        loginBtn.click();

        wait.until(ExpectedConditions.urlContains("/dashboard"));
    }

    @When("I navigate to the Add User page")
    public void i_navigate_to_the_add_user_page() {
        WebElement adminTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Admin']")));
        adminTab.click();

        WebElement addUserButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Add ']")));
        addUserButton.click();
    }

    @And("I fill in the user details")
    public void i_fill_in_the_user_details() {
        try {
         //   WebElement userRoleDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='User Role']/following-sibling::div//i")));
         //   userRoleDropdown.click();
        } catch (TimeoutException e) {
            System.out.println("Element not found. Capturing page source for debugging.");
            System.out.println(driver.getPageSource());
            throw e;
        }

        WebElement adminOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Admin']")));
        adminOption.click();

        WebElement employeeName = driver.findElement(By.xpath("//input[@placeholder='Type for hints...']"));
        WebElement username = driver.findElement(By.xpath("//label[text()='Username']/following-sibling::div/input"));
        WebElement password = driver.findElement(By.xpath("//label[text()='Password']/following-sibling::div/input"));
        WebElement confirmPassword = driver.findElement(By.xpath("//label[text()='Confirm Password']/following-sibling::div/input"));

        employeeName.sendKeys("John Smith");
        username.sendKeys("john.smith");
        password.sendKeys("Password123");
        confirmPassword.sendKeys("Password123");
    }

    @And("I leave mandatory fields empty")
    public void i_leave_mandatory_fields_empty() {
        // Intentionally leave fields empty
    }

    @And("I click the Save button")
    public void i_click_the_save_button() {
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Save ']")));
        saveButton.click();
    }

    @Then("I should see a confirmation message indicating the user was added successfully")
    public void i_should_see_a_confirmation_message_indicating_the_user_was_added_successfully() {
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'oxd-toast--success')]")));
        Assert.assertTrue(successMessage.isDisplayed());
    }

    @Then("I should see an error message indicating the mandatory fields must be filled")
    public void i_should_see_an_error_message_indicating_the_mandatory_fields_must_be_filled() {
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Required']")));
        Assert.assertTrue(errorMessage.isDisplayed());
    }
}
