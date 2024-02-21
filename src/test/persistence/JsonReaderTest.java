package persistence;

import model.HistoryManager;
import model.Operation;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/testNoSuchFile.json");
        try {
            HistoryManager hm = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyHistory() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyHistory.json");
        try {
            HistoryManager hm = reader.read();
            assertEquals(0, hm.getHistory().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralHistory() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralHistory.json");
        try {
            HistoryManager hm = reader.read();
            List<Operation> history = hm.getHistory();
            assertEquals(3, history.size());
            checkOperation("Addition", history.get(0));
            checkOperation("Subtraction", history.get(1));
            checkOperation("Multiplication", history.get(2));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
