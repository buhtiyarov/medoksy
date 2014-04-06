package view.diagnosis;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import java.util.ArrayList;

import view.ButtonFactory;
import model.Diagnosis;

public class List {
    private JComponent root = new JPanel(new BorderLayout());
    private JTable table = new JTable();
    private JButton add;
    private JButton edit;
    private JButton remove;
    private ActionListener buttonActionListener;
    private ListHandler handler;
    private ListTableModel model = new ListTableModel();

    public List() {
        JToolBar toolbar = new JToolBar("Operations");
        toolbar.setAlignmentX(JToolBar.LEFT_ALIGNMENT);
        root.add(toolbar, BorderLayout.PAGE_START);

        buttonActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (handler == null) {
                    return;
                }
                switch (e.getActionCommand()) {
                case "add":
                    handler.add();
                    break;
                case "edit":
                    handler.edit(model.getDiagnosisAtRow(table.getSelectionModel().getMinSelectionIndex()));
                    break;
                case "remove":
                    handler.remove(model.getDiagnosisAtRow(table.getSelectionModel().getMinSelectionIndex()));
                    break;
                }
            }
        };

        add = ButtonFactory.createAddButton();
        add.addActionListener(buttonActionListener);
        toolbar.add(add);

        edit = ButtonFactory.createEditButton();
        edit.addActionListener(buttonActionListener);
        toolbar.add(edit);

        remove = ButtonFactory.createRemoveButton();
        remove.addActionListener(buttonActionListener);
        toolbar.add(remove);

        table.setModel(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        root.add(scrollPane, BorderLayout.CENTER);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (table.getSelectionModel().isSelectionEmpty()) {
                    edit.setEnabled(false);
                    remove.setEnabled(false);
                } else {
                    edit.setEnabled(true);
                    remove.setEnabled(true);
                }
            }
        });
        edit.setEnabled(false);
        remove.setEnabled(false);

        table.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && handler != null) {
                    handler.edit(model.getDiagnosisAtRow(table.rowAtPoint(e.getPoint())));
                }
            }
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
        });
    }

    public void setDiagnoses(ArrayList<Diagnosis> diagnoses) {
        model.setDiagnoses(diagnoses);
    }

    public void setHandler(ListHandler handler) {
        this.handler = handler;
    }

    public JComponent getRoot() {
        return root;
    }

    private class ListTableModel extends AbstractTableModel {
        private ArrayList<Diagnosis> diagnoses = new ArrayList<Diagnosis>();
        private String[] columnNames = { "Дигноз" };

        public void setDiagnoses(ArrayList<Diagnosis> diagnoses) {
            this.diagnoses = diagnoses;
            fireTableDataChanged();
        }

        public Diagnosis getDiagnosisAtRow(int row) {
            return diagnoses.get(row);
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public int getRowCount() {
            return diagnoses.size();
        }

        public Object getValueAt(int row, int col) {
            Diagnosis diagnosis = diagnoses.get(row);
            switch (col) {
            case 0:
                return diagnosis.getName();
            }
            return null;
        }
    }
}
