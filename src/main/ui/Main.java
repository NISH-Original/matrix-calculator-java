package ui;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // try {
        //     new MatrixCalculator();
        // } catch (FileNotFoundException e) {
        //     System.out.println("Unable to run application: file not found!");
        // }

        SwingUtilities.invokeLater(new GuiCalculator());
    }
}
