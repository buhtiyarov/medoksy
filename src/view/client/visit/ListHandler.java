package view.client.visit;

import model.Visit;

public interface ListHandler {
    public void add();
    public void remove(Visit visit);
    public void select(Visit visit);
    public void deselect();
}
