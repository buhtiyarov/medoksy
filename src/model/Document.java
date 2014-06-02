package model;

public class Document extends Attachment {
    private String content;

    public void setContent(String value) {
        content = value;
    }

    public String getContent() {
        return content;
    }
}
