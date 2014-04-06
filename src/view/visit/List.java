package view.visit;

import javax.swing.JComponent;
import java.util.ArrayList;

import view.AbstractList;
import view.AbstractListHandler;

import model.Visit;
import model.Client;
import model.SearchParams;

public class List {
    private AbstractList list = new AbstractList();
    private ListTableModel model = new ListTableModel();
    private ListHandler handler;

    public List() {
        list.setModel(model);
        list.setHandler(new AbstractListHandler() {
            public void add() {
                handler.add();
            }

            public void edit(int row) {
                handler.edit(model.getVisitAtRow(row).getClient(), model.getVisitAtRow(row));
            }

            public void remove(int row) {
                handler.remove(model.getVisitAtRow(row));
            }
        });
    }

    public JComponent getRoot() {
        return list.getRoot();
    }

    public void setVisits(ArrayList<Visit> visits) {
        model.setVisits(visits);
    }

    public void setHandler(ListHandler handler) {
        this.handler = handler;
    }

    public void setSearchParams(SearchParams params) {
        list.setSearchParams(params);
    }
}
