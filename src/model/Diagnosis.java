package model;

public class Diagnosis {
    private int id = 0;
    private String name = "";
    private String template = "";

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

    public void setTemplate(String value) {
        template = value;
    }
    public String getTemplate() {
        return template;
    }

    public static class Null extends Diagnosis {}
}
