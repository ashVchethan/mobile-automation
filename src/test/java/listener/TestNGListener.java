package listener;

import com.aventstack.extentreports.Status;

import constants.PathConstants;

import driver.DriverManager;

import global.AppiumServer;

import helper.DeviceManager;

import org.testng.IExecutionListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

import report.ExtentManager;

import utilities.CommonUtils;
import utilities.SlackReportUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestNGListener implements IExecutionListener, ITestListener, ISuiteListener {
    private final String platformName = PathConstants.PLATFORM;

    private final int threadCount = Integer.parseInt(System.getProperty("threadCount", "1"));

    private int totalTestCases, totalTestCasesPassed, totalTestCasesFailed, totalTestCasesSkipped;

    private static final List<String> failedCasesExceptions = new ArrayList<>();

    private static String slackExceptionString = "";

    ProcessBuilder builder = new ProcessBuilder();

    @Override
    public void onExecutionStart() {
        try {
            AppiumServer.startServer();
            DeviceManager.launchEmulatorOrSimulator(platformName, threadCount);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onStart(ISuite iSuite) {
        totalTestCases = iSuite.getAllMethods().size();
        totalTestCasesPassed = 0;
        totalTestCasesFailed = 0;
        totalTestCasesSkipped = 0;
        System.out.println("\nTotal test cases : " + totalTestCases);
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        System.out.println("Test under execution: " + iTestResult.getMethod().getMethodName());
        ExtentManager.startTest(iTestResult.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        totalTestCasesPassed++;
        ExtentManager.getTest().log(Status.PASS, "Test passed");
        // TODO: Update the success test count and test duration
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        totalTestCasesFailed++;
        ExtentManager.getTest().log(Status.FAIL, iTestResult.getThrowable());
        try {
            String screenshotPath =
                    CommonUtils.takeScreenshotAndSave(
                            DriverManager.getDriver(),
                            System.getProperty("user.dir") + "/screenshots",
                            iTestResult.getMethod().getMethodName());
            Path reportsDir = Paths.get(System.getProperty("user.dir"), "reports");
            String relativeScreenshotPath =
                    reportsDir.relativize(Paths.get(screenshotPath)).toString();
            ExtentManager.getTest().addScreenCaptureFromPath(relativeScreenshotPath);
        } catch (Exception e) {
            System.out.println("Failed to capture screenshot: " + e.getMessage());
        }
        failedCasesExceptions.add(
                new String[] {
                            iTestResult.getMethod().getMethodName(),
                            iTestResult.getThrowable().getMessage(),
                            Arrays.toString(iTestResult.getThrowable().getStackTrace())
                        }
                        .toString());
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        totalTestCasesSkipped++;
        ExtentManager.getTest().log(Status.SKIP, "Test skipped");
    }

    @Override
    public void onFinish(ISuite iSuite) {
        System.out.println(
                "\nTotal number of test cases executed : "
                        + (totalTestCasesPassed + totalTestCasesFailed + totalTestCasesSkipped));
        System.out.println("Total test cases passed : " + totalTestCasesPassed);
        System.out.println("Total test cases failed : " + totalTestCasesFailed);
        System.out.println("Total test cases skipped : " + totalTestCasesSkipped);
        System.out.println(
                "------Test Suite : " + iSuite.getName() + " has finished executing------\n");
        ExtentManager.flush();
        SlackReportUtil.sendReportToSlack(iSuite, failedCasesExceptions);
    }

    @Override
    public void onExecutionFinish() {
        try {
            DeviceManager.shutdownEmulatorOrSimulator();
            AppiumServer.stopServer();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
