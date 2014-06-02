package view.diagnosis;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.JDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.awt.GridBagLayout;
import java.awt.Dimension;

import model.Diagnosis;

import view.EditConstraintsFactory;
import view.IRichEditor;
import util.PluginFactory;

public class Edit {
    private JComponent root;
    private JFrame frame;
    private EditHandler handler;
    private JTextField name;
    private IRichEditor template;
    private Diagnosis diagnosis;

    public Edit() {
        buildUI();
    }

    public Edit(JFrame parent) {
        buildUI();
        frame = new JFrame();
        frame.getContentPane().add(root);
        frame.pack();
        frame.setLocationRelativeTo(parent);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (handler != null && diagnosis != null) {
                    diagnosis.setName(name.getText());
                    diagnosis.setTemplate(template.getText());
                    handler.submit(diagnosis);
                }
            }
        });
    }

    private void buildUI() {
        root = new JPanel(new GridBagLayout());
        root.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        root.setPreferredSize(new Dimension(1024, 500));

        root.add(new JLabel("Диагноз"), EditConstraintsFactory.createLabelConstraint(0, 0));
        root.add(name = new JTextField(20), EditConstraintsFactory.createTextFieldConstraint(1, 0));

        template = PluginFactory.createRichEditor();
        root.add(template.getRoot(), EditConstraintsFactory.createRichEditorConstraint(0, 1));
    }

    public Edit setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
        name.setText(diagnosis.getName());
        template.setText(diagnosis.getTemplate());
        return this;
    }

    public Edit setHandler(EditHandler handler) {
        this.handler = handler;
        return this;
    }

    public JComponent getRoot() {
        return root;
    }

    public Edit show() {
        if (frame != null) {
            frame.setVisible(true);
        }
        return this;
    }

    public Edit hide() {
        if (frame != null) {
            frame.setVisible(false);
        }
        return this;
    }
}
