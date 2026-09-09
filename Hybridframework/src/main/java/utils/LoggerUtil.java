package utils;

import com.aventstack.extentreports.Status;
import reports.ExtentManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtil {

    private static final Logger logger = LogManager.getLogger(LoggerUtil.class);

    public static void info(String message) {
        logger.info(message);
        if (ExtentManager.getTest() != null) {
            ExtentManager.getTest().log(Status.INFO, message);
        }
    }

    public static void pass(String message) {
        logger.info("[PASS] " + message);
        if (ExtentManager.getTest() != null) {
            ExtentManager.getTest().log(Status.PASS, message);
        }
    }

    public static void warn(String message) {
        logger.warn(message);
        if (ExtentManager.getTest() != null) {
            ExtentManager.getTest().log(Status.WARNING, message);
        }
    }

    public static void error(String message, Throwable t) {
        logger.error(message, t);
        if (ExtentManager.getTest() != null) {
            ExtentManager.getTest().log(Status.FAIL, message);
            ExtentManager.getTest().log(Status.FAIL, t);
        }
    }
}
