import database.DatabaseController;
import ui.UI;

public class Main {


    public static void main(String[] args) {
        DatabaseController databaseController = new DatabaseController();
        databaseController.start();
        UI.start();
    }
}
