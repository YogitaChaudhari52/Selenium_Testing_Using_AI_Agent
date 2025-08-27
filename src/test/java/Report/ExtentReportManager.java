package Report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            createInstance("extentReport.html");
        }
        return extent;
    }

    public static ExtentReports createInstance(String fileName) {
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(fileName);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle("Automation Report");
        htmlReporter.config().setReportName("Test Execution Report");

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Tester", "QA Team");

        return extent;
    }
}
