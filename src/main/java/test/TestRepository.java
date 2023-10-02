package test;

import database.DatabaseService;
import database.StringUtils;
import logger.OurLogger;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import repository.Repository;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.LogRecord;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestRepository implements Repository<UUID, Test> {

    private static final TestRepository testRepository = new TestRepository();

    @Override
    public void save(Test test) {
        try (Connection connection = DatabaseService.connection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(StringUtils.insertTest);
            preparedStatement.setString(1, test.getQuestion());
            preparedStatement.setString(2, test.getDescription());
            preparedStatement.setObject(3, test.getExam_id());
            preparedStatement.execute();
        } catch (SQLException e) {
            OurLogger.throwLog(new LogRecord(Level.SEVERE, e.getMessage()));
        }
    }

    @Override
    public void update(Test test) {
        try (Connection connection = DatabaseService.connection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(StringUtils.updateTest);
            preparedStatement.setString(1, test.getQuestion());
            preparedStatement.setString(2, test.getDescription());
            preparedStatement.setObject(3, test.getExam_id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            OurLogger.throwLog(new LogRecord(Level.SEVERE, e.getMessage()));
        }
    }

    @Override
    public void delete(UUID uuid) {
        try (Connection connection = DatabaseService.connection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(StringUtils.deleteTest);
            preparedStatement.setObject(1, uuid);
            preparedStatement.execute();
        } catch (SQLException e) {
            OurLogger.throwLog(new LogRecord(Level.SEVERE, e.getMessage()));
        }
    }

    @Override
    public Optional<Test> findById(UUID uuid) {
        try (Connection connection = DatabaseService.connection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(StringUtils.findByIdTest);
            preparedStatement.setObject(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                UUID id = (UUID) resultSet.getObject("id");
                String question = resultSet.getString("question");
                String description = resultSet.getString("description");
                UUID exam_id = (UUID) resultSet.getObject("exam_id");
                Test test = new Test(id, question, description, exam_id);
                return Optional.of(test);
            }
        } catch (SQLException e) {
            OurLogger.throwLog(new LogRecord(Level.SEVERE, e.getMessage()));
        }
        return Optional.empty();
    }

    @Override
    public List<Test> findAll() {
        ArrayList<Test> tests = new ArrayList<>();
        try (Connection connection = DatabaseService.connection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(StringUtils.getAllTests);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UUID id = (UUID) resultSet.getObject("id");
                String question = resultSet.getString("question");
                String description = resultSet.getString("description");
                UUID exam_id = (UUID) resultSet.getObject("exam_id");
                Test test = new Test(id, question, description, exam_id);
                tests.add(test);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tests;
    }

    public static TestRepository getInstance() {
        return testRepository;
    }
}
