package util;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class Printer {
    public static void printHTML(String html) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.PRINT)) {
                try {
                    File temp = File.createTempFile("temp", ".html");

                    BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
                    bw.write(html, 0, html.length());
                    bw.close();

                    desktop.print(temp);
                } catch (IOException e) {
                    System.err.println("Print error: " + e);
                }
            } else {
                System.err.println("Print isn't supported");
            }
        }
    }
}
