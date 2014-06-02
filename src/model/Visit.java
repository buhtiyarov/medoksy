package model;

import java.util.Date;
import java.util.ArrayList;

public class Visit {
    private int id = 0;
    private Client client;
    private Diagnosis diagnosis;
    private String description = "";
    private Date created = new Date();
    private ValueHolder images;

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

    protected ValueHolder getImagesHolder() {
        if (images == null) {
            images = new ValueHolder();
        }
        if (images.getValue() == null) {
            images.setValue(new ArrayList<Image>());
        }
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        getImagesHolder().setValue(images);
    }

    public void setImages(ValueHolder value) {
        images = value;
    }

    public ArrayList<Image> getImages() {
        return (ArrayList<Image>) getImagesHolder().getValue();
    }
}
