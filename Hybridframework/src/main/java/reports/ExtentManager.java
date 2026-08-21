package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> testThreadLocal = new ThreadLocal<>();

    public static ExtentReports getInstance() {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter("target/ExtentReport.html");
            spark.config().setReportName("Automation Test Results");
            spark.config().setDocumentTitle("Test Execution Report");

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Framework", "Hybrid, Selenium Webdriver + TestNG");
            extent.setSystemInfo("Author", "Vinnarsi");
            extent.setSystemInfo("Browser", config.ConfigReader.getProperty("browser"));
            extent.setSystemInfo("URL", config.ConfigReader.getProperty("url"));
        }
        return extent;
    }

    public static ExtentTest createTest(String name) {
        ExtentTest test = getInstance().createTest(name);
        testThreadLocal.set(test);
        return test;
    }

    public static ExtentTest getTest() {
        return testThreadLocal.get();
    }
}
