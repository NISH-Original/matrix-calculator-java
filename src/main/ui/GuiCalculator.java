package ui;

import model.*;
import model.exception.LogException;
import persistence.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 Class for main GUI-based matrix calculator.
 Handles user input and shows output.
 */
public class GuiCalculator implements Runnable {
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel sidePanel;
    private List<List<Double>> answer;
    private HistoryManager sessionHistory;
    private static final String JSON_STORE = "./data/session_histories.json";
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private final String logoPath = "./images/logo.png";

    // EFFECTS: initializes the UI
    public GuiCalculator() {
        splashScreen();
        sessionHistory = new HistoryManager();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: packs the frame and makes it visible
    @Override
    public void run() {
        initialize();
    }

    // EFFECTS: creates the splash screen for the app
    private void splashScreen() {
        JWindow window = new JWindow();
        JLabel label = new JLabel(new ImageIcon(logoPath));
        window.add(label);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        window.setVisible(false);
        window.dispose();
    }

    // MODIFIES: this
    // EFFECTS: makes the window
    @SuppressWarnings("methodlength")
    private void initialize() {
        frame = new JFrame();
        frame.pack();
        frame.setTitle("Matrix Calculator");
        frame.setSize(400, 250);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(logoPath));
        frame.setLocationRelativeTo(null);

        sidePanel = createSidePanel();
        mainPanel = createMainPanel();

        frame.add(sidePanel, BorderLayout.WEST);
        frame.add(mainPanel, BorderLayout.CENTER);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?");
                if (option == JOptionPane.YES_OPTION) {
                    try {
                        ScreenPrinter.printLog(EventLog.getInstance());
                    } catch (LogException le) {
                        System.out.println(le.getMessage());
                    }

                    System.exit(0);
                }
            }
        });
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    // EFFECTS: creates the main panel with operation inputs
    private JPanel createMainPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        mainPanel = new JPanel();
        mainPanel.setBackground(Color.GRAY);
        mainPanel.setLayout(new GridBagLayout());

        JLabel label1 = new JLabel("Matrix 1 rows and columns");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(label1, gbc);

        JSpinner rowsSpinner1 = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(rowsSpinner1, gbc);

        JSpinner colsSpinner1 = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(colsSpinner1, gbc);

        JLabel label2 = new JLabel("Matrix 2 rows and columns");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(label2, gbc);

        JSpinner rowsSpinner2 = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(rowsSpinner2, gbc);

        JSpinner colsSpinner2 = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(colsSpinner2, gbc);

        JLabel label3 = new JLabel("Operation");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(label3, gbc);

        String[] operations = { "Addition", "Subtraction", "Multiplication" };

        JComboBox<String> operationsDropDown = new JComboBox<>(operations);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(operationsDropDown, gbc);

        JButton enterButton = new JButton("Enter");
        enterButton.addActionListener(e -> {
            String op = (String) operationsDropDown.getSelectedItem();
            int rows1 = (int) rowsSpinner1.getValue();
            int cols1 = (int) colsSpinner1.getValue();
            int rows2 = (int) rowsSpinner2.getValue();
            int cols2 = (int) colsSpinner2.getValue();

            if (Objects.equals(op, "Addition") || Objects.equals(op, "Subtraction")) {
                if (rows1 != rows2 || cols1 != cols2) {
                    JOptionPane.showConfirmDialog(null,
                            "The rows and cols need to be equal for Addition or Subtraction :(",
                            "Error", JOptionPane.DEFAULT_OPTION);
                    return;
                }
            }

            if (Objects.equals(op, "Multiplication")) {
                if (cols1 != rows2) {
                    JOptionPane.showConfirmDialog(null,
                            "The rows of first and cols of second need to be equal for Multiplication :(",
                            "Error", JOptionPane.DEFAULT_OPTION);
                    return;
                }
            }

            getMatrices(
                    (int) rowsSpinner1.getValue(),
                    (int) colsSpinner1.getValue(),
                    (int) rowsSpinner2.getValue(),
                    (int) colsSpinner2.getValue(),
                    (String) operationsDropDown.getSelectedItem());
        });

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(enterButton, gbc);

        return mainPanel;
    }

    // EFFECTS: creates side panel with save, load, and history options
    private JPanel createSidePanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        sidePanel = new JPanel();
        sidePanel.setBackground(Color.DARK_GRAY);
        sidePanel.setLayout(new GridBagLayout());

        JButton saveButton = new JButton("Save");
        saveButton.setBorder(BorderFactory.createEmptyBorder(5, 11, 5, 11));
        saveButton.addActionListener(e -> saveHistory());
        gbc.gridy = 0;
        sidePanel.add(saveButton, gbc);

        JButton loadButton = new JButton("Load");
        loadButton.setBorder(BorderFactory.createEmptyBorder(5, 11, 5, 11));
        loadButton.addActionListener(e -> loadHistory());
        gbc.gridy = 1;
        sidePanel.add(loadButton, gbc);

        JButton historyButton = new JButton("History");
        historyButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        historyButton.addActionListener(e -> displayHistory());
        gbc.gridy = 2;
        sidePanel.add(historyButton, gbc);

        return sidePanel;
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    // EFFECTS: gets the matrices input in a new window
    private void getMatrices(int rows1, int cols1, int rows2, int cols2, String operation) {
        JFrame frame = new JFrame();
        frame.setTitle("Matrices Input");
        frame.setLayout(new GridLayout(1, 0));
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);

        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        panel1.setBackground(Color.GRAY);

        final JTextField[][] textFields1 = new JTextField[rows1][cols1];

        addMatrixTextFields(rows1, cols1, panel1, gbc, textFields1);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());

        final JTextField[][] textFields2 = new JTextField[rows2][cols2];

        addMatrixTextFields(rows2, cols2, panel2, gbc, textFields2);

        JButton calcButton = new JButton("Calculate");
        calcButton.setBorder(BorderFactory.createEmptyBorder(5, 4, 5, 4));

        calcButton.addActionListener(e -> {
            calculate(
                    parseMatrix(rows1, cols1, textFields1),
                    parseMatrix(rows2, cols2, textFields2),
                    operation);
            frame.dispose();
        });

        gbc.gridx = rows2 - 1;
        gbc.gridy = cols2 + 1;
        panel2.add(calcButton, gbc);

        frame.add(panel1);
        frame.add(panel2);

        frame.pack();
    }

    // EFFECTS: helper method to add text fields in matrix form in the panel
    private static void addMatrixTextFields(int rows, int cols, JPanel panel,
                                            GridBagConstraints gbc, JTextField[][] textFields) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                textFields[i][j] = new JTextField(5);
                gbc.gridx = j;
                gbc.gridy = i;
                panel.add(textFields[i][j], gbc);
            }
        }
    }

    // EFFECTS: reads the matrix from the text fields
    private List<List<Double>> parseMatrix(int rows, int cols, JTextField[][] textFields) {
        List<List<Double>> matrix = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            List<Double> row = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                String value = textFields[i][j].getText();
                try {
                    row.add(Double.parseDouble(value));
                } catch (NumberFormatException ex) {
                    row.add(0.0);
                }
            }
            matrix.add(row);
        }

        return matrix;
    }

    @SuppressWarnings({"methodLength", "checkstyle:SuppressWarnings"})
    // MODIFIES: this
    // EFFECTS: calculates the answer of the matrix, and stores in history
    private void calculate(List<List<Double>> matrix1, List<List<Double>> matrix2, String operation) {
        switch (operation) {
            case "Addition":
                Addition addition = new Addition();
                addition.setMatrix1(matrix1);
                addition.setMatrix2(matrix2);
                addition.calculateAnswer();
                this.answer = addition.getAnswer();
                this.sessionHistory.addOperation(addition);
                break;
            case "Subtraction":
                Subtraction subtraction = new Subtraction();
                subtraction.setMatrix1(matrix1);
                subtraction.setMatrix2(matrix2);
                subtraction.calculateAnswer();
                this.answer = subtraction.getAnswer();
                this.sessionHistory.addOperation(subtraction);
                break;
            case "Multiplication":
                Multiplication multiplication = new Multiplication();
                multiplication.setMatrix1(matrix1);
                multiplication.setMatrix2(matrix2);
                multiplication.calculateAnswer();
                this.answer = multiplication.getAnswer();
                this.sessionHistory.addOperation(multiplication);
                break;
            default:
                break;
        }

        displayAnswer(answer);
    }

    @SuppressWarnings({"methodLength", "checkstyle:SuppressWarnings"})
    // EFFECTS: displays the answer in a new window
    public void displayAnswer(List<List<Double>> answer) {
        JFrame frame = new JFrame();
        frame.setTitle("Answer");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        int rows = answer.size();
        int cols = answer.get(0).size();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JPanel answerPanel = new JPanel();
        answerPanel.setLayout(new GridBagLayout());

        JTextField[][] textFields = new JTextField[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Double entry = answer.get(i).get(j);
                textFields[i][j] = new JTextField(Double.toString(entry));
                textFields[i][j].setEditable(false);

                Font largerFont = textFields[i][j].getFont().deriveFont(Font.PLAIN, 15);
                textFields[i][j].setFont(largerFont);
                Dimension largerSize = new Dimension(textFields[i][j].getPreferredSize().width + 20,
                        textFields[i][j].getPreferredSize().height + 10);
                textFields[i][j].setPreferredSize(largerSize);

                gbc.gridx = j;
                gbc.gridy = i;
                answerPanel.add(textFields[i][j], gbc);
            }
        }

        JButton okButton = new JButton("Ok");
        okButton.addActionListener(e -> frame.dispose());

        gbc.gridx = rows - 1;
        gbc.gridy = cols + 1;
        answerPanel.add(okButton, gbc);

        panel.add(answerPanel, BorderLayout.CENTER);
        frame.add(panel);
        frame.pack();
    }

    // EFFECTS: displays the history window
    public void displayHistory() {
        JFrame frame = new JFrame();
        frame.setTitle("History");
        frame.setSize(300, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        addListOfOperationsToPanel(mainPanel, sessionHistory.getHistory());

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel settingsPanel = getSettingsPanel(mainPanel);

        frame.add(settingsPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    // EFFECTS: helper method to add operations to the main panel
    private void addListOfOperationsToPanel(JPanel mainPanel, List<Operation> operationsList) {
        for (int i = 0; i < operationsList.size(); i++) {
            addOperationPanel(mainPanel, operationsList.get(i));
        }
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    // EFFECTS: makes the settings panel in the history window
    private JPanel getSettingsPanel(JPanel mainPanel) {
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton clearButton = new JButton("Clear History");
        clearButton.addActionListener(e -> clearHistory(mainPanel));
        settingsPanel.add(clearButton);

        JButton filterButton = new JButton("Filter");
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.setLayout(new GridLayout(0, 1));

        JButton allButton = new JButton("All");
        allButton.addActionListener(e -> showFilteredHistory(mainPanel, sessionHistory.getHistory()));

        JButton addButton = new JButton("Addition");
        addButton.addActionListener(e ->
                showFilteredHistory(mainPanel, sessionHistory.filterHistory("Addition")));

        JButton subButton = new JButton("Subtraction");
        subButton.addActionListener(e ->
                showFilteredHistory(mainPanel, sessionHistory.filterHistory("Subtraction")));

        JButton mulButton = new JButton("Multiplication");
        mulButton.addActionListener(e ->
                showFilteredHistory(mainPanel, sessionHistory.filterHistory("Multiplication")));

        popupMenu.add(allButton);
        popupMenu.add(addButton);
        popupMenu.add(subButton);
        popupMenu.add(mulButton);

        filterButton.addActionListener(e -> popupMenu.show(filterButton, 0, filterButton.getHeight()));

        settingsPanel.add(filterButton);

        return settingsPanel;
    }

    // EFFECTS: helper method to add an operation to the history window
    public void addOperationPanel(JPanel mainPanel, Operation operation) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.setLayout(new BorderLayout());

        JPanel titlePanel =  new JPanel();
        titlePanel.setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel(operation.getOperation());
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel, BorderLayout.WEST);

        JButton deleteButton = new JButton("Delete");
        titlePanel.add(deleteButton, BorderLayout.EAST);
        deleteButton.addActionListener(e -> deleteOperationPanel(mainPanel, panel, operation));
        titlePanel.setBackground(Color.GRAY);

        JPanel contentPanel = new JPanel();
        JTextArea textArea = new JTextArea(createText(operation));
        textArea.setPreferredSize(new Dimension(200, 200));
        textArea.setEditable(false);
        contentPanel.add(textArea);

        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);

        mainPanel.add(panel);
    }

    // EFFECTS: shows the filtered history
    private void showFilteredHistory(JPanel mainPanel, List<Operation> history) {
        for (Component comp : mainPanel.getComponents()) {
            mainPanel.remove(comp);
        }

        addListOfOperationsToPanel(mainPanel, history);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // EFFECTS: returns the operation in text form
    private String createText(Operation operation) {

        return "Matrix 1: \n"
                + matrixToString(operation.getMatrix1())
                + "Matrix 2: \n"
                + matrixToString(operation.getMatrix2())
                + "Answer: \n"
                + matrixToString(operation.getAnswer());
    }

    // EFFECTS: returns the matrix in text form
    private String matrixToString(List<List<Double>> matrix) {
        int rows = matrix.size();
        int cols = matrix.get(0).size();
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                output.append(matrix.get(i).get(j)).append(" ");
            }
            output.append("\n");
        }

        return output.toString();
    }

    // EFFECTS: clears the entire history
    private void clearHistory(JPanel mainPanel) {
        sessionHistory.deleteEntireHistory();

        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof JPanel) {
                mainPanel.remove(comp);
            }
        }

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // EFFECTS: deletes an operation panel from the history
    private void deleteOperationPanel(JPanel mainPanel, JPanel panel, Operation operation) {
        int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this operation?");
        if (option == JOptionPane.YES_OPTION) {
            sessionHistory.deleteOperation(operation);
            mainPanel.remove(panel);
            mainPanel.revalidate();
            mainPanel.repaint();
        }
    }

    // EFFECTS: saves history to file
    private void saveHistory() {
        try {
            jsonWriter.open();
            jsonWriter.write(sessionHistory);
            jsonWriter.close();
            JOptionPane.showConfirmDialog(null,
                    "Session history saved successfully!", "Confirmation", JOptionPane.DEFAULT_OPTION);
        } catch (FileNotFoundException e) {
            JOptionPane.showConfirmDialog(null,
                    "Session history could not be saved :(", "Error", JOptionPane.DEFAULT_OPTION);
        }
    }

    private void loadHistory() {
        try {
            sessionHistory = jsonReader.read();
            JOptionPane.showConfirmDialog(null,
                    "Session history loaded successfully!", "Confirmation", JOptionPane.DEFAULT_OPTION);
        } catch (IOException e) {
            JOptionPane.showConfirmDialog(null,
                    "Unable to read from file :(", "Error", JOptionPane.DEFAULT_OPTION);
        }
    }
}
