package database;

public interface StringUtils {
    String createDatabase = """
            create database dtm_database;
            """;
    String createUserTable = """
            create table if not exists "user"
            (
                id        uuid primary key default gen_random_uuid(),
                firstname      varchar        not null,
                lastname varchar        not null,
                username  varchar unique not null,
                password  varchar        not null,
                created   timestamp        default now()
            );
                 
            """;
    String createExamTable = """
                        
             create type exam_type as enum ('REGULAR','EXAM');
             
            create table if not exists exam
            (
                id   uuid primary key default gen_random_uuid(),
                name varchar,
                type exam_type
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
            (firstname, lastname, username, password, created)
            values
            (?, ?, ?, ?, ?);
            """;

    String updateUser = """
            update "user"
            set
            firstname = ?,
            lastname = ?,
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

    String createAnswer = """
            insert into  answer
            (id, answer,is_answer,test_id)
            values
            (?, ?, ?, ?);
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

    String insertTest = """
            insert into exam_test(id, question, description, exam_id) values(?, ?, ?, ?);
                        """;
    String findByIdTest = """
            select * from exam_test where id = ?;
            """;
    String getAllTests = """
            select * from exam_test;
            """;
    String deleteTest = """
            delete from exam_test where id = ?; 
            """;
    String updateTest = """
            update exam_test set
            question = ?,
            description = ?,
            exam_id = ?
            """;

    String createExam = """
            insert into exam (id, name, type)
             values (?, ?, cast(? as exam_type));
            """;

    String updateExam = """
            update exam
            set name = ?,
            type = cast(? as exam_type)
            where id = ?;
            """;

    String deleteExam = """
            delete
            from exam
            where id = ?;
            """;

    String findByIdExam = """
            select *
            from exam
            where id = ?;
            """;

    String findByAllExam = """
            select *
            from exam;
            """;

    String propertiesPath = "src/main/resources/project.properties";
    String loggerPath = "logger.log";
    String isRunning = "Program is successful running";
}
