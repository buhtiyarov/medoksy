package view.image;

import model.Image;

public interface ListHandler {
    public void add();
    public void remove(Image image);
    public void select(Image image);
    public void deselect();
}
