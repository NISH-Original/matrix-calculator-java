package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdditionTest {
    private Addition addition;
    List<List<Double>> matrix1;
    List<List<Double>> matrix2;

    @BeforeEach
    void runBefore() {
        addition = new Addition();

        matrix1 = new ArrayList<>();
        matrix1.add(Arrays.asList(1.0, 2.0, 3.0));
        matrix1.add(Arrays.asList(4.0, 5.0, 6.0));
        matrix1.add(Arrays.asList(7.0, 8.0, 9.0));

        matrix2 = new ArrayList<>();
        matrix2.add(Arrays.asList(1.0, 1.0, 1.0));
        matrix2.add(Arrays.asList(2.0, 2.0, 2.0));
        matrix2.add(Arrays.asList(3.0, 3.0, 3.0));

        addition.setMatrix1(matrix1);
        addition.setMatrix2(matrix2);
    }

    @Test
    void testConstructor() {
        assertEquals("Addition", addition.getOperation());
    }

    @Test
    void testSetters() {
        List<List<Double>> newMatrix1 = new ArrayList<>();
        newMatrix1.add(Arrays.asList(1.5, 2.0));
        newMatrix1.add(Arrays.asList(4.0, 45.21));

        List<List<Double>> newMatrix2 = new ArrayList<>();
        newMatrix1.add(Arrays.asList(1.5, 2.0, 3.5));
        newMatrix1.add(Arrays.asList(4.0, 45.21, 6.29));

        addition.setMatrix1(newMatrix1);
        addition.setMatrix2(newMatrix2);

        assertEquals(newMatrix1, addition.getMatrix1());
        assertEquals(newMatrix2, addition.getMatrix2());
    }

    @Test
    void testAddition() {
        addition.calculateAnswer();

        List<List<Double>> answer = new ArrayList<>();
        answer.add(Arrays.asList(2.0, 3.0, 4.0));
        answer.add(Arrays.asList(6.0, 7.0, 8.0));
        answer.add(Arrays.asList(10.0, 11.0, 12.0));

        assertEquals(answer, addition.getAnswer());
    }

    @Test
    void testAdditionZeroes() {
        matrix1 = new ArrayList<>();
        matrix1.add(Arrays.asList(0.0, 0.0, 0.0));
        matrix1.add(Arrays.asList(0.0, 0.0, 0.0));

        matrix2 = new ArrayList<>();
        matrix2.add(Arrays.asList(0.0, 0.0, 0.0));
        matrix2.add(Arrays.asList(0.0, 0.0, 0.0));

        addition.setMatrix1(matrix1);
        addition.setMatrix2(matrix2);

        addition.calculateAnswer();

        List<List<Double>> answer = new ArrayList<>();
        answer.add(Arrays.asList(0.0, 0.0, 0.0));
        answer.add(Arrays.asList(0.0, 0.0, 0.0));

        assertEquals(answer, addition.getAnswer());
    }

    @Test
    void testAdditionNegative() {
        matrix1 = new ArrayList<>();
        matrix1.add(Arrays.asList(0.0, 0.0));
        matrix1.add(Arrays.asList(0.0, 0.0));
        matrix1.add(Arrays.asList(0.0, 0.0));

        matrix2 = new ArrayList<>();
        matrix2.add(Arrays.asList(-1.0, -2.0));
        matrix2.add(Arrays.asList(-3.0, -4.0));
        matrix2.add(Arrays.asList(-5.0, -6.0));

        addition.setMatrix1(matrix1);
        addition.setMatrix2(matrix2);

        addition.calculateAnswer();

        List<List<Double>> answer = new ArrayList<>();
        answer.add(Arrays.asList(-1.0, -2.0));
        answer.add(Arrays.asList(-3.0, -4.0));
        answer.add(Arrays.asList(-5.0, -6.0));

        assertEquals(answer, addition.getAnswer());
    }

    @Test
    void testAdditionDecimal() {
        matrix1 = new ArrayList<>();
        matrix1.add(Arrays.asList(1.4, 2.5));
        matrix1.add(Arrays.asList(3.6, 8.7));

        matrix2 = new ArrayList<>();
        matrix2.add(Arrays.asList(-3.4, 3.5));
        matrix2.add(Arrays.asList(7.9, 4.8));

        addition.setMatrix1(matrix1);
        addition.setMatrix2(matrix2);

        addition.calculateAnswer();

        List<List<Double>> answer = new ArrayList<>();
        answer.add(Arrays.asList(-2.0, 6.0));
        answer.add(Arrays.asList(11.5, 13.5));

        assertEquals(answer, addition.getAnswer());
    }

    @Test
    void testGetters() {
        assertEquals(matrix1, addition.getMatrix1());
        assertEquals(matrix2, addition.getMatrix2());
    }
}