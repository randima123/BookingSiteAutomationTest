package Common;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporter {

    public static ExtentReports getExtentReports(){
        String reportPath = System.getProperty("user.dir")+ "/reports/"+"index.html";

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setReportName("Bookings Web Automation Results");
        sparkReporter.config().setDocumentTitle("Test Results");
        ExtentReports extentReport = new ExtentReports();
        extentReport.attachReporter(sparkReporter);
        extentReport.setSystemInfo("Tester", "Randima Senanayake");
        return extentReport;
    }

}
