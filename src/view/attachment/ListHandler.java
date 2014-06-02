package view.attachment;

import model.Attachment;

public interface ListHandler {
    public void add();
    public void remove(Attachment attachment);
    public void select(Attachment attachment);
    public void deselect();
}
