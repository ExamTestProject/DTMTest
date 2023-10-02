package ui;

import user.User;
import user.UserService;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.UUID;

public class UI {
    public static Scanner scannerInt = new Scanner(System.in);
    public static Scanner scannerStr = new Scanner(System.in);
    private static final UserService userService = UserService.getInstance();

    public static void start() {
        boolean isExited = false;
        while (!isExited) {
            System.out.print("""
                    1. Sign up
                    2. Sign in
                    0. Exit
                    >>> \s""");
            int command = scannerInt.nextInt();
            switch (command) {
                case 1 -> signUp();
                case 2 -> signIn();
                case 0 -> isExited = true;
                default -> System.out.println("Wrong command!");
            }
        }
    }

    private static void signIn() {
        System.out.print("Enter your username: ");
        String username = scannerStr.nextLine();
        System.out.print("Enter your password: ");
        String password = scannerStr.nextLine();
        if(userService.checkByUsernameAndPassword(username, password)){
            System.out.println("You've entered successfully");
        }else{
            System.out.println("Username or password is wrong. Try again !");
        }
    }

    private static void signUp() {
        System.out.print("Enter your name: ");
        String name = scannerStr.nextLine();
        System.out.print("Enter your surname: ");
        String surname = scannerStr.nextLine();
        System.out.print("Enter username: ");
        String username = scannerStr.nextLine();
        System.out.print("Enter password: ");
        String password = scannerStr.nextLine();

        User user = new User(UUID.randomUUID(), name, surname, username, password, LocalDateTime.now());
        userService.save(user);
        System.out.println("You've successfully entered");
    }
}
