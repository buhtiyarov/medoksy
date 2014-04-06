package view.visit;

import model.Visit;
import model.Client;

import java.util.Date;

public interface ListHandler {
    public void add();
    public void edit(Client client, Visit visit);
    public void remove(Visit visit);
}
