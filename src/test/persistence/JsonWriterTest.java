package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyHistory() {
        try {
            HistoryManager hm = new HistoryManager();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyHistory.json");
            writer.open();
            writer.write(hm);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyHistory.json");
            hm = reader.read();
            assertEquals(0, hm.getHistory().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            HistoryManager hm = new HistoryManager();
            makeTestHistory(hm);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralHistory.json");
            writer.open();
            writer.write(hm);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralHistory.json");
            hm = reader.read();
            List<Operation> history = hm.getHistory();
            assertEquals(3, history.size());
            checkOperation("Addition", history.get(0));
            checkOperation("Subtraction", history.get(1));
            checkOperation("Multiplication", history.get(2));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    void makeTestHistory(HistoryManager hm) {
        Operation add = new Addition();
        Operation sub = new Subtraction();
        Operation mul = new Multiplication();

        List<List<Double>> matrix1 = new ArrayList<>();
        matrix1.add(Arrays.asList(1.5, 2.0, 2.34));
        matrix1.add(Arrays.asList(4.0, 45.21, 5.0));

        List<List<Double>> matrix2 = new ArrayList<>();
        matrix2.add(Arrays.asList(1.5, 2.0, 3.5));
        matrix2.add(Arrays.asList(4.0, 45.21, 6.29));

        List<List<Double>> matrix3 = new ArrayList<>();
        matrix3.add(Arrays.asList(1.5, 2.0));
        matrix3.add(Arrays.asList(4.0, 45.21));
        matrix3.add(Arrays.asList(3.24, 1.0));

        List<List<Double>> answerAdd = new ArrayList<>();
        answerAdd.add(Arrays.asList(3.0, 4.0, 5.84));
        answerAdd.add(Arrays.asList(8.0, 90.42, 11.29));

        List<List<Double>> answerSub = new ArrayList<>();
        answerSub.add(Arrays.asList(0.0, 0.0, -1.16));
        answerSub.add(Arrays.asList(0.0, 0.0, -1.29));

        List<List<Double>> answerMul = new ArrayList<>();
        answerMul.add(Arrays.asList(17.8316, 95.76));
        answerMul.add(Arrays.asList(203.04, 2056.9441));

        add.setMatrix1(matrix1);
        add.setMatrix2(matrix2);
        add.setAnswer(answerAdd);

        sub.setMatrix1(matrix1);
        sub.setMatrix2(matrix2);
        sub.setAnswer(answerSub);

        mul.setMatrix1(matrix1);
        mul.setMatrix2(matrix3);
        mul.setAnswer(answerMul);

        hm.addOperation(add);
        hm.addOperation(sub);
        hm.addOperation(mul);
    }
}
