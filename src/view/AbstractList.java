package view;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import net.sourceforge.jdatepicker.JDateComponentFactory;
import net.sourceforge.jdatepicker.JDatePicker;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.Date;
import model.SearchParams;

public class AbstractList {
    private JComponent root;
    private JTable table;
    private AbstractListHandler handler;
    private ActionListener actionListener;
    private JTextField search;
    private JDatePicker created;
    private SearchParams searchParams;
    private JButton add;
    private JButton edit;
    private JButton remove;

    public AbstractList() {
        root = new JPanel(new BorderLayout());

        JToolBar toolbar = new JToolBar("Operations");
        toolbar.setAlignmentX(JToolBar.LEFT_ALIGNMENT);
        root.add(toolbar, BorderLayout.PAGE_START);

        search = new JTextField();
        search.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {}
            public void insertUpdate(DocumentEvent e) {
                if (searchParams != null) {
                    searchParams.setSearch(search.getText());
                }
            }
            public void removeUpdate(DocumentEvent e) {
                if (searchParams != null) {
                    searchParams.setSearch(search.getText());
                }
            }
        });
        toolbar.add(search);

        created = JDateComponentFactory.createJDatePicker(new UtilDateModel());
        toolbar.add((JComponent) created);
        created.addActionListener(createCreatedActionListener());

        actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (handler == null) {
                    return;
                }
                switch (e.getActionCommand()) {
                case "add":
                    handler.add();
                    break;

                case "edit":
                    handler.edit(table.getSelectionModel().getMinSelectionIndex());
                    break;

                case "remove":
                    handler.remove(table.getSelectionModel().getMinSelectionIndex());
                    break;
                }
            }
        };

        add = new JButton(new ImageIcon(getClass().getResource("/icons/add.png")));
        add.setActionCommand("add");
        add.addActionListener(actionListener);
        toolbar.add(add);

        edit = new JButton(new ImageIcon(getClass().getResource("/icons/edit.png")));
        edit.setActionCommand("edit");
        edit.setEnabled(false);
        edit.addActionListener(actionListener);
        toolbar.add(edit);

        remove = new JButton(new ImageIcon(getClass().getResource("/icons/remove.png")));
        remove.setActionCommand("remove");
        remove.setEnabled(false);
        remove.addActionListener(actionListener);
        toolbar.add(remove);

        table = new JTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (table.getSelectionModel().isSelectionEmpty()) {
                    edit.setEnabled(false);
                    remove.setEnabled(false);
                } else {
                    edit.setEnabled(true);
                    remove.setEnabled(true);
                }
            }
        });

        table.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    handler.edit(table.rowAtPoint(e.getPoint()));
                }
            }
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
        });

        JScrollPane scrollPane = new JScrollPane(table);
        root.add(scrollPane, BorderLayout.CENTER);
    }

    protected ActionListener createCreatedActionListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchParams.setDate((Date) created.getModel().getValue());
            }
        };
    }

    public JComponent getRoot() {
        return root;
    }

    public void setModel(AbstractTableModel model) {
        table.setModel(model);
    }

    public void setHandler(AbstractListHandler handler) {
        this.handler = handler;
    }

    public void setSearchParams(SearchParams params) {
        searchParams = params;
    }
}
