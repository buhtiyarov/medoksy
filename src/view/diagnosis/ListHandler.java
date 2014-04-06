package view.diagnosis;

import model.Diagnosis;

public interface ListHandler {
    public void add();
    public void edit(Diagnosis diagnosis);
    public void remove(Diagnosis diagnosis);
}
