import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.UIManager;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import java.awt.Dimension;

import java.util.Date;
import java.util.ArrayList;

import view.Constants;
import view.MainFrame;
import mapper.MapperFactory;
import model.Client;
import model.Visit;
import model.Diagnosis;
import model.SearchParams;
import model.SimpleListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Application {
    private static Connection dbh;
    private static MapperFactory mapperFactory;
    private static MainFrame mainframe;

    private static view.visit.List visitsList;
    private static view.client.List clientsList;
    private static view.client.Edit editClient;
    private static view.diagnosis.List diagnosesList;
    private static view.diagnosis.Edit editDiagnosis;
    private static SearchParams clientsSearchParams = new SearchParams();
    private static SearchParams visitsSearchParams = new SearchParams();

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initDb();
                createVisitsList();
                createClientsList();
                createDiagnosesList();
                createMainFrame();
                createEditClient();
                createEditDiagnosis();
                updateClients();
                updateVisits();
                updateDiagnoses();
            }
        });
    }

    private static void initDb() {
        System.out.println(Application.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        try {
            dbh = DriverManager.getConnection("jdbc:sqlite:db.sqlite3");
            dbh.createStatement().execute("PRAGMA foreign_keys = ON");
        } catch (SQLException e) {
            // @TODO: show error message
            System.err.println("Can't connect to the database!");
        }
        mapperFactory = new MapperFactory(dbh);
    }

    private static void createMainFrame() {
        mainframe = new MainFrame();
        mainframe.addComponent(Constants.VISIT_LIST_TITLE, visitsList.getRoot());
        mainframe.addComponent(Constants.CLIENT_LIST_TITLE, clientsList.getRoot());
        mainframe.addComponent(Constants.DIAGNOSIS_LIST_TITLE, diagnosesList.getRoot());
        mainframe.show();
    }

    private static void createVisitsList() {
        visitsList = new view.visit.List();
        visitsList.setSearchParams(visitsSearchParams);
        visitsList.setHandler(new view.visit.ListHandler() {
            public void add() {
                Client client = mapperFactory.getClientMapper().create();
                Visit visit = mapperFactory.getVisitMapper().create();
                client.addVisit(visit);
                editClient.setClient(client).selectVisit(visit).show();
            }
            public void edit(Client client, Visit visit) {
                editClient.setClient(client).selectVisit(visit).show();
            }
            public void remove(Visit visit) {
                if (confirm("Удалить визит?")) {
                    try {
                        mapperFactory.getVisitMapper().remove(visit);
                    } catch (SQLException e) {}
                    updateVisits();
                }
            }
        });
        visitsSearchParams.addListener(new SimpleListener() {
            public void changed() {
                updateVisits();
            }
        });
    }

    private static void createClientsList() {
        clientsList = new view.client.List();
        clientsList.setSearchParams(clientsSearchParams);
        clientsList.setHandler(new view.client.ListHandler() {
            public void search(String search, Date created) {
                try {
                    clientsList.setClients(mapperFactory.getClientMapper().findAll());
                } catch (SQLException e) {}
            }
            public void add() {
                Client client = mapperFactory.getClientMapper().create();
                editClient.setClient(client).show();
            }
            public void edit(Client client) {
                editClient.setClient(client).show();
            }
            public void remove(Client client) {
                if (confirm("Удалить пациента?")) {
                    try {
                        mapperFactory.getClientMapper().remove(client);
                    } catch (SQLException e) {}
                    updateClients();
                    updateVisits();
                }
            }
        });
        clientsSearchParams.addListener(new SimpleListener() {
            public void changed() {
                updateClients();
            }
        });
    }

    private static void createDiagnosesList() {
        diagnosesList = new view.diagnosis.List();
        diagnosesList.setHandler(new view.diagnosis.ListHandler() {
            public void add() {
                editDiagnosis.setDiagnosis(mapperFactory.getDiagnosisMapper().create()).show();
            }
            public void edit(Diagnosis diagnosis) {
                editDiagnosis.setDiagnosis(diagnosis).show();
            }
            public void remove(Diagnosis diagnosis) {
                if (confirm("Удалить запись?")) {
                    try {
                        mapperFactory.getDiagnosisMapper().remove(diagnosis);
                    } catch (SQLException e) {}
                    updateVisits();
                    updateDiagnoses();
                }
            }
        });
    }

    private static void createEditClient() {
        editClient = new view.client.Edit(mainframe.getFrame());
        editClient.setActionHandler(new view.client.EditHandler() {
            public void addVisit(Client client) {
                Visit visit = mapperFactory.getVisitMapper().create();
                client.addVisit(visit);
                editClient.setClient(client).selectVisit(visit);
            }
            public void removeVisit(Client client, Visit visit) {
                client.removeVisit(visit);
                editClient.setClient(client).deselectVisit();
            }
            public void submit(Client client) {
                try {
                    mapperFactory.getClientMapper().persist(client);
                } catch (SQLException e) {}
                editClient.hide();
                updateClients();
                updateVisits();
            }
        });
    }

    private static void createEditDiagnosis() {
        editDiagnosis = new view.diagnosis.Edit(mainframe.getFrame());
        editDiagnosis.setHandler(new view.diagnosis.EditHandler() {
            public void submit(Diagnosis diagnosis) {
                try {
                    mapperFactory.getDiagnosisMapper().persist(diagnosis);
                } catch (SQLException e) {}
                editDiagnosis.hide();
                updateVisits();
                updateDiagnoses();
            }
        });
    }

    private static void updateClients() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    clientsList.setClients(mapperFactory.getClientMapper().search(clientsSearchParams));
                } catch (SQLException e) {}
            }
        });
    }

    private static void updateVisits() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    visitsList.setVisits(mapperFactory.getVisitMapper().search(visitsSearchParams));
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
            }
        });
    }

    private static void updateDiagnoses() {
        try {
            ArrayList<Diagnosis> diagnoses = mapperFactory.getDiagnosisMapper().findAll();
            diagnosesList.setDiagnoses(diagnoses);
            editClient.setDiagnoses(diagnoses);
        } catch (SQLException e) {}
    }

    private static boolean confirm(String msg) {
        return JOptionPane.showConfirmDialog(null, msg) == JOptionPane.YES_OPTION;
    }
}
