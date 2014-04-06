package view;

import javax.swing.JComponent;

public interface IRichEditor {
    public JComponent getRoot();
    public void setText(String text);
    public String getText();
}
