package logger;

import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

@NoArgsConstructor
public class LoggerFormatter extends Formatter {

    private static final LoggerFormatter loggerFormater = new LoggerFormatter();

    @Override
    public String format(LogRecord record) {
        return record.getLevel() + ": " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " -> " + record.getMessage();
    }

    public static LoggerFormatter getInstance() {
        return loggerFormater;
    }
}
