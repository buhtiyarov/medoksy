package view.image;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Dimension;
import view.Constants;
import model.Image;

public class View {
    private Image image;
    private ImageIcon imageIcon;
    private JFrame frame;

    public View() {
        frame = new JFrame("Image view");
        frame.setPreferredSize(new Dimension(700, 400));
    }

    public void setImage(Image image) {
        this.image = image;
        imageIcon = new ImageIcon(image.getContent());
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new JScrollPane(new JLabel(imageIcon)));
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    public void show() {
        frame.setVisible(true);
    }

    public void hide() {
        frame.setVisible(false);
    }
}
