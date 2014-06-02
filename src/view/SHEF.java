package view;

import javax.swing.JComponent;
import net.atlanticbb.tantlinger.shef.HTMLEditorPane;

public class SHEF implements IRichEditor {
    private HTMLEditorPane editor;

    public SHEF() {
        editor = new HTMLEditorPane();
    }

    public JComponent getRoot() {
        return editor;
    }

    public void setText(String text) {
        editor.setText(text);
    }

    public String getText() {
        return editor.getText();
    }
}
