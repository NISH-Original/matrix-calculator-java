package ui;

import model.*;
import persistence.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 Class for main matrix calculator.
 Handles user input and shows output.
 */
public class MatrixCalculator {
    private final Scanner input = new Scanner(System.in);
    private static final String JSON_STORE = "./data/session_histories.json";
    private HistoryManager sessionHistory = new HistoryManager();
    private List<List<Double>> matrix1;
    private List<List<Double>> matrix2;
    private List<List<Double>> answer;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private boolean isSaved = false;

    // EFFECTS: runs the calculator
    public MatrixCalculator() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runCalculator();
    }

    // EFFECTS: shows the user options and takes command input
    @SuppressWarnings("methodlength")
    private void runCalculator() {
        boolean keepGoing = true;

        while (keepGoing) {
            System.out.println("\n\nHello! Choose one of the options below by typing an option number:"
                    + "\n[1] Add"
                    + "\n[2] Subtract"
                    + "\n[3] Multiply"
                    + "\n[4] View session history"
                    + "\n[5] See history of a particular operation"
                    + "\n[6] Delete an operation from session history"
                    + "\n[7] Delete entire history"
                    + "\n[8] Save the current session"
                    + "\n[9] Load the previous session"
                    + "\n[0] Quit");

            int command = input.nextInt();

            if (command == 0) {
                while (askForSave()) {
                    askForSave();
                }
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nGoodbye!");
    }

    // EFFECTS: runs different functions based on command value
    @SuppressWarnings("methodlength")
    private void processCommand(int command) {
        switch (command) {
            case 1:
                doAddition();
                isSaved = false;
                break;
            case 2:
                doSubtraction();
                isSaved = false;
                break;
            case 3:
                doMultiplication();
                isSaved = false;
                break;
            case 4:
                outputHistory(sessionHistory.getHistory());
                break;
            case 5:
                viewFilteredHistory(sessionHistory);
                break;
            case 6:
                deleteFromHistory(sessionHistory);
                isSaved = false;
                break;
            case 7:
                sessionHistory.deleteEntireHistory();
                System.out.println("Entire history deleted!");
                isSaved = false;
                break;
            case 8:
                saveHistory();
                isSaved = true;
                break;
            case 9:
                loadHistory();
                break;
            default:
                System.out.println("\nPlease enter a valid option!");
        }
    }

    // MODIFIES: this
    // EFFECTS: takes matrices input and adds them
    //          then appends the operation to session history
    private void doAddition() {
        int rows;
        int cols;

        System.out.println("\nEnter the rows and columns of your matrices:");
        rows = input.nextInt();
        cols = input.nextInt();

        if (rows <= 0 || cols <= 0) {
            System.out.println("\nPlease enter numbers more than 0!");
            return;
        }

        MatrixInput matrixInput = new MatrixInput(rows, cols);

        System.out.println("\nInput for matrix 1:");
        matrix1 = matrixInput.readMatrix();
        System.out.println("\nInput for matrix 2:");
        matrix2 = matrixInput.readMatrix();

        Addition addition = new Addition();
        addition.setMatrix1(matrix1);
        addition.setMatrix2(matrix2);
        addition.calculateAnswer();
        answer = addition.getAnswer();
        outputAnswer(answer);

        sessionHistory.addOperation(addition);
    }

    // MODIFIES: this
    // EFFECTS: takes matrices input and subtracts them
    //          and adds it to session history
    private void doSubtraction() {
        int rows;
        int cols;

        System.out.println("\nEnter the rows and columns of your matrices:");
        rows = input.nextInt();
        cols = input.nextInt();

        if (rows <= 0 || cols <= 0) {
            System.out.println("\nPlease enter numbers more than 0!");
            return;
        }

        MatrixInput matrixInput = new MatrixInput(rows, cols);

        System.out.println("\nInput for matrix 1:");
        matrix1 = matrixInput.readMatrix();
        System.out.println("\nInput for matrix 2:");
        matrix2 = matrixInput.readMatrix();

        Subtraction subtraction = new Subtraction();
        subtraction.setMatrix1(matrix1);
        subtraction.setMatrix2(matrix2);
        subtraction.calculateAnswer();
        answer = subtraction.getAnswer();
        outputAnswer(answer);

        sessionHistory.addOperation(subtraction);
    }

    // MODIFIES: this
    // EFFECTS: takes matrices input and multiplies them
    //          and adds it to session history
    @SuppressWarnings("methodlength")
    private void doMultiplication() {
        int rows1;
        int cols1;
        int rows2;
        int cols2;

        System.out.println("\nEnter the rows and columns of matrix 1:");
        rows1 = input.nextInt();
        cols1 = input.nextInt();

        System.out.println("\nEnter the rows and columns of matrix 2:");
        rows2 = input.nextInt();
        cols2 = input.nextInt();

        if (rows1 <= 0 || cols1 <= 0 || cols2 <= 0) {
            System.out.println("\nPlease enter numbers more than 0!");
            return;
        }

        if (cols1 != rows2) {
            System.out.println("\nERROR: The columns of the first matrix and the rows of the second matrix"
                    + " must be equal for multiplication!");
            return;
        }

        MatrixInput matrixInput1 = new MatrixInput(rows1, cols1);
        MatrixInput matrixInput2 = new MatrixInput(rows2, cols2);

        System.out.println("\nInput for matrix 1:");
        matrix1 = matrixInput1.readMatrix();
        System.out.println("\nInput for matrix 2:");
        matrix2 = matrixInput2.readMatrix();

        Multiplication multiplication = new Multiplication();
        multiplication.setMatrix1(matrix1);
        multiplication.setMatrix2(matrix2);
        multiplication.calculateAnswer();
        answer = multiplication.getAnswer();
        outputAnswer(answer);

        sessionHistory.addOperation(multiplication);
    }

    // EFFECTS: output the matrix answer into the console
    private void outputAnswer(List<List<Double>> answer) {
        int rows = answer.size();
        int cols = answer.get(0).size();

        System.out.println("\nAnswer:");

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(answer.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }

    // EFFECTS: output any matrix into the console
    private void outputMatrix(List<List<Double>> matrix) {
        int rows = matrix.size();
        int cols = matrix.get(0).size();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(matrix.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }

    // EFFECTS: delete an operation from the history
    private void deleteFromHistory(HistoryManager history) {
        if (history.getHistory().isEmpty()) {
            System.out.println("There is no history to delete!");
            return;
        }

        System.out.println("Please enter the serial number of the operation to delete: ");
        int choice = input.nextInt();
        int historySize = history.getHistory().size();

        if (1 <= choice && choice <= historySize) {
            history.deleteOperationIndex(choice - 1);
            System.out.println("Operation number " + choice + " deleted!");
        } else {
            System.out.println("Please enter a valid number between 1 and " + historySize + "!");
        }
    }

    // EFFECTS: output history filtered for a particular operation
    private void viewFilteredHistory(HistoryManager sessionHistory) {
        System.out.println("\nWhich operation? \n[1] Addition\n[2] Subtraction\n[3] Multiplication");
        int choice = input.nextInt();

        List<Operation> filteredHistory;

        switch (choice) {
            case 1:
                filteredHistory = sessionHistory.filterHistory("Addition");
                break;
            case 2:
                filteredHistory = sessionHistory.filterHistory("Subtraction");
                break;
            case 3:
                filteredHistory = sessionHistory.filterHistory("Multiplication");
                break;
            default:
                System.out.println("Please enter a valid number!");
                return;
        }

        if (filteredHistory.isEmpty()) {
            System.out.println("There is no history of this operation!");
            return;
        }

        outputHistory(filteredHistory);
    }

    // EFFECTS: prints the history in the console
    private void outputHistory(List<Operation> history) {
        if (history.isEmpty()) {
            System.out.println("There is no history saved yet!");
            return;
        }

        int count = 1;

        for (Operation operation : history) {
            System.out.println(count + ") " + operation.getOperation() + ":\nMatrix1");
            outputMatrix(operation.getMatrix1());
            System.out.println("\nMatrix2");
            outputMatrix(operation.getMatrix2());
            System.out.println("\nAnswer:");
            outputMatrix(operation.getAnswer());
            System.out.println("\n----------------------------------------");
            count++;
        }
    }

    // EFFECTS: saves the session history to file
    private void saveHistory() {
        try {
            jsonWriter.open();
            jsonWriter.write(sessionHistory);
            jsonWriter.close();
            System.out.println("Saved session history to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads history from file
    private void loadHistory() {
        try {
            sessionHistory = jsonReader.read();
            System.out.println("Loaded history from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: asks for saving the file if the user is quitting
    private boolean askForSave() {
        if (isSaved) {
            return false;
        }

        System.out.println("\n(Y/N) Do you want to save your progress before quitting?");
        String option = input.next().toLowerCase();

        if (Objects.equals(option, "y")) {
            saveHistory();
            return false;
        } else if (Objects.equals(option, "n")) {
            return false;
        } else {
            System.out.println("Please choose a correct option!");
        }

        return true;
    }
}
