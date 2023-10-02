package database;

import lombok.*;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class DatabaseService {
    private final static Properties properties = new Properties();
    private final static String username;
    private final static String password;
    private final static String type;
    private final static String url;

    static {
        try {
            properties.load(new FileReader(StringUtils.propertiesPath));
            username = properties.getProperty("property.database.user");
            password = properties.getProperty("property.database.password");
            type = properties.getProperty("property.database.type");
            url = properties.getProperty("property.database.url");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public static Connection connection() {
        String dbUrl = "jdbc:" + type + "://" + url + "/dtm_database";
        return DriverManager.getConnection(dbUrl, username, password);
    }

    @SneakyThrows
    public static Connection connectionDefaultDatabase() {
        String defaultDatabase = properties.getProperty("property.database.default.database");
        String dbUrl = "jdbc:" + type + "://" + url + "/" + defaultDatabase;
        return DriverManager.getConnection(dbUrl, username, password);
    }
}
