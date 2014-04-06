package view.client;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Iterator;
import view.Format;
import view.Constants;

import model.Client;

public class ListTableModel extends AbstractTableModel {
    private ArrayList<Client> clients = new ArrayList<Client>();
    private ArrayList<Client> visibleClients = new ArrayList<Client>();
    private String[] columnNames = {
        Constants.CLIENT_NAME_LABEL,
        Constants.CLIENT_ADDRESS_LABEL,
        Constants.CLIENT_PHONE_LABEL,
        Constants.CLIENT_CREATED_LABEL
    };
    private String search = "";

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
        filter();
    }

    public Client getClientAtRow(int row) {
        return visibleClients.get(row);
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public int getRowCount() {
        return visibleClients.size();
    }

    public Object getValueAt(int row, int col) {
        Client client = visibleClients.get(row);
        switch (col) {
        case 0:
            return client.getName();

        case 1:
            return client.getAddress();

        case 2:
            return client.getPhone();

        case 3:
            return Format.date(client.getCreated());
        }
        return null;
    }

    public void setSearch(String search) {
        this.search = search;
        filter();
    }

    private void filter() {
        visibleClients.clear();

        Iterator<Client> iterator = clients.iterator();
        while (iterator.hasNext()) {
            Client client = iterator.next();
            if (search.length() == 0 || client.getName().contains(search) ||
                client.getAddress().contains(search)) {
                visibleClients.add(client);
            }
        }

        fireTableDataChanged();
    }
}
