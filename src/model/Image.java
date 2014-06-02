package model;

public class Image extends Attachment {
    private int size = 0;
    private ValueHolder content;

    public void setSize(int value) {
        size = value;
    }
    public int getSize() {
        return size;
    }

    private ValueHolder getContentHolder() {
        if (content == null) {
            content = new ValueHolder();
        }
        if (content.getValue() == null) {
            content.setValue(new byte[] {});
        }
        return content;
    }

    public void setContent(byte[] value) {
        getContentHolder().setValue(value);
    }

    public void setContent(ValueHolder value) {
        content = value;
    }

    public byte[] getContent() {
        return (byte[]) getContentHolder().getValue();
    }
}
