package view.client;

import model.Client;

public interface ListHandler {
    public void add();
    public void edit(Client client);
    public void remove(Client client);
}
