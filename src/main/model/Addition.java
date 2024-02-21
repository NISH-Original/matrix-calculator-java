package model;

import java.util.ArrayList;
import java.util.List;

/**
 Class for matrix addition.
 Gets two matrices and returns the answer for addition.
 */
public class Addition implements Operation {
    List<List<Double>> matrix1;
    List<List<Double>> matrix2;
    String operationName;
    List<List<Double>> answer = new ArrayList<>();

    // EFFECTS: initiates the operation name of addition
    public Addition() {
        this.operationName = "Addition";
    }

    // setters

    // MODIFIES: this
    // EFFECTS: sets the value of matrix1
    @Override
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
    // EFFECTS: calculates the answer of the addition
    public void calculateAnswer() {
        int rows = matrix1.size();
        int cols = matrix1.get(0).size();


        for (int i = 0; i < rows; i++) {
            List<Double> row = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                double sum = matrix1.get(i).get(j) + matrix2.get(i).get(j);
                row.add(sum);
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
