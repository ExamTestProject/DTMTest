import database.DatabaseController;
import test.Test;
import test.TestRepository;

import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        DatabaseController databaseController = new DatabaseController();
        databaseController.start();
        TestRepository instance = TestRepository.getInstance();
        instance.save(new Test(UUID.randomUUID(), "1", "1", UUID.randomUUID()));
    }
}