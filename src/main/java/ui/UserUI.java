package ui;

import answer.Answer;
import answer.AnswerService;
import exam.Exam;
import exam.ExamService;
import exam.ExamType;
import logger.OurLogger;
import test.Test;
import test.TestRepository;
import test.TestService;
import test.view.TestView;
import user.User;
import user.UserService;

import java.time.LocalDateTime;
import java.util.*;

public class UserUI {
    public final Scanner scannerInt = new Scanner(System.in);
    public final Scanner scannerStr = new Scanner(System.in);
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public final Scanner scannerBoolean = new Scanner(System.in);
    private final UserService userService = UserService.getInstance();
    private final ExamService examService = ExamService.getInstance();
    private final AnswerService answerService = AnswerService.getInstance();
    private final TestService testService = TestService.getInstance();


    public void mainMenu(UUID id) {
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

    private void passByExam(UUID id) {
        if (examService.findAll().size() > 0) {
            boolean isExit = false;
            int offset = 0;
            int limit = 10;

            while (!isExit) {
                List<Exam> getExams = examService.getExamByLimit(offset, limit);
                if (!getExams.isEmpty()) {
                    int count = 1;
                    for (Exam getExam : getExams) {
                        System.out.println(count + ": " + getExam.getName() + " -> " + getExam.getExamType().name());
                        count++;
                    }

                    System.out.println(ANSI_GREEN + "< : Prev | Next : >" + ANSI_RESET);
                    System.out.print("\n>>> ");

                    String command = scannerStr.nextLine();

                    if (command.equals(">") || command.equals("<")) {
                        if (command.equals(">")) {
                            offset += 10;
                        } else {
                            offset -= 10;
                        }
                    } else {
                        try {
                            int commandInt = Integer.parseInt(command);
                            if (commandInt > 0 && commandInt <= getExams.size()) {
                                Exam exam = getExams.get(commandInt - 1);
                                List<Test> tests = testService.findByExamId(exam.getId());
                                if (tests.size() > 0) {
                                    enterToTest(exam.getId());
                                } else {
                                    System.out.println("Not Found test this exam!");
                                }
                            } else if (commandInt == 0) {
                                isExit = true;
                            } else {
                                System.out.println("Wrong command!");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Wrong command!");
                        }

                    }
                } else {
                    if (offset < 0) {
                        offset += 10;
                    } else {
                        offset -= 10;
                    }
                    System.out.println("Not found exam!");
                }
            }
        } else {
            System.out.println("Not found exam!");
        }
    }

    private void enterToTest(UUID examId) {
        List<Test> tests = testService.findByExamId(examId);
        List<TestView> testViews = tests
                .stream()
                .map(test -> new TestView(test.getId(), false, false, 0))
                .toList();

        int testCount = tests.size();
        int correctAnswer = 0;
        int wrongAnswer = 0;

        int position = 0;

        while (true) {
            Test test = tests.get(position);
            TestView testView = testViews.get(position);
            List<Answer> answers = answerService.findByTestId(test.getId());
            int count = 1;
            if (testView.isDone()) {
                System.out.println("Question: " + test.getQuestion());
                System.out.println("\nDescription: " + test.getDescription());
                for (Answer answer : answers) {
                    if (testView.getChooseAnswer() == count) {
                        System.out.println(ANSI_GREEN + count + ": " + answer.getAnswer() + ANSI_RESET);
                    } else {
                        System.out.println(count + ": " + answer.getAnswer());
                    }
                    count++;
                }
            } else {
                System.out.println("Question: " + test.getQuestion());
                System.out.println("\nDescription: " + test.getDescription());
                for (Answer answer : answers) {
                    System.out.println(count + ": " + answer.getAnswer());
                    count++;
                }
            }
            System.out.println();

            if (position == testCount - 1) {
                System.out.print("""
                        <: Prev Test
                        *: Finish Test
                                            
                        >>>\s
                        """);

                String command = scannerStr.nextLine();

                if (command.equalsIgnoreCase("<")) {
                    if (position != 0) {
                        position--;
                    } else {
                        System.out.println("Not found test!");
                    }
                } else if (command.equalsIgnoreCase("*")) {
                    int viewCount = 1;
                    for (TestView view : testViews) {
                        Test test1 = testService.findById(view.getId()).get();
                        if (view.isDone()) {
                            System.out.println("\u001B[34m" + viewCount + ": " + test1.getQuestion() + ANSI_RESET);
                        } else {
                            System.out.println("\u001B[33m" + viewCount + ": " + test1.getQuestion() + ANSI_RESET);
                        }
                        viewCount++;
                    }
                    System.out.print("Are you finish test? (Y/N): ");
                    String finishCommand = scannerStr.nextLine();
                    if (finishCommand.equalsIgnoreCase("y")) {
                        Optional<Exam> examOptional = examService.findById(examId);
                        if (examOptional.isPresent()) {
                            Exam exam = examOptional.get();
                            int testCounts = 0;
                            if (exam.getExamType() == ExamType.REGULAR) {
                                for (Test test1 : tests) {
                                    List<Answer> answerList = answerService.findByTestId(test1.getId());
                                    int correctAnswerInt = 0;
                                    for (int i = 0; i < answerList.size(); i++) {
                                        if (answerList.get(i).isCorrect()) {
                                            correctAnswerInt = i + 1;
                                            break;
                                        }
                                    }
                                    TestView testView1 = testViews.get(testCounts);

                                    System.out.println("\n");
                                    System.out.println("Question: " + test1.getQuestion());
                                    System.out.println();
                                    System.out.println("Description: " + test1.getDescription());
                                    for (int i = 0; i < answerList.size(); i++) {
                                        if (correctAnswerInt == (i + 1)) {
                                            System.out.println(ANSI_GREEN + (i + 1) + ": " + test1.getQuestion() + ANSI_RESET);
                                        } else if (testView1.getChooseAnswer() == (i + 1)) {
                                            System.out.println(ANSI_RED + (i + 1) + ": " + test1.getQuestion() + ANSI_RESET);
                                        } else {
                                            System.out.println((i + 1) + ": " + test1.getQuestion());
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    } else if (finishCommand.equalsIgnoreCase("n")) {

                    } else {
                        try {
                            int finishCommandInt = Integer.parseInt(finishCommand);
                            if (finishCommandInt > 0 && finishCommandInt <= testCount) {
                                position = finishCommandInt - 1;
                            } else {
                                System.out.println("Wrong command!");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Wrong command!");
                        }
                    }
                } else {
                    try {
                        int commandInt = Integer.parseInt(command);
                        if (commandInt > 0 && commandInt <= count) {
                            Answer answer = answerService.findAll().get(commandInt - 1);
                            testView.setChooseAnswer(commandInt);
                            if (answer.isCorrect()) {
                                if (testView.isDone()) {
                                    if (!testView.isCorrectAnswer()) {
                                        wrongAnswer--;
                                        correctAnswer++;
                                    }
                                } else {
                                    correctAnswer++;
                                    testView.setCorrectAnswer(true);
                                }
                            } else {
                                if (testView.isDone()) {
                                    if (testView.isCorrectAnswer()) {
                                        correctAnswer--;
                                        wrongAnswer++;
                                    }
                                } else {
                                    wrongAnswer++;
                                    testView.setCorrectAnswer(false);
                                }
                            }
                            testView.setDone(true);
                        } else {
                            System.out.println("Wrong Command!");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Wrong command!");
                    }
                }

            } else {
                System.out.print("""
                        <: Prev Test
                        >: Next Test
                        *: Skip Test
                                            
                        >>>\s
                        """);
                String command = scannerStr.nextLine();
                switch (command) {
                    case "<" -> {
                        if (position != 0) {
                            position--;
                        } else {
                            System.out.println("Not found test!");
                        }
                    }
                    case ">" -> {
                        if (position != testCount - 1) {
                            position++;
                        } else {
                            System.out.println("Not found test!");
                        }
                    }
                    case "*" -> position++;
                    default -> {
                        try {
                            int commandInt = Integer.parseInt(command);
                            if (commandInt > 0 && commandInt <= count) {
                                Answer answer = answerService.findAll().get(commandInt - 1);
                                testView.setChooseAnswer(commandInt);
                                if (answer.isCorrect()) {
                                    if (testView.isDone()) {
                                        if (!testView.isCorrectAnswer()) {
                                            wrongAnswer--;
                                            correctAnswer++;
                                        }
                                    } else {
                                        correctAnswer++;
                                        testView.setCorrectAnswer(true);
                                    }
                                } else {
                                    if (testView.isDone()) {
                                        if (testView.isCorrectAnswer()) {
                                            correctAnswer--;
                                            wrongAnswer++;
                                        }
                                    } else {
                                        wrongAnswer++;
                                        testView.setCorrectAnswer(false);
                                    }
                                }
                                position++;
                                testView.setDone(true);
                            } else {
                                System.out.println("Wrong Command!");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Wrong command!");
                        }
                    }
                }
            }


        }

        System.out.printf("""
                Tablriklayman Testni muvaffaqiyatli topshirdingiz!
                        
                Sizga omad tilayman sizning natijalaringiz!
                        
                To'g'ri javoblar: %d
                Noto'g'ri javoblar: %d
                Jami savollar: %d
                %n""", correctAnswer, wrongAnswer, testCount);

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

            System.out.print("Please enter answer count: ");
            int answerCount = scannerInt.nextInt();

            int correctAnswer = 0;
            int count = answerCount;
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


}
