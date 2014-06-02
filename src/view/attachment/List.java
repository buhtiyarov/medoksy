package view.attachment;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import view.Constants;

import model.Attachment;
import model.Image;
import model.Document;

public class List {
    private JComponent root;
    private JTable table;
    private ListTableModel model;
    private ListHandler handler;
    private JButton remove;
    private View view;

    public List() {
        root = new JPanel(new BorderLayout());
        table = new JTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);
        table.getSelectionModel().addListSelectionListener(createTableListSelectionListener());
        table.addMouseListener(createTableMouseListener());
        table.setModel(model = new ListTableModel());
        root.add(new JScrollPane(table));

        JPanel buttons = new JPanel(new FlowLayout());
        root.add(buttons, BorderLayout.PAGE_END);

        JButton addImage = new JButton("Добавить изображение");
        addImage.addActionListener(createAddImageActionListener());
        buttons.add(addImage);

        JButton addDocument = new JButton("Добавить документ");
        addDocument.addActionListener(createAddDocumentActionListener());
        buttons.add(addDocument);

        remove = new JButton("Удалить");
        remove.setEnabled(false);
        remove.addActionListener(createRemoveActionListener());
        buttons.add(remove);

        view = new View();
        view.hide();
    }

    protected ListSelectionListener createTableListSelectionListener() {
        return new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (table.getSelectionModel().isSelectionEmpty()) {
                    remove.setEnabled(false);
                } else {
                    remove.setEnabled(true);
                }
            }
        };
    }

    protected MouseListener createTableMouseListener() {
        return new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        view.setAttachment(model.getAttachmentAtRow(row));
                        view.show();
                    }
                }
            }
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
        };
    }

    protected ActionListener createAddImageActionListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "JPG & GIF Images", "jpg", "gif");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(null);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    try {
                        Image image = new Image();
                        image.setName(chooser.getSelectedFile().getName());
                        FileInputStream is = new FileInputStream(chooser.getSelectedFile());
                        byte b[] = new byte[is.available()];
                        is.read(b, 0, is.available());
                        image.setContent(b);
                        image.setSize(b.length);
                        model.addAttachment(image);
                        view.setAttachment(image);
                        view.show();
                    } catch (FileNotFoundException exception) {

                    } catch (IOException exception) {

                    }
                }
            }
        };
    }

    protected ActionListener createAddDocumentActionListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Document document = new Document();
                model.addAttachment(document);
                view.setAttachment(document);
                view.show();
            }
        };
    }

    protected ActionListener createRemoveActionListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectionModel().getMinSelectionIndex();
                if (row != -1 && JOptionPane.showConfirmDialog(null, "Удалить визит?") == JOptionPane.YES_OPTION) {
                    handler.remove(model.getAttachmentAtRow(row));
                }
            }
        };
    }

    public JComponent getRoot() {
        return root;
    }

    public void setAttachments(ArrayList<Attachment> attachments) {
        model.setAttachments(attachments);
    }

    public void updateAttachment(Attachment attachment) {
        model.updateAttachment(attachment);
    }

    public void setHandler(ListHandler handler) {
        this.handler = handler;
    }

    private class ListTableModel extends AbstractTableModel {
        private ArrayList<Attachment> attachments = new ArrayList<Attachment>();
        private String [] columnNames = { Constants.ATTACHMENT_NAME_LABEL };

        public void setAttachments(ArrayList<Attachment> attachments) {
            this.attachments = attachments;
            fireTableDataChanged();
        }

        public void addAttachment(Attachment attachment) {
            attachments.add(attachment);
            fireTableDataChanged();
        }

        public void updateAttachment(Attachment attachment) {
            int row = getRowAtAttachment(attachment);
            if (row >= 0) {
                fireTableRowsUpdated(row, row);
            }
        }

        public Attachment getAttachmentAtRow(int row) {
            return attachments.get(row);
        }

        public int getRowAtAttachment(Attachment attachment) {
            return attachments.indexOf(attachment);
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public int getRowCount() {
            return attachments.size();
        }

        public Object getValueAt(int row, int col) {
            Attachment attachment = getAttachmentAtRow(row);
            switch (col) {
            case 0:
                return attachment.getName();
            }
            return null;
        }
    }
}
