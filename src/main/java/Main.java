import database.DatabaseController;
import exam.ExamRepository;
import ui.UIController;

public class Main {

    public static void main(String[] args) {
        DatabaseController databaseController = new DatabaseController();
        UIController uiController = new UIController();
        databaseController.start();
        uiController.start();
    }
}