package view.image;

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
import model.Image;

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

        JButton add = new JButton("Добавить изображение");
        add.addActionListener(createAddActionListener());
        buttons.add(add);

        remove = new JButton("Удалить изображение");
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
                    // handler.deselect();
                } else {
                    remove.setEnabled(true);
                    // handler.select(model.getImageAtRow(table.getSelectionModel().getMinSelectionIndex()));
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
                        view.setImage(model.getImageAtRow(row));
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

    protected ActionListener createAddActionListener() {
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
                        model.addImage(image);
                    } catch (FileNotFoundException exception) {

                    } catch (IOException exception) {

                    }
                }
            }
        };
    }

    protected ActionListener createRemoveActionListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectionModel().getMinSelectionIndex();
                if (row != -1 && JOptionPane.showConfirmDialog(null, "Удалить визит?") == JOptionPane.YES_OPTION) {
                    // handler.remove(model.getImageAtRow(row));
                }
            }
        };
    }

    public JComponent getRoot() {
        return root;
    }

    public void setImages(ArrayList<Image> images) {
        model.setImages(images);
    }

    public void updateImage(Image image) {
        model.updateImage(image);
    }

    public void setHandler(ListHandler handler) {
        this.handler = handler;
    }

    private class ListTableModel extends AbstractTableModel {
        private ArrayList<Image> images = new ArrayList<Image>();
        private String [] columnNames = {
            Constants.IMAGE_NAME_LABEL, Constants.IMAGE_SIZE_LABEL
        };

        public void setImages(ArrayList<Image> images) {
            this.images = images;
            fireTableDataChanged();
        }

        public void addImage(Image image) {
            images.add(image);
            fireTableDataChanged();
        }

        public void updateImage(Image image) {
            int row = getRowAtImage(image);
            if (row >= 0) {
                fireTableRowsUpdated(row, row);
            }
        }

        public Image getImageAtRow(int row) {
            return images.get(row);
        }

        public int getRowAtImage(Image image) {
            return images.indexOf(image);
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public int getRowCount() {
            return images.size();
        }

        public Object getValueAt(int row, int col) {
            Image image = getImageAtRow(row);
            switch (col) {
            case 0:
                return image.getName();

            case 1:
                return image.getSize();
            }
            return null;
        }
    }
}
