package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultiplicationTest {
    private Multiplication multiplication;
    List<List<Double>> matrix1;
    List<List<Double>> matrix2;

    @BeforeEach
    void runBefore() {
        multiplication = new Multiplication();

        matrix1 = new ArrayList<>();
        matrix1.add(Arrays.asList(1.0, 2.0, 3.0));
        matrix1.add(Arrays.asList(4.0, 5.0, 6.0));

        matrix2 = new ArrayList<>();
        matrix2.add(Arrays.asList(1.0, 1.0));
        matrix2.add(Arrays.asList(2.0, 2.0));
        matrix2.add(Arrays.asList(3.0, 3.0));

        multiplication.setMatrix1(matrix1);
        multiplication.setMatrix2(matrix2);
    }

    @Test
    void testConstructor() {
        assertEquals("Multiplication", multiplication.getOperation());
    }

    @Test
    void testSetters() {
        List<List<Double>> newMatrix1 = new ArrayList<>();
        newMatrix1.add(Arrays.asList(1.5, 2.0));
        newMatrix1.add(Arrays.asList(4.0, 45.21));

        List<List<Double>> newMatrix2 = new ArrayList<>();
        newMatrix1.add(Arrays.asList(1.5, 2.0, 3.5));
        newMatrix1.add(Arrays.asList(4.0, 45.21, 6.29));

        multiplication.setMatrix1(newMatrix1);
        multiplication.setMatrix2(newMatrix2);

        assertEquals(newMatrix1, multiplication.getMatrix1());
        assertEquals(newMatrix2, multiplication.getMatrix2());
    }

    @Test
    void testMultiplication() {
        multiplication.calculateAnswer();

        List<List<Double>> answer = new ArrayList<>();
        answer.add(Arrays.asList(14.0, 14.0));
        answer.add(Arrays.asList(32.0, 32.0));

        assertEquals(answer, multiplication.getAnswer());
    }

    @Test
    void testGetters() {
        assertEquals(matrix1, multiplication.getMatrix1());
        assertEquals(matrix2, multiplication.getMatrix2());
    }
}
