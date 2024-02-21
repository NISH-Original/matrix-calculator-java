package persistence;

import model.Operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    public void checkOperation(String operationName, Operation operation) {
        assertEquals(operationName, operation.getOperation());
    }
}
