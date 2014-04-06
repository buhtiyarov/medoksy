package view;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.awt.FlowLayout;
import net.sourceforge.jdatepicker.JDateComponentFactory;
import net.sourceforge.jdatepicker.JDatePicker;
import model.SearchParams;

public class SearchPanel {
    private JComponent root;
    private JTextField search;
    private JDatePicker created;

    public SearchPanel() {
        root = new JPanel();

        search = new JTextField(20);
        search.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {}
            public void insertUpdate(DocumentEvent e) {
                // searchParams.setSearch(search.getText());
            }
            public void removeUpdate(DocumentEvent e) {
                // searchParams.setSearch(search.getText());
            }
        });
        root.add(search);

        created = JDateComponentFactory.createJDatePicker();
        root.add((JComponent) created);
    }

    public JComponent getRoot() {
        return root;
    }

    public void setSearchParams(SearchParams params) {

    }
}
