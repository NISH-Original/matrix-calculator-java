package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
Class for handling the input of matrices
*/
public class MatrixInput {
    private final int rows;
    private final int cols;
    private final Scanner input = new Scanner(System.in);

    // EFFECTS: initiates the rows and columns of the matrix input
    public MatrixInput(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    // EFFECTS: takes the input from the user,
    //          and reads and returns it as a matrix
    public List<List<Double>> readMatrix() {
        List<List<Double>> matrix = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            System.out.println("\nEnter entries in row " + (i + 1) + " (" + cols + " entries):");
            List<Double> row = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                row.add(input.nextDouble());
            }
            matrix.add(row);
        }
        return matrix;
    }
}
