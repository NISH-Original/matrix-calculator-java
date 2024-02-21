package ui;

import model.Event;
import model.EventLog;
import model.exception.LogException;

public class ScreenPrinter {

    public static void printLog(EventLog el) throws LogException {
        for (Event next : el) {
            System.out.println(next.toString() + "\n\n");
        }
    }
}
