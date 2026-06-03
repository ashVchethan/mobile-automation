package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtils {
    private static final Logger LOGGER = LogManager.getLogger(LoggerUtils.class);

    public enum LogsType {
        INFO,
        FAIL,
        WARN,
        DEBUG
    }

    public static void ReportLog(LogsType type, String message) {
        switch (type) {
            case INFO:
                LOGGER.info(message);
                break;
            case FAIL:
                LOGGER.error(message);
                break;
            case WARN:
                LOGGER.warn(message);
                break;
            case DEBUG:
                LOGGER.debug(message);
                break;
        }
    }
}
