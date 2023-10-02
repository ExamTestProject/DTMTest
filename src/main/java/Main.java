import database.DatabaseController;
import ui.UserUI;

public class Main {

    public static void main(String[] args) {
        DatabaseController databaseController = new DatabaseController();
        databaseController.start();
        UserUI userUI = new UserUI();
        userUI.start();
    }

}
