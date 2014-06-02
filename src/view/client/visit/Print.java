package view.client.visit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import java.awt.print.PrinterException;
import model.Visit;
import model.Client;

public class Print {
    public Print(JFrame parent, Visit visit) {
        JFrame frame = new JFrame();
        JEditorPane pane = new JEditorPane("text/html", visit.getDescription());
        pane.setEditable(false);
        frame.getContentPane().add(new JScrollPane(pane));
        frame.pack();
        frame.setLocationRelativeTo(parent);
        frame.setVisible(true);
        try {
            pane.print();
        } catch (PrinterException e) {

        }
    }
}
