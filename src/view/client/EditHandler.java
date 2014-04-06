package view.client;

import model.Client;
import model.Visit;

public interface EditHandler {
    public void addVisit(Client client);
    public void removeVisit(Client client, Visit visit);
    public void submit(Client client);
}
