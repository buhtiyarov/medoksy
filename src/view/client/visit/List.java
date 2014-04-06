package view.client.visit;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.text.DateFormat;
import view.Constants;
import view.Format;
import model.Visit;

public class List {
    private JComponent root;
    private JTable table;
    private ListTableModel model;
    private ListHandler handler;
    private JButton remove;

    public List() {
        root = new JPanel(new BorderLayout());
        root.setOpaque(true);
        root.setBorder(BorderFactory.createTitledBorder(Constants.VISIT_LIST_TITLE));

        table = new JTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);
        table.getSelectionModel().addListSelectionListener(createTableListSelectionListener());
        table.setModel(model = new ListTableModel());
        root.add(new JScrollPane(table));

        JPanel buttons = new JPanel(new FlowLayout());
        root.add(buttons, BorderLayout.PAGE_END);

        JButton add = new JButton("Добавить визит");
        add.addActionListener(createAddActionListener());
        buttons.add(add);

        remove = new JButton("Удалить визит");
        remove.setEnabled(false);
        remove.addActionListener(createRemoveActionListener());
        buttons.add(remove);
    }

    protected ListSelectionListener createTableListSelectionListener() {
        return new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (table.getSelectionModel().isSelectionEmpty()) {
                    remove.setEnabled(false);
                    handler.deselect();
                } else {
                    remove.setEnabled(true);
                    handler.select(model.getVisitAtRow(table.getSelectionModel().getMinSelectionIndex()));
                }
            }
        };
    }

    protected ActionListener createAddActionListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handler.add();
            }
        };
    }

    protected ActionListener createRemoveActionListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectionModel().getMinSelectionIndex();
                if (row != -1 && JOptionPane.showConfirmDialog(null, "Удалить визит?") == JOptionPane.YES_OPTION) {
                    handler.remove(model.getVisitAtRow(row));
                }
            }
        };
    }

    public JComponent getRoot() {
        return root;
    }

    public void setVisits(ArrayList<Visit> visits) {
        model.setVisits(visits);
    }

    public void selectVisit(Visit visit) {
        int row = model.getRowAtVisit(visit);
        ListSelectionModel sm = table.getSelectionModel();
        if (row != -1) {
            if (sm.isSelectionEmpty() && sm.getMinSelectionIndex() != row) {
                sm.setSelectionInterval(row, row);
            }
        }
    }

    public void updateVisit(Visit visit) {
        model.updateVisit(visit);
    }

    public void setHandler(ListHandler handler) {
        this.handler = handler;
    }

    private class ListTableModel extends AbstractTableModel {
        private ArrayList<Visit> visits = new ArrayList<Visit>();
        private String[] columnNames = { "Диагноз", "Дата" };

        public void setVisits(ArrayList<Visit> visits) {
            this.visits = visits;
            fireTableDataChanged();
        }

        public void updateVisit(Visit visit) {
            int row = getRowAtVisit(visit);
            if (row >= 0) {
                fireTableRowsUpdated(row, row);
            }
        }

        public Visit getVisitAtRow(int row) {
            return visits.get(row);
        }

        public int getRowAtVisit(Visit visit) {
            return visits.indexOf(visit);
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public int getRowCount() {
            return visits.size();
        }

        public Object getValueAt(int row, int col) {
            Visit visit = getVisitAtRow(row);
            switch (col) {
            case 0:
                return visit.getDiagnosis().getName();

            case 1:
                return Format.date(visit.getCreated());
            }
            return null;
        }
    }
}
