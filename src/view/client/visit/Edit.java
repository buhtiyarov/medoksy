package view.client.visit;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.Iterator;
import view.EditConstraintsFactory;
import view.Constants;
import model.Visit;
import model.Diagnosis;

public class Edit {
    private JComponent root;
    private JComboBox<String> diagnosis;
    private JTextArea description;
    private Visit visit;
    private ArrayList<Diagnosis> diagnoses;
    private EditHandler handler;
    private JButton insertTemplate;

    public Edit() {
        root = new JPanel(new GridBagLayout());
        root.setOpaque(true);
        root.setBorder(BorderFactory.createTitledBorder(Constants.VISIT_EDIT_TITLE));

        root.add(new JLabel(Constants.VISIT_DIAGNOSIS_LABEL),
                 EditConstraintsFactory.createLabelConstraint(0, 0));
        root.add(diagnosis = new JComboBox<String>(),
                 EditConstraintsFactory.createTextFieldConstraint(1, 0));
        diagnosis.addActionListener(createDiagnosisActionListener());

        root.add(new JLabel(Constants.VISIT_DESCRIPTION_LABEL),
                 EditConstraintsFactory.createLabelConstraint(0, 1));
        root.add(new JScrollPane(description = new JTextArea(5, 20)),
                 EditConstraintsFactory.createTextAreaConstraint(1, 1));
        description.getDocument().addDocumentListener(createDescriptionDocumentListener());

        JPanel buttons = new JPanel(new FlowLayout());
        GridBagConstraints c = EditConstraintsFactory.createButtonConstraint(0, 2, 2);
        c.insets = new Insets(0, 0, 0, 0);
        root.add(buttons, c);

        insertTemplate = new JButton("Вставить шаблон");
        insertTemplate.setEnabled(false);
        insertTemplate.addActionListener(createInsertTemplateActionListener());
        buttons.add(insertTemplate);

        JButton print = new JButton("Распечатать описание");
        print.addActionListener(createPrintActionListener());
        buttons.add(print);
    }

    protected DocumentListener createDescriptionDocumentListener() {
        return new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {}
            public void insertUpdate(DocumentEvent e) {
                if (visit != null) {
                    visit.setDescription(description.getText());
                    fireChanged();
                }
            }
            public void removeUpdate(DocumentEvent e) {
                if (visit != null) {
                    visit.setDescription(description.getText());
                    fireChanged();
                }
            }
        };
    }

    protected ActionListener createDiagnosisActionListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (diagnosis.getSelectedIndex() == 0) {
                    insertTemplate.setEnabled(false);
                } else {
                    insertTemplate.setEnabled(true);
                }
                if (diagnoses != null && visit != null && diagnosis.getSelectedIndex() > 0) {
                    Diagnosis d = diagnoses.get(diagnosis.getSelectedIndex() - 1);
                    visit.setDiagnosis(d);
                    fireChanged();
                }
            }
        };
    }

    protected ActionListener createInsertTemplateActionListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (diagnoses != null && visit != null && diagnosis.getSelectedIndex() > 0 &&
                    JOptionPane.showConfirmDialog(null, "Вставить шаблон?") == JOptionPane.YES_OPTION) {
                    Diagnosis d = diagnoses.get(diagnosis.getSelectedIndex() - 1);
                    description.setText(d.getTemplate());
                    visit.setDescription(d.getTemplate());
                    fireChanged();
                }
            }
        };
    }

    protected ActionListener createPrintActionListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    description.print();
                } catch (PrinterException exception) {
                    JOptionPane.showMessageDialog(getRoot(), "Не получилось распечатать документ", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
    }

    private void fireChanged() {
        if (handler != null) {
            handler.changed();
        }
    }

    public JComponent getRoot() {
        return root;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
        diagnosis.setSelectedIndex(0);
        description.setText(visit.getDescription());
        if (visit.getDiagnosis() != null) {
            int index = diagnoses.indexOf(visit.getDiagnosis());
            if (index >= 0) {
                diagnosis.setSelectedIndex(index + 1);
            }
        }
    }

    public void setDiagnoses(ArrayList<Diagnosis> diagnoses) {
        this.diagnoses = diagnoses;
        this.diagnosis.removeAllItems();
        this.diagnosis.addItem("");
        Iterator<Diagnosis> iterator = diagnoses.iterator();
        while (iterator.hasNext()) {
            this.diagnosis.addItem(iterator.next().getName());
        }
    }

    public void setHandler(EditHandler handler) {
        this.handler = handler;
    }
}
