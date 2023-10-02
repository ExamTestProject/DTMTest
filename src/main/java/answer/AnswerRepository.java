package answer;

import database.DatabaseService;
import database.StringUtils;
import logger.OurLogger;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import repository.Repository;

import javax.swing.*;
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
public class AnswerRepository implements Repository<UUID, Answer> {
    private final static AnswerRepository answerRepository = new AnswerRepository();

    @Override
    public void save(Answer answer) {
        Connection connection = DatabaseService.connection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(StringUtils.createAnswer);
            preparedStatement.setString(1, answer.getAnswer());
            preparedStatement.setBoolean(2, answer.isAnswer());
            preparedStatement.setObject(3, answer.getTestId());
            preparedStatement.execute();
            connection.close();
        } catch (SQLException e) {
            OurLogger.throwLog(new LogRecord(Level.SEVERE, e.getMessage()));
        }

    }

    @Override
    public void update(Answer answer) {
        Connection connection = DatabaseService.connection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(StringUtils.updateAnswer);
            preparedStatement.setString(1, answer.getAnswer());
            preparedStatement.setBoolean(2, answer.isAnswer());
            preparedStatement.setObject(3, answer.getTestId());
            preparedStatement.setObject(4, answer.getId());
            preparedStatement.executeUpdate();
            connection.close();


        } catch (SQLException e) {
            OurLogger.throwLog(new LogRecord(Level.SEVERE, e.getMessage()));
        }


    }

    @Override
    public void delete(UUID uuid) {
        Connection connection = DatabaseService.connection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(StringUtils.deleteAnswer);
            preparedStatement.setObject(1, uuid);
            preparedStatement.execute();
            connection.close();

        } catch (SQLException e) {
            OurLogger.throwLog(new LogRecord(Level.SEVERE, e.getMessage()));
        }


    }

    @Override
    public Optional<Answer> findById(UUID uuid) {
        Connection connection = DatabaseService.connection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(StringUtils.findByIdAnswer);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                UUID id = (UUID) resultSet.getObject("id");
                String answer = resultSet.getString("answer");
                boolean isAnswer = resultSet.getBoolean("is_answer");
                UUID testId = (UUID) resultSet.getObject("test_id");
                Answer answer1 = new Answer(id, answer, isAnswer, testId);

                return Optional.of(answer1);
            }
        } catch (SQLException e) {
            OurLogger.throwLog(new LogRecord(Level.SEVERE, e.getMessage()));

        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                OurLogger.throwLog(new LogRecord(Level.SEVERE, e.getMessage()));
            }

        }
        return Optional.empty();
    }

    @Override
    public List<Answer> findAll() {
        Connection connection = DatabaseService.connection();
        List<Answer> allAnswer = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(StringUtils.findAllAnswer);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSet resultSetAnswer = preparedStatement.executeQuery();
            while (resultSetAnswer.next()) {
                UUID id = (UUID) resultSet.getObject("id");
                String answer = resultSet.getString("answer");
                boolean isAnswer = resultSet.getBoolean("is_answer");
                UUID testId = (UUID) resultSet.getObject("test_id");
                Answer answer2 = new Answer(id, answer, isAnswer, testId);
                allAnswer.add(answer2);
            }


        } catch (SQLException e) {
            OurLogger.throwLog(new LogRecord(Level.SEVERE, e.getMessage()));
        }
        return allAnswer;


    }

    public static AnswerRepository getInstance() {
        return answerRepository;
    }


}
