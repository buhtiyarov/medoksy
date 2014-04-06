package view.client;

import javax.swing.JComponent;
import java.util.ArrayList;

import view.AbstractList;
import view.AbstractListHandler;

import model.Client;
import model.SearchParams;

public class List {
    private AbstractList list = new AbstractList();
    private ListTableModel model = new ListTableModel();
    private ListHandler handler;

    public List() {
        list.setModel(model);
        list.setHandler(createListHandler());
    }

    protected AbstractListHandler createListHandler() {
        return new AbstractListHandler() {
            public void add() {
                handler.add();
            }

            public void edit(int row) {
                handler.edit(model.getClientAtRow(row));
            }

            public void remove(int row) {
                handler.remove(model.getClientAtRow(row));
            }
        };
    }

    public JComponent getRoot() {
        return list.getRoot();
    }

    public void setClients(ArrayList<Client> clients) {
        model.setClients(clients);
    }

    public void setHandler(ListHandler handler) {
        this.handler = handler;
    }

    public void setSearchParams(SearchParams params) {
        list.setSearchParams(params);
    }
}
