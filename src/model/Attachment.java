package model;

abstract public class Attachment {
    private int id = 0;
    private String name = "";

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
}
