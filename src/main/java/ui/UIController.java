package ui;

import user.User;
import user.UserService;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.UUID;

public class UIController {

    private final UserService userService = UserService.getInstance();
    public final Scanner scannerInt = new Scanner(System.in);
    public final Scanner scannerStr = new Scanner(System.in);


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
            UserUI userUI = new UserUI();
            userUI.mainMenu(user.getId());
        } else {
            System.out.println("Username or password is wrong. Try again !");
        }
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
