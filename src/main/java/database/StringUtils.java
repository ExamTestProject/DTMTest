package database;

public interface StringUtils {
    String createDatabase = """
            create database dtm_database;
            """;
    String createUserTable = """
            create table if not exists "user"
            (
                id        uuid primary key default gen_random_uuid(),
                name      varchar        not null,
                firstname varchar        not null,
                username  varchar unique not null,
                password  varchar        not null,
                created   timestamp        default now()
            );
            """;
    String createExamTable = """
            create table if not exists exam
            (
                id   uuid primary key default gen_random_uuid(),
                name varchar
            );
            """;
    String createTestTable = """
            create table if not exists exam_test
            (
                id          uuid primary key default gen_random_uuid(),
                question    varchar not null,
                description text    not null,
                exam_id     uuid references exam (id) on delete cascade
            );
            """;
    String createAnswerTable = """
            create table if not exists answer
            (
                id        uuid primary key default gen_random_uuid(),
                answer    varchar,
                is_answer boolean,
                test_id   uuid references exam_test (id) on delete cascade
            );
            """;

    String createUser = """
            insert into "user"
            (name, firstname, username, password, created)
            values
            (?, ?, ?, ?, ?);
            """;

    String updateUser = """
            update "user"
            set
            name = ?,
            firstname = ?,
            username = ?,
            password = ?
            where id = ?;
            """;

    String userDelete = """
            delete from "user" where id = ?;
            """;

    String userFindById = """
            select * from "user" where id = ?;
            """;

    String userSelectAll = """
            select * from "user";
             """;
    String propertiesPath = "src/main/resources/project.properties";
    String loggerPath = "logger.log";
    String isRunning = "Program is successful running";
}
