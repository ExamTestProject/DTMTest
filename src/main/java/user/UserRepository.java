package user;

import com.google.common.hash.Hashing;
import database.DatabaseService;
import database.StringUtils;
import logger.OurLogger;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import repository.Repository;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.LogRecord;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserRepository implements Repository<UUID, User> {
    private static final UserRepository userRepository = new UserRepository();

    @Override
    public void save(User user) {
        try (Connection connection = DatabaseService.connection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(StringUtils.createUser);
            String password = Hashing.sha256()
                    .hashString(user.getPassword(), StandardCharsets.UTF_8)
                    .toString();
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getFirstname());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, password);
            preparedStatement.setObject(5, user.getCreated());
            preparedStatement.execute();
        } catch (SQLException e) {
            OurLogger.throwLog(new LogRecord(Level.SEVERE, e.getMessage()));
        }
    }

    @Override
    public void update(User user) {
        try (Connection connection = DatabaseService.connection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(StringUtils.updateUser);

            String password = Hashing.sha256()
                    .hashString(user.getPassword(), StandardCharsets.UTF_8)
                    .toString();

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getFirstname());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, password);
            preparedStatement.setObject(5, user.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            OurLogger.throwLog(new LogRecord(Level.SEVERE, e.getMessage()));
        }
    }

    @Override
    public void delete(UUID uuid) {
        try (Connection connection = DatabaseService.connection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(StringUtils.userDelete);
            preparedStatement.setObject(1, uuid);
            preparedStatement.execute();
        } catch (SQLException e) {
            OurLogger.throwLog(new LogRecord(Level.SEVERE, e.getMessage()));
        }
    }

    @Override
    public Optional<User> findById(UUID uuid) {
        try (Connection connection = DatabaseService.connection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(StringUtils.userFindById);
            preparedStatement.setObject(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                UUID id = (UUID) resultSet.getObject("id");
                String name = resultSet.getString("name");
                String firstname = resultSet.getString("firstname");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String created = resultSet.getString("created");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.n");
                LocalDateTime localDate = LocalDateTime.parse(created, formatter);
                return Optional.of(new User(id, name, firstname, username, password, localDate));
            }
        } catch (SQLException e) {
            OurLogger.throwLog(new LogRecord(Level.SEVERE, e.getMessage()));
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = DatabaseService.connection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(StringUtils.userSelectAll);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UUID id = (UUID) resultSet.getObject("id");
                String name = resultSet.getString("name");
                String firstname = resultSet.getString("firstname");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String created = resultSet.getString("created");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.n");
                LocalDateTime localDate = LocalDateTime.parse(created, formatter);
                users.add(new User(id, name, firstname, username, password, localDate));
            }
        } catch (SQLException e) {
            OurLogger.throwLog(new LogRecord(Level.SEVERE, e.getMessage()));
        }
        return users;
    }

    public static UserRepository getInstance() {
        return userRepository;
    }
}
