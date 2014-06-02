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
import javax.print.PrintServiceLookup;
import javax.print.PrintService;
import javax.print.DocPrintJob;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.SimpleDoc;
import javax.print.PrintException;
import javax.print.ServiceUI;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.StringReader;
import view.EditConstraintsFactory;
import view.Constants;
import view.IRichEditor;
import util.PluginFactory;
import model.Visit;
import model.Diagnosis;
import util.Printer;

public class Edit {
    private JComponent root;
    private JComboBox<String> diagnosis;
    private IRichEditor description;
    private Visit visit;
    private ArrayList<Diagnosis> diagnoses;
    private EditHandler handler;

    public Edit() {
        root = new JPanel(new GridBagLayout());

        root.add(new JLabel(Constants.VISIT_DIAGNOSIS_LABEL),
                 EditConstraintsFactory.createLabelConstraint(0, 0));
        root.add(diagnosis = new JComboBox<String>(),
                 EditConstraintsFactory.createTextFieldConstraint(1, 0));
        diagnosis.addActionListener(createDiagnosisActionListener());

        description = PluginFactory.createRichEditor();
        root.add(description.getRoot(), EditConstraintsFactory.createRichEditorConstraint(0, 1));

        JPanel buttons = new JPanel(new FlowLayout());
        GridBagConstraints c = EditConstraintsFactory.createButtonConstraint(0, 2, 2);
        c.insets = new Insets(0, 0, 0, 0);
        root.add(buttons, c);

        JButton print = new JButton("Распечатать описание");
        print.addActionListener(createPrintActionListener());
        buttons.add(print);
    }

    protected ActionListener createDiagnosisActionListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (diagnoses != null && diagnosis.getSelectedIndex() > 0) {
                    Diagnosis d = diagnoses.get(diagnosis.getSelectedIndex() - 1);
                    if (description.getText().replaceAll("<[^>]+>", "").isEmpty()) {
                        description.setText(d.getTemplate());
                    }
                }
            }
        };
    }

    protected ActionListener createPrintActionListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Printer.printHTML(description.getText());
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

    public void commit() {
        if (visit != null) {
            if (diagnoses != null && diagnosis.getSelectedIndex() > 0) {
                Diagnosis d = diagnoses.get(diagnosis.getSelectedIndex() - 1);
                visit.setDiagnosis(d);
            }
            visit.setDescription(description.getText());
        }
    }
}
