package view;

import javax.swing.JButton;
import javax.swing.ImageIcon;

public class ButtonFactory {
    private static ImageIcon createImage(String name) {
        return new ImageIcon(ButtonFactory.class.getResource("/icons/" + name + ".png"));
    }

    private static JButton createButton(String name) {
        JButton button = new JButton(createImage(name));
        button.setActionCommand(name);
        return button;
    }

    public static JButton createAddButton() {
        return createButton("add");
    }

    public static JButton createEditButton() {
        return createButton("edit");
    }

    public static JButton createRemoveButton() {
        return createButton("remove");
    }
}
