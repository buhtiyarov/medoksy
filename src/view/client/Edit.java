package view.client;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JTabbedPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import model.Client;
import model.Visit;
import model.Diagnosis;
import model.Image;
import view.EditConstraintsFactory;
import view.Constants;

public class Edit {
    private JComponent root;
    private JFrame frame;
    private EditHandler handler;
    private Client client;
    private Visit visit;
    private JTextField name;
    private JTextField address;
    private JTextField birthday;
    private JTextField phone;
    private JTextArea notes;
    private JTabbedPane visitTabs;
    private view.client.visit.List visitList;
    private view.client.visit.Edit visitEdit;
    private view.attachment.List attachemntList;

    public Edit() {
        root = buildUI();
    }

    public Edit(JFrame parent) {
        frame = new JFrame();
        frame.getContentPane().add(root = buildUI());
        frame.pack();
        frame.setLocationRelativeTo(parent);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (handler != null && client != null) {
                    client.setName(name.getText());
                    client.setAddress(address.getText());
                    client.setBirthday(birthday.getText());
                    client.setPhone(phone.getText());
                    client.setNotes(notes.getText());
                    visitEdit.commit();
                    handler.submit(client);
                }
            }
        });
    }

    protected JComponent buildUI() {
        JComponent root = new JPanel(new GridBagLayout());
        root.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        root.setPreferredSize(new Dimension(1024, 500));

        root.add(new JLabel(Constants.CLIENT_NAME_LABEL),
                 EditConstraintsFactory.createLabelConstraint(0, 0));
        root.add(name = new JTextField(20),
                 EditConstraintsFactory.createTextFieldConstraint(1, 0));

        root.add(new JLabel(Constants.CLIENT_BIRTHDAY_LABEL),
                 EditConstraintsFactory.createLabelConstraint(0, 1));
        root.add(birthday = new JTextField(20),
                 EditConstraintsFactory.createTextFieldConstraint(1, 1));

        root.add(new JLabel(Constants.CLIENT_ADDRESS_LABEL),
                 EditConstraintsFactory.createLabelConstraint(0, 2));
        root.add(address = new JTextField(20),
                 EditConstraintsFactory.createTextFieldConstraint(1, 2));

        root.add(new JLabel(Constants.CLIENT_PHONE_LABEL),
                 EditConstraintsFactory.createLabelConstraint(0, 3));
        root.add(phone = new JTextField(20),
                 EditConstraintsFactory.createTextFieldConstraint(1, 3));

        root.add(new JLabel(Constants.CLIENT_NOTES_LABEL),
                 EditConstraintsFactory.createLabelConstraint(0, 4));
        root.add(new JScrollPane(notes = new JTextArea()),
                 EditConstraintsFactory.createTextAreaConstraint(1, 4));

        visitList = new view.client.visit.List();
        visitList.setHandler(createVisitListHandler());
        root.add(visitList.getRoot(),
                 EditConstraintsFactory.createComponentConstraint(0, 5, 2, 1));

        visitTabs = new JTabbedPane();
        root.add(visitTabs, EditConstraintsFactory.createComponentConstraint(2, 0, 2, 6));

        visitEdit = new view.client.visit.Edit();
        visitEdit.setHandler(createVisitEditHandler());
        visitTabs.add("Визит", visitEdit.getRoot());

        attachmentList = new view.attachment.List();
        // attachmentList.setHandler(createImageListHandler());
        visitTabs.add("Документы", attachmentList.getRoot());

        return root;
    }

    protected view.client.visit.ListHandler createVisitListHandler() {
        return new view.client.visit.ListHandler() {
            public void add() {
                handler.addVisit(client);
            }
            public void remove(Visit visit) {
                handler.removeVisit(client, visit);
            }
            public void select(Visit visit) {
                selectVisit(visit);
            }
            public void deselect() {
                deselectVisit();
            }
        };
    }

    protected view.client.visit.EditHandler createVisitEditHandler() {
        return new view.client.visit.EditHandler() {
            public void changed() {
                visitList.updateVisit(visit);
            }
        };
    }

    protected view.image.ListHandler createImageListHandler() {
        return new view.image.ListHandler() {
            public void add() {}
            public void remove(Image image) {}
            public void select(Image image) {}
            public void deselect() {}
        };
    }

    public JComponent getRoot() {
        return root;
    }

    public Edit setActionHandler(EditHandler handler) {
        this.handler = handler;
        return this;
    }

    public Edit setClient(Client client) {
        this.client = client;
        name.setText(client.getName());
        address.setText(client.getAddress());
        birthday.setText(client.getBirthday());
        phone.setText(client.getPhone());
        notes.setText(client.getNotes());
        visitList.setVisits(client.getVisits());
        return this;
    }

    public Edit selectVisit(Visit visit) {
        this.visit = visit;
        visitList.selectVisit(visit);
        visitEdit.setVisit(visit);
        // imageList.setImages(visit.getImages());
        return this;
    }

    public Edit deselectVisit() {
        return this;
    }

    public void setDiagnoses(ArrayList<Diagnosis> diagnoses) {
        visitEdit.setDiagnoses(diagnoses);
    }

    public Edit show() {
        if (frame != null) {
            frame.setVisible(true);
        }
        return this;
    }

    public Edit hide() {
        if (frame != null) {
            frame.setVisible(false);
        }
        return this;
    }
}
