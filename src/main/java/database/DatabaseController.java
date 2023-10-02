package database;

import logger.OurLogger;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.LogRecord;

@NoArgsConstructor
public class DatabaseController {
    public void start() {

        OurLogger.throwLog(new LogRecord(Level.FINE, StringUtils.isRunning));

        try (Connection defaultDatabase = DatabaseService.connectionDefaultDatabase()) {
            defaultDatabase.prepareStatement(StringUtils.createDatabase).execute();
        } catch (SQLException e) {
            OurLogger.throwLog(new LogRecord(Level.SEVERE, e.getMessage()));
        }

        Connection connection = DatabaseService.connection();

        try {
            connection.prepareStatement(StringUtils.createUserTable).execute();
        } catch (SQLException e) {
            OurLogger.throwLog(new LogRecord(Level.WARNING, e.getMessage()));
        }

        try {
            connection.prepareStatement(StringUtils.createExamTable).execute();
        } catch (SQLException e) {
            OurLogger.throwLog(new LogRecord(Level.WARNING, e.getMessage()));
        }

        try {
            connection.prepareStatement(StringUtils.createTestTable).execute();
        } catch (SQLException e) {
            OurLogger.throwLog(new LogRecord(Level.WARNING, e.getMessage()));
        }

        try {
            connection.prepareStatement(StringUtils.createAnswerTable).execute();
        } catch (SQLException e) {
            OurLogger.throwLog(new LogRecord(Level.WARNING, e.getMessage()));
        }

        try {
            connection.close();
        } catch (SQLException e) {
            OurLogger.throwLog(new LogRecord(Level.WARNING, e.getMessage()));
        }
    }
}