package model;

import java.util.Date;
import java.util.ArrayList;

public class Client {
    private int id = 0;
    private String name = "";
    private String address = "";
    private String birthday = "";
    private String phone = "";
    private String notes = "";  // @TODO: ValueHolder
    private Date created = new Date();
    private ValueHolder visits;
    private ValueHolder attachments;

    public void setId(int value) {
        id = value;
    }
    public int getId() {
        return id;
    }

    public void setName(String value) {
        name = value;
    }
    public String getName() {
        return name;
    }

    public void setAddress(String value) {
        address = value;
    }
    public String getAddress() {
        return address;
    }

    public void setBirthday(String value) {
        birthday = value;
    }
    public String getBirthday() {
        return birthday;
    }

    public void setPhone(String value) {
        phone = value;
    }
    public String getPhone() {
        return phone;
    }

    public void setNotes(String value) {
        notes = value;
    }
    public String getNotes() {
        return notes;
    }

    public void setCreated(Date value) {
        created = value;
    }
    public Date getCreated() {
        return created;
    }

    private ValueHolder getVisitsHolder() {
        if (visits == null) {
            visits = new ValueHolder();
        }
        if (visits.getValue() == null) {
            visits.setValue(new ArrayList<Visit>());
        }
        return visits;
    }
    public void setVisits(ArrayList<Visit> value) {
        getVisitsHolder().setValue(value);
    }
    public void setVisits(ValueHolder value) {
        visits = value;
    }
    public ArrayList<Visit> getVisits() {
        return (ArrayList<Visit>) getVisitsHolder().getValue();
    }
    public void addVisit(Visit visit) {
        getVisits().add(visit);
    }
    public void removeVisit(Visit visit) {
        getVisits().remove(visit);
    }

    private ValueHolder getAttachmentsHolder() {
        if (attachments == null) {
            attachments = new ValueHolder();
        }
        if (attachments.getValue() == null) {
            attachments.setValue(new ArrayList<Attachment>());
        }
        return visits;
    }
    public void setAttachment(ArrayList<Attachment> value) {
        getAttachmentsHolder().setValue(value);
    }
    public void setAttachments(ValueHolder value) {
        attachments = value;
    }
    public ArrayList<Attachment> getAttachments() {
        return (ArrayList<Attachment>) getAttachmentsHolder().getValue();
    }
    public void addAttachment(Attachment attachment) {
        getAttachments().add(attachment);
    }
    public void removeAttachment(Attachment attachment) {
        getAttachments().remove(attachment);
    }
}
