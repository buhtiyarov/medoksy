package view.attachment;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Dimension;
import view.Constants;
import view.IRichEditor;
import util.PluginFactory;

import model.Attachment;
import model.Image;
import model.Document;

public class View {
    private Attachment attachment;
    private ImageIcon imageIcon;
    private Image image;
    private IRichEditor content;
    private JFrame frame;

    public View() {
        frame = new JFrame("Attachment view");
        frame.setPreferredSize(new Dimension(700, 400));
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
        if (attachment instanceof Image) {
            image = (Image) attachment;
            imageIcon = new ImageIcon(image.getContent());
            frame.getContentPane().removeAll();
            frame.getContentPane().add(new JScrollPane(new JLabel(imageIcon)));
        }
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
