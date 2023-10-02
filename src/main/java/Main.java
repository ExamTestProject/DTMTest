import database.DatabaseController;
import ui.UI;
import user.User;
import user.UserRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        DatabaseController databaseController = new DatabaseController();
        databaseController.start();
    }
}
