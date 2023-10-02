package ui;

import test.Test;
import user.User;
import user.UserService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class UserUI {
    public final Scanner scannerInt = new Scanner(System.in);
    public final Scanner scannerStr = new Scanner(System.in);
    private final UserService userService = UserService.getInstance();

    public void start() {
        boolean isExited = false;
        while (!isExited) {
            System.out.print("""
                    1. Sign up
                    2. Sign in
                    0. Exit
                    >>>\s
                    """);
            int command = scannerInt.nextInt();
            switch (command) {
                case 1 -> signUp();
                case 2 -> signIn();
                case 0 -> isExited = true;
                default -> System.out.println("Wrong commmand!");
            }
        }
    }

    private void signIn() {
        System.out.print("Enter your username: ");
        String username = scannerStr.nextLine();
        System.out.print("Enter your password: ");
        String password = scannerStr.nextLine();
        if (userService.checkByUsernameAndPassword(username, password)) {
            User user = userService.findByUsername(username).get();
            mainMenu(user.getId());
        } else {
            System.out.println("Username or password is wrong. Try again !");
        }
    }

    private void mainMenu(UUID id) {
        boolean isExit = false;

        while (!isExit) {
            System.out.print("""
                    1. Pass by Exam
                    2. Created Exam
                                    
                    0. Exit
                    >> \s""");

            int command = scannerInt.nextInt();

            switch (command) {
                case 1 -> passByExam(id);
                case 2 -> createExam(id);
                case 0 -> isExit = true;
                default -> System.out.println("Wrong command!");
            }
        }
    }

    private void passByExam(UUID id) {

    }

    private void createExam(UUID id) {
        System.out.println("Test yaratish ");

        System.out.println("Test yaratadigan fanigizni tanlang: ");
        System.out.println("Savolni yozing: ");
      String createTest =  scannerStr.nextLine();

        Test test = new Test();







    }

    private void signUp() {
        System.out.print("Enter your name: ");
        String name = scannerStr.nextLine();
        System.out.print("Enter your surname: ");
        String surname = scannerStr.nextLine();
        System.out.print("Enter username: ");
        String username = scannerStr.nextLine();
        System.out.print("Enter password: ");
        String password = scannerStr.nextLine();
        if (userService.findByUsername(username).isEmpty()) {
            User user = new User(UUID.randomUUID(), name, surname, username, password, LocalDateTime.now());
            userService.save(user);
            System.out.println("Created");
        } else {
            System.out.println("This username already created");
        }

    }
}
