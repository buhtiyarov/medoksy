package view;

import javax.swing.JTextArea;
import javax.swing.JComponent;
import javax.swing.JScrollPane;

public class RichEditor implements IRichEditor {
    private JTextArea area;
    private JComponent root;

    public RichEditor() {
        area = new JTextArea(5, 20);
        root = new JScrollPane(area);
    }

    public JComponent getRoot() {
        return root;
    }

    public void setText(String text) {
        area.setText(text);
    }

    public String getText() {
        return area.getText();
    }
}
