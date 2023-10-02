package ui;

import answer.Answer;
import answer.AnswerService;
import exam.Exam;
import exam.ExamService;
import exam.ExamType;
import test.Test;
import test.TestService;
import user.User;
import user.UserService;

import java.time.LocalDateTime;
import java.util.*;

public class UserUI {
    public final Scanner scannerInt = new Scanner(System.in);
    public final Scanner scannerStr = new Scanner(System.in);

    public final Scanner scannerBoolean = new Scanner(System.in);
    private final UserService userService = UserService.getInstance();
    private final ExamService examService = ExamService.getInstance();
    private final AnswerService answerService = AnswerService.getInstance();
    private final TestService testService = TestService.getInstance();

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
                    3. Create Test
                                    
                    0. Exit
                    >> \s""");

            int command = scannerInt.nextInt();

            switch (command) {
                case 1 -> passByExam(id);
                case 2 -> createExam(id);
                case 3 -> createTest(id);
                case 0 -> isExit = true;
                default -> System.out.println("Wrong command!");
            }
        }
    }

    private void createTest(UUID id) {
        List<Exam> all = examService.findAll();

        if (!all.isEmpty()) {
            boolean isExited = false;
            while (!isExited) {
                int count = 1;
                for (Exam exam : all) {
                    System.out.println(count + " : " + exam.getName());
                    count++;
                }
                System.out.print("0. Exit\n>>> ");

                int command = scannerInt.nextInt();

                if (command > 0 && all.size() >= command) {
                    addTest(command);

                } else if (command == 0) {
                    isExited = true;
                } else {
                    System.out.print("Wrong command !");
                }
            }
        } else {
            System.out.println("Not found Exam!");
        }

    }

    private void addTest(int command) {
        System.out.print("Enter test question: ");
        String question = scannerStr.nextLine();

        if (question.length() <= 255) {
            System.out.print("Enter test description: ");
            String description = scannerStr.nextLine();


            List<Exam> all = examService.findAll();
            Exam exam = all.get(command - 1);

            Test test = new Test(UUID.randomUUID(), question, description, exam.getId());
            testService.save(test);

            int correctAnswer = 0;
            int count = 4;
            while (count != 0) {
                System.out.print("Enter answer: ");
                String answer = scannerStr.nextLine();
                Answer answer1 = new Answer(UUID.randomUUID(), answer, false, test.getId());
                if (correctAnswer != 1) {
                    System.out.print("Is correct answer? (bool) : ");
                    if (scannerBoolean.nextBoolean()) {
                        correctAnswer = 1;
                        answer1.setCorrect(true);
                    }
                }
                answerService.save(answer1);
                count--;
            }

            System.out.println("Done!");

        } else {
            System.out.println("Long text you entered");
        }


    }

    private void passByExam(UUID id) {

    }

    private void createExam(UUID id) {

        System.out.print("Enter exam name: ");
        String examName = scannerStr.nextLine();

        System.out.print("Enter is exam regular? (bool) : ");
        boolean isExam = scannerInt.nextBoolean();
        Exam exam = new Exam(UUID.randomUUID(), examName, null);

        if (isExam) {
            exam.setExamType(ExamType.REGULAR);
        } else {
            exam.setExamType(ExamType.EXAM);
        }

        examService.save(exam);

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
