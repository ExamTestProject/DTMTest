package logger;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.logging.LogRecord;
import java.util.logging.Logger;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OurLogger {
    private static Logger logger = Logger.getGlobal();
    private static FileHandler fileHandler = new FileHandler();

    public static void throwLog(LogRecord record) {
        logger.setUseParentHandlers(true);
        fileHandler.publish(record);
    }
}
