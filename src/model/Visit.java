package model;

import java.util.Date;

public class Visit {
    private int id = 0;
    private Client client;
    private Diagnosis diagnosis;
    private String description = "";
    private Date created = new Date();

    public void setId(int value) {
        id = value;
    }
    public int getId() {
        return id;
    }

    public void setClient(Client value) {
        client = value;
    }
    public Client getClient() {
        return client;
    }

    public void setDiagnosis(Diagnosis value) {
        diagnosis = value;
    }
    public Diagnosis getDiagnosis() {
        return diagnosis;
    }

    public void setDescription(String value) {
        description = value;
    }
    public String getDescription() {
        return description;
    }

    public void setCreated(Date value) {
        created = value;
    }
    public Date getCreated() {
        return created;
    }
}
