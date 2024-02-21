package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 Class for saving history of the session.
 Saves the history as a list of operations.
 */
public class HistoryManager implements Writable {
    private List<Operation> history;

    // EFFECTS: initiate the history
    public HistoryManager() {
        history = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds the operation to the history
    public void addOperation(Operation operation) {

        history.add(operation);

        EventLog.getInstance().logEvent(new Event("Added operation to history"));
    }

    // MODIFIES: this
    // EFFECTS: deletes the operation from the history
    public void deleteOperation(Operation operation) {
        history.remove(operation);

        EventLog.getInstance().logEvent(new Event("Deleted operation from history"));
    }

    // REQUIRES: history.getHistory().size() > 0
    // MODIFIES: this
    // EFFECTS: deletes the operation from the history
    public void deleteOperationIndex(int index) {
        history.remove(index);

        EventLog.getInstance().logEvent(new Event("Deleted operation from history"));
    }

    // MODIFIES: this
    // EFFECTS: deletes the entire history
    public void deleteEntireHistory() {
        history = new ArrayList<>();

        EventLog.getInstance().logEvent(new Event("Cleared entire history"));
    }

    // MODIFIES: this
    // EFFECTS: returns filteredHistory for given operationName
    public List<Operation> filterHistory(String operationName) {
        List<Operation> filteredHistory = new ArrayList<>();
        for (Operation operation : history) {
            if (Objects.equals(operation.getOperation(), operationName)) {
                filteredHistory.add(operation);
            }
        }

        EventLog.getInstance().logEvent(new Event("Filtered history for " + operationName));

        return filteredHistory;
    }

    @Override
    public JSONObject toJson(JSONObject json) {
        json.put("history", history);
        return json;
    }

    // getters

    public List<Operation> getHistory() {
        return history;
    }
}
