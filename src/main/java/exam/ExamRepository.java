package exam;

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
public class ExamRepository implements Repository<UUID, Exam> {

    private static final ExamRepository examRepository = new ExamRepository();

    @Override
    public void save(Exam exam) {
        Connection connection = DatabaseService.connection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(StringUtils.createExam);
            preparedStatement.setObject(1, exam.getId());
            preparedStatement.setString(2,exam.getName());
            preparedStatement.setString(3, exam.getExamType().name());
            preparedStatement.execute();
            connection.close();
        } catch (SQLException e) {
            OurLogger.throwLog(new LogRecord(Level.SEVERE, e.getMessage()));
        }
    }

    @Override
    public void update(Exam exam) {
        Connection connection = DatabaseService.connection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(StringUtils.updateExam);
            preparedStatement.setString(1, exam.getName());
            preparedStatement.setString(2,exam.getExamType().name());
            preparedStatement.setObject(3, exam.getId());
            preparedStatement.execute();
            connection.close();
        } catch (SQLException e) {
            OurLogger.throwLog(new LogRecord(Level.SEVERE, e.getMessage()));
        }
    }

    @Override
    public void delete(UUID uuid) {
        Connection connection = DatabaseService.connection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(StringUtils.deleteExam);
            preparedStatement.setObject(1, uuid);
            preparedStatement.execute();
            connection.close();
        } catch (SQLException e) {
            OurLogger.throwLog(new LogRecord(Level.SEVERE, e.getMessage()));
        }

    }

    @Override
    public Optional<Exam> findById(UUID uuid) {

        try (Connection connection = DatabaseService.connection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(StringUtils.findByIdExam);
            preparedStatement.setObject(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                UUID id = (UUID) resultSet.getObject("id");
                String name = resultSet.getString("name");
                String type = resultSet.getObject("type").toString();
                ExamType examType = ExamType.valueOf(type);
                Exam exam = new Exam(id, name, examType);
                return Optional.of(exam);
            }
        } catch (SQLException e) {
            OurLogger.throwLog(new LogRecord(Level.SEVERE, e.getMessage()));
        }
        return Optional.empty();
    }

    @Override
    public List<Exam> findAll() {
        List<Exam> examList = new ArrayList<>();
        try (Connection connection = DatabaseService.connection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(StringUtils.findByAllExam);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                UUID id = (UUID) resultSet.getObject("id");
                String name = resultSet.getString("name");
                String type = resultSet.getObject("type").toString();
                ExamType examType = ExamType.valueOf(type);
                Exam exam = new Exam(id, name, examType);
                examList.add(exam);
            }
        } catch (SQLException e) {
            OurLogger.throwLog(new LogRecord(Level.SEVERE, e.getMessage()));
        }
        return examList;
    }

    public static ExamRepository getInstance() {
        return examRepository;
    }
}
