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
                password  varchar        not null check ( length(password) = 64 ),
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

    String createAnswer = """
            insert into  answer
            (answer,is_answer,test_id)
            values
            (?, ?, ?);
             """;
    String updateAnswer = """
            update answer
            set
            answer = ?,
            is_answer = ?,
            test_id = ?
            where id = ?;
            """;
    String deleteAnswer = """
            drop from  answer 
            where id =?;
                    
            """;
    String findByIdAnswer = """
            select * from answer
            where id = ?;
            """;
    String findAllAnswer = """
            select * from answer;
            
            """;
    String propertiesPath = "src/main/resources/project.properties";
    String loggerPath = "logger.log";
    String isRunning = "Program is successful running";
}
