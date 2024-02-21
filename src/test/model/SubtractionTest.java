package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubtractionTest {
    private Subtraction subtraction;
    List<List<Double>> matrix1;
    List<List<Double>> matrix2;

    @BeforeEach
    void runBefore() {
        subtraction = new Subtraction();

        matrix1 = new ArrayList<>();
        matrix1.add(Arrays.asList(1.0, 2.0, 3.0));
        matrix1.add(Arrays.asList(4.0, 5.0, 6.0));
        matrix1.add(Arrays.asList(7.0, 8.0, 9.0));

        matrix2 = new ArrayList<>();
        matrix2.add(Arrays.asList(1.0, 1.0, 1.0));
        matrix2.add(Arrays.asList(2.0, 2.0, 2.0));
        matrix2.add(Arrays.asList(3.0, 3.0, 3.0));

        subtraction.setMatrix1(matrix1);
        subtraction.setMatrix2(matrix2);
    }

    @Test
    void testConstructor() {
        assertEquals("Subtraction", subtraction.getOperation());
    }

    @Test
    void testSetters() {
        List<List<Double>> newMatrix1 = new ArrayList<>();
        newMatrix1.add(Arrays.asList(1.5, 2.0));
        newMatrix1.add(Arrays.asList(4.0, 45.21));

        List<List<Double>> newMatrix2 = new ArrayList<>();
        newMatrix1.add(Arrays.asList(1.5, 2.0, 3.5));
        newMatrix1.add(Arrays.asList(4.0, 45.21, 6.29));

        subtraction.setMatrix1(newMatrix1);
        subtraction.setMatrix2(newMatrix2);

        assertEquals(newMatrix1, subtraction.getMatrix1());
        assertEquals(newMatrix2, subtraction.getMatrix2());
    }

    @Test
    void testSubtraction() {
        subtraction.calculateAnswer();

        List<List<Double>> answer = new ArrayList<>();
        answer.add(Arrays.asList(0.0, 1.0, 2.0));
        answer.add(Arrays.asList(2.0, 3.0, 4.0));
        answer.add(Arrays.asList(4.0, 5.0, 6.0));

        assertEquals(answer, subtraction.getAnswer());
    }

    @Test
    void testSubtractionZeroes() {
        matrix1 = new ArrayList<>();
        matrix1.add(Arrays.asList(0.0, 0.0, 0.0));
        matrix1.add(Arrays.asList(0.0, 0.0, 0.0));

        matrix2 = new ArrayList<>();
        matrix2.add(Arrays.asList(0.0, 0.0, 0.0));
        matrix2.add(Arrays.asList(0.0, 0.0, 0.0));

        subtraction.setMatrix1(matrix1);
        subtraction.setMatrix2(matrix2);

        subtraction.calculateAnswer();

        List<List<Double>> answer = new ArrayList<>();
        answer.add(Arrays.asList(0.0, 0.0, 0.0));
        answer.add(Arrays.asList(0.0, 0.0, 0.0));

        assertEquals(answer, subtraction.getAnswer());
    }

    @Test
    void testSubtractionNegative() {
        matrix1 = new ArrayList<>();
        matrix1.add(Arrays.asList(0.0, 0.0));
        matrix1.add(Arrays.asList(0.0, 0.0));
        matrix1.add(Arrays.asList(0.0, 0.0));

        matrix2 = new ArrayList<>();
        matrix2.add(Arrays.asList(-1.0, -2.0));
        matrix2.add(Arrays.asList(-3.0, -4.0));
        matrix2.add(Arrays.asList(-5.0, -6.0));

        subtraction.setMatrix1(matrix1);
        subtraction.setMatrix2(matrix2);

        subtraction.calculateAnswer();

        List<List<Double>> answer = new ArrayList<>();
        answer.add(Arrays.asList(1.0, 2.0));
        answer.add(Arrays.asList(3.0, 4.0));
        answer.add(Arrays.asList(5.0, 6.0));

        assertEquals(answer, subtraction.getAnswer());
    }

    @Test
    void testSubtractionDecimal() {
        matrix1 = new ArrayList<>();
        matrix1.add(Arrays.asList(1.4, 2.5));
        matrix1.add(Arrays.asList(3.6, 8.7));

        matrix2 = new ArrayList<>();
        matrix2.add(Arrays.asList(-3.4, 3.5));
        matrix2.add(Arrays.asList(7.9, 4.7));

        subtraction.setMatrix1(matrix1);
        subtraction.setMatrix2(matrix2);

        subtraction.calculateAnswer();

        //List<List<Double>> answer = new ArrayList<>();
        //answer.add(Arrays.asList(4.8, -1.0));
        //answer.add(Arrays.asList(-4.3, 4.0));

        //assertEquals(answer, subtraction.getAnswer());
    }

    @Test
    void testGetters() {
        assertEquals(matrix1, subtraction.getMatrix1());
        assertEquals(matrix2, subtraction.getMatrix2());
    }
}
