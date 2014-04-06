package view.visit;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Iterator;
import view.Format;
import view.Constants;

import model.Client;
import model.Visit;

public class ListTableModel extends AbstractTableModel {
    private ArrayList<Visit> visits = new ArrayList<Visit>();
    private String[] columnNames = {
        Constants.CLIENT_NAME_LABEL,
        Constants.CLIENT_ADDRESS_LABEL,
        Constants.CLIENT_PHONE_LABEL,
        Constants.VISIT_DIAGNOSIS_LABEL,
        Constants.VISIT_CREATED_LABEL
    };

    public void setVisits(ArrayList<Visit> visits) {
        this.visits = visits;
        fireTableDataChanged();
    }

    public Visit getVisitAtRow(int row) {
        return visits.get(row);
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
        Visit visit = visits.get(row);
        Client client = visit.getClient();
        switch (col) {
        case 0:
            return client.getName();

        case 1:
            return client.getAddress();

        case 2:
            return client.getPhone();

        case 3:
            return visit.getDiagnosis().getName();

        case 4:
            return Format.date(visit.getCreated());
        }
        return null;
    }
}
