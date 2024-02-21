package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HistoryTest {
    HistoryManager testHistory;
    Operation testAddition;
    Operation testSubtraction;
    Operation testMultiplication;

    @BeforeEach
    void runBefore() {
        testHistory = new HistoryManager();

        testAddition = new Addition();
        testSubtraction = new Subtraction();
        testMultiplication = new Multiplication();
    }

    @Test
    // Adding 0 operations to history
    void testGetEmptyHistory() {
        assertEquals(0, testHistory.getHistory().size());
    }

    @Test
    // Adding one operation to history
    void testAddOneToHistory() {
        testHistory.addOperation(testAddition);
        assertEquals(1, testHistory.getHistory().size());
        assertEquals(testAddition, testHistory.getHistory().get(0));
    }

    @Test
    // Adding many operations to history
    void testAddMultipleToHistory() {
        testHistory.addOperation(testAddition);
        testHistory.addOperation(testSubtraction);
        assertEquals(2, testHistory.getHistory().size());

        testHistory.addOperation(testMultiplication);
        testHistory.addOperation(testAddition);
        assertEquals(4, testHistory.getHistory().size());

        assertEquals(testAddition, testHistory.getHistory().get(0));
        assertEquals(testSubtraction, testHistory.getHistory().get(1));
        assertEquals(testMultiplication, testHistory.getHistory().get(2));
        assertEquals(testAddition, testHistory.getHistory().get(3));
    }

    @Test
    void testFilterHistoryAddition() {
        testHistory.addOperation(testAddition);
        testHistory.addOperation(testSubtraction);
        testHistory.addOperation(testMultiplication);

        assertEquals(1, testHistory.filterHistory("Addition").size());
        assertEquals(testAddition, testHistory.filterHistory("Addition").get(0));
    }

    @Test
    void testFilterHistorySubtraction() {
        testHistory.addOperation(testAddition);
        testHistory.addOperation(testSubtraction);
        testHistory.addOperation(testMultiplication);

        assertEquals(1, testHistory.filterHistory("Subtraction").size());
        assertEquals(testSubtraction, testHistory.filterHistory("Subtraction").get(0));
    }

    @Test
    void testFilterHistoryMultiplication() {
        testHistory.addOperation(testAddition);
        testHistory.addOperation(testSubtraction);
        testHistory.addOperation(testMultiplication);

        assertEquals(1, testHistory.filterHistory("Multiplication").size());
        assertEquals(testMultiplication, testHistory.filterHistory("Multiplication").get(0));
    }

    @Test
    void testFilterHistoryMany() {
        Addition testAddition2 = new Addition();

        testHistory.addOperation(testAddition);
        testHistory.addOperation(testSubtraction);
        testHistory.addOperation(testMultiplication);
        testHistory.addOperation(testAddition2);

        assertEquals(2, testHistory.filterHistory("Addition").size());
        assertEquals(testAddition, testHistory.filterHistory("Addition").get(0));
        assertEquals(testAddition2, testHistory.filterHistory("Addition").get(1));
    }

    @Test
    // test to filter when all elements in the history are the same operation
    void testFilterHistoryAllSame() {
        Subtraction testSubtraction2 = new Subtraction();
        Subtraction testSubtraction3 = new Subtraction();

        testHistory.addOperation(testSubtraction);
        testHistory.addOperation(testSubtraction2);
        testHistory.addOperation(testSubtraction3);

        assertEquals(3, testHistory.filterHistory("Subtraction").size());
        assertEquals(testSubtraction, testHistory.filterHistory("Subtraction").get(0));
        assertEquals(testSubtraction2, testHistory.filterHistory("Subtraction").get(1));
        assertEquals(testSubtraction3, testHistory.filterHistory("Subtraction").get(2));
    }

    @Test
    // test to filter when no elements are equal to the user input
    void testFilterHistoryNoneSame() {
        Subtraction testSubtraction2 = new Subtraction();
        Subtraction testSubtraction3 = new Subtraction();

        testHistory.addOperation(testSubtraction);
        testHistory.addOperation(testSubtraction2);
        testHistory.addOperation(testSubtraction3);
        testHistory.addOperation(testMultiplication);

        assertEquals(0, testHistory.filterHistory("Addition").size());
    }

    @Test
    // testing deleting one operation from a history with just a single operation
    void testDeleteOneOperationWithOneLongHistory() {
        testHistory.addOperation(testSubtraction);
        testHistory.deleteOperationIndex(0);

        assertEquals(0, testHistory.getHistory().size());
    }

    @Test
    void testDeleteOneOperationIndex() {
        testHistory.addOperation(testAddition);
        testHistory.addOperation(testSubtraction);

        testHistory.addOperation(testMultiplication);
        testHistory.addOperation(testAddition);

        testHistory.deleteOperationIndex(2);
        assertEquals(3, testHistory.getHistory().size());
    }

    @Test
    void testDeleteMultipleOperationsIndex() {
        testHistory.addOperation(testAddition);
        testHistory.addOperation(testSubtraction);

        testHistory.addOperation(testMultiplication);
        testHistory.addOperation(testAddition);

        testHistory.deleteOperationIndex(2);
        testHistory.deleteOperationIndex(1);
        testHistory.deleteOperationIndex(0);
        assertEquals(1, testHistory.getHistory().size());
    }

    @Test
    void testDeleteOneOperation() {
        testHistory.addOperation(testAddition);
        testHistory.addOperation(testSubtraction);

        testHistory.addOperation(testMultiplication);
        testHistory.addOperation(testAddition);

        testHistory.deleteOperation(testMultiplication);
        assertEquals(3, testHistory.getHistory().size());
    }

    @Test
    void testDeleteMultipleOperations() {
        testHistory.addOperation(testAddition);
        testHistory.addOperation(testSubtraction);

        testHistory.addOperation(testMultiplication);
        testHistory.addOperation(testAddition);

        testHistory.deleteOperation(testMultiplication);
        testHistory.deleteOperation(testSubtraction);
        testHistory.deleteOperation(testAddition);
        assertEquals(1, testHistory.getHistory().size());
    }

    @Test
    void testDeleteEntireHistory() {
        testHistory.addOperation(testAddition);
        testHistory.addOperation(testSubtraction);
        testHistory.addOperation(testMultiplication);
        testHistory.deleteEntireHistory();

        assertEquals(0, testHistory.getHistory().size());
    }

}
