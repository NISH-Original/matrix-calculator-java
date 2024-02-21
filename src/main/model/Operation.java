package model;

import java.util.List;

/**
 Abstract class for all matrix operations.
 Implemented by Addition, Subtraction, and Multiplication.
 */
public interface Operation {
    void calculateAnswer();

    List<List<Double>> getMatrix1();

    List<List<Double>> getMatrix2();

    List<List<Double>> getAnswer();

    String getOperation();

    void setMatrix1(List<List<Double>> matrix1);

    void setMatrix2(List<List<Double>> matrix2);

    void setAnswer(List<List<Double>> answer);
}
