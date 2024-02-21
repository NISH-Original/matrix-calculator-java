package persistence;

import model.HistoryManager;

import java.io.*;
import org.json.*;

/**
 Represents a writer that writes JSON representation of history to file
 */
public class JsonWriter {
    private final String destination;
    private PrintWriter writer;
    JSONObject json = new JSONObject();
    private static final int TAB = 2;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of HistoryManager to file
    public void write(HistoryManager hm) {
        json = hm.toJson(json);
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
