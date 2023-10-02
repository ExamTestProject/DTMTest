package logger;

import database.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

public class FileHandler extends StreamHandler {
    @Override
    public synchronized void publish(LogRecord record) {
        Path of = Path.of(StringUtils.loggerPath);
        try {
            if (!Files.exists(of)) {
                Files.createFile(of);
            }
            Files.write(of, (LoggerFormatter.getInstance().format(record) + "\n" + Files.readString(of)).getBytes());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
