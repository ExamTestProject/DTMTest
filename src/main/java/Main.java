import database.DatabaseController;
import exam.Exam;
import exam.ExamRepository;
import test.Test;
import test.TestRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        DatabaseController databaseController = new DatabaseController();
        databaseController.start();
    }
}