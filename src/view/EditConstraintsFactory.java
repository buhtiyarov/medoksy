package view;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class EditConstraintsFactory {
    private static GridBagConstraints createDefaultConstraint(int gridx, int gridy) {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(2, 2, 2, 2);
        c.anchor = GridBagConstraints.PAGE_START;
        c.gridx = gridx;
        c.gridy = gridy;
        return c;
    }

    public static GridBagConstraints createLabelConstraint(int gridx, int gridy) {
        return createDefaultConstraint(gridx, gridy);
    }

    public static GridBagConstraints createTextFieldConstraint(int gridx, int gridy) {
        GridBagConstraints c = createDefaultConstraint(gridx, gridy);
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        return c;
    }

    public static GridBagConstraints createTextAreaConstraint(int gridx, int gridy) {
        GridBagConstraints c = createDefaultConstraint(gridx, gridy);
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        return c;
    }

    public static GridBagConstraints createRichEditorConstraint(int gridx, int gridy) {
        GridBagConstraints c = createTextAreaConstraint(gridx, gridy);
        c.gridwidth = 2;
        return c;
    }

    public static GridBagConstraints createButtonConstraint(int gridx, int gridy, int gridwidth) {
        GridBagConstraints c = createDefaultConstraint(gridx, gridy);
        c.gridwidth = gridwidth;
        return c;
    }

    public static GridBagConstraints createButtonConstraint(int gridx, int gridy) {
        return createButtonConstraint(gridx, gridy, 2);
    }

    public static GridBagConstraints createComponentConstraint(int gridx, int gridy, int gridwidth, int gridheight) {
        GridBagConstraints c = createDefaultConstraint(gridx, gridy);
        c.gridwidth = gridwidth;
        c.gridheight = gridheight;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 0, 0, 0);
        return c;
    }
}
