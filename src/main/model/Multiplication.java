package model;

import java.util.ArrayList;
import java.util.List;

/**
 Class for matrix multiplication.
 Gets two matrices and returns the answer for multiplication.
 */
public class Multiplication implements Operation {
    List<List<Double>> matrix1;
    List<List<Double>> matrix2;
    String operationName;
    List<List<Double>> answer = new ArrayList<>();

    // EFFECTS: initiates the operation name of multiplication
    public Multiplication() {
        this.operationName = "Multiplication";
    }

    // setters

    // MODIFIES: this
    // EFFECTS: sets the value of matrix1
    public void setMatrix1(List<List<Double>> matrix1) {
        this.matrix1 = matrix1;
    }

    // MODIFIES: this
    // EFFECTS: sets the value of matrix2
    public void setMatrix2(List<List<Double>> matrix2) {
        this.matrix2 = matrix2;
    }

    // MODIFIES: this
    // EFFECTS: sets the value of answer
    public void setAnswer(List<List<Double>> answer) {
        this.answer = answer;
    }

    // MODIFIES: this
    // EFFECTS: calculates the answer of the multiplication
    public void calculateAnswer() {
        int rows1 = matrix1.size();
        int rows2 = matrix2.size();
        int cols2 = matrix2.get(0).size();

        for (int i = 0; i < rows1; i++) {
            List<Double> row = new ArrayList<>();
            for (int j = 0; j < cols2; j++) {
                double product = 0;
                for (int k = 0; k < rows2; k++) {
                    product += matrix1.get(i).get(k) * matrix2.get(k).get(j);
                }
                row.add(product);
            }
            answer.add(row);
        }

        EventLog.getInstance().logEvent(new Event("Calculated answer for " + operationName));
    }

    // getters

    public List<List<Double>> getMatrix1() {
        return matrix1;
    }

    public List<List<Double>> getMatrix2() {
        return matrix2;
    }

    public List<List<Double>> getAnswer() {
        return answer;
    }

    public String getOperation() {
        return operationName;
    }
}
