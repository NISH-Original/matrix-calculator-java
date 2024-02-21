package persistence;

import model.*;
import org.json.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 Represents a reader that reads history from JSON data stored in file
 */
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads history from file and returns it;
    //          throws IOException if an error occurs reading data from file
    public HistoryManager read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseHistory(new HistoryManager(), jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses history from JSON object and returns it
    private HistoryManager parseHistory(HistoryManager hm, JSONObject jsonObject) {
        JSONArray operationsArray = jsonObject.getJSONArray("history");
        Operation operation;

        for (int i = 0; i < operationsArray.length(); i++) {
            JSONObject operationObject = operationsArray.getJSONObject(i);
            JSONArray matrix1Array = operationObject.getJSONArray("matrix1");
            JSONArray matrix2Array = operationObject.getJSONArray("matrix2");
            String operationName = operationObject.getString("operation");
            JSONArray answerArray = operationObject.getJSONArray("answer");

            List<List<Double>> matrix1 = parseMatrix(matrix1Array);
            List<List<Double>> matrix2 = parseMatrix(matrix2Array);
            List<List<Double>> answer = parseMatrix(answerArray);

            if (Objects.equals(operationName, "Addition")) {
                operation = new Addition();
            } else if (Objects.equals(operationName, "Subtraction")) {
                operation = new Subtraction();
            } else {
                operation = new Multiplication();
            }

            makeOperation(operation, matrix1, matrix2, answer);

            hm.addOperation(operation);
        }
        return hm;
    }

    // EFFECTS: makes an operation by setting all the matrices
    private void makeOperation(Operation op, List<List<Double>> m1, List<List<Double>> m2, List<List<Double>> ans) {
        op.setMatrix1(m1);
        op.setMatrix2(m2);
        op.setAnswer(ans);
    }

    // EFFECTS: parses a matrix from a 2D Array in the JSON file
    private List<List<Double>> parseMatrix(JSONArray arr) {
        List<List<Double>> matrix = new ArrayList<>();

        for (int i = 0; i < arr.length(); i++) {
            List<Double> row = new ArrayList<>();
            for (int j = 0; j < arr.getJSONArray(0).length(); j++) {
                row.add(arr.getJSONArray(i).getDouble(j));
            }
            matrix.add(row);
        }
        return matrix;
    }
}
