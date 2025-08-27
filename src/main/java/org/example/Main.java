package org.example;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter ;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // Set up ExtentReports
        ExtentSparkReporter  htmlReporter = new ExtentSparkReporter ("extentReport.html");
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        ExtentTest test = extent.createTest("Selenium Chrome Test", "Test to open Chrome and navigate to Google");

        // Set up ChromeDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        test.info("Chrome browser opened");
        // Open a website (e.g., Google)
        driver.get("https://www.google.com");
        test.info("Navigated to https://www.google.com");
        // Wait for a few seconds to see the browser
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            test.warning("Thread was interrupted: " + e.getMessage());
            e.printStackTrace();
        }
        // Close the browser
        driver.quit();
        test.info("Browser closed");
        // Mark test as passed
        test.pass("Test completed successfully");
        // Flush the report
        extent.flush();
    }
}