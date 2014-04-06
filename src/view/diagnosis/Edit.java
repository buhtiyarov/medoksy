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
import java.awt.GridBagLayout;
import java.awt.Dimension;

import model.Diagnosis;

import view.EditConstraintsFactory;

public class Edit {
    private JComponent root;
    private JDialog frame;
    private EditHandler handler;
    private JTextField name;
    private JTextArea template;
    private Diagnosis diagnosis;

    public Edit() {
        buildUI();
    }

    public Edit(JFrame parent) {
        buildUI();
        frame = new JDialog(parent, "Edit diagnosis");
        frame.getContentPane().add(root);
        frame.pack();
        frame.setLocationRelativeTo(parent);
    }

    private void buildUI() {
        root = new JPanel(new GridBagLayout());
        root.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        root.setPreferredSize(new Dimension(700, 400));

        root.add(new JLabel("Диагноз"), EditConstraintsFactory.createLabelConstraint(0, 0));
        root.add(name = new JTextField(20), EditConstraintsFactory.createTextFieldConstraint(1, 0));

        root.add(new JLabel("Шаблон"), EditConstraintsFactory.createLabelConstraint(0, 1));
        root.add(new JScrollPane(template = new JTextArea(5, 20)), EditConstraintsFactory.createTextAreaConstraint(1, 1));

        JButton submit = new JButton("Сохранить");
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (handler != null && diagnosis != null) {
                    diagnosis.setName(name.getText());
                    diagnosis.setTemplate(template.getText());
                    handler.submit(diagnosis);
                }
            }
        });
        root.add(submit, EditConstraintsFactory.createButtonConstraint(0, 2));
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
