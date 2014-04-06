package mapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;

import model.Visit;
import model.Client;
import model.Diagnosis;
import model.ValueHolder;
import model.ValueLoader;
import model.SearchParams;

public class VisitMapper extends AbstractMapper {
    private String inserSql = "INSERT INTO visit (clientId, diagnosisId, description, created) VALUES (?, ?, ?, ?)";
    private String updateSql = "UPDATE visit SET clientId = ?, diagnosisId = ?, description = ?, created = ? WHERE id = ?";
    private String removeSql = "DELETE FROM visit WHERE id = ?";

    protected String getTable() {
        return "visit";
    }

    protected String[] getFieldsArray() {
        return new String[] { "id", "description", "created" };
    }

    protected String getFields() {
        return super.getFields() + ", " +
            getMapperFactory().getClientMapper().getFields() + ", " +
            getMapperFactory().getDiagnosisMapper().getFields();
    }

    protected String getJoins() {
        return " LEFT JOIN client ON client.id = visit.clientId LEFT JOIN diagnosis ON diagnosis.id = visit.diagnosisId ";
    }

    protected String getFindSql() {
        return "SELECT " + getFields() + " FROM visit " + getJoins() + " WHERE visit.id = ?";
    }

    protected String getFindAllSql() {
        return getFindAllSql("");
    }

    protected String getFindAllSql(String where) {
        return "SELECT " + getFields() + " FROM visit " + getJoins() + " " + where + " ORDER BY visit.created DESC";
    }

    protected String getFindAllByClientSql() {
        return "SELECT " + getFields() + " FROM visit " + getJoins() + " WHERE clientId = ? ORDER BY visit.created DESC";
    }

    public Visit create() {
        Visit visit = new Visit();
        visit.setDiagnosis(getMapperFactory().getDiagnosisMapper().createNull());
        return visit;
    }

    public Object doLoad(int id, ResultSet rs) throws SQLException {
        Visit visit = create();
        visit.setId(id);
        visit.setDescription(rs.getString(getTable() + "_description"));
        visit.setCreated(rs.getTimestamp(getTable() + "_created"));
        visit.setClient((Client) getMapperFactory().getClientMapper().load(rs));
        Diagnosis diagnosis = (Diagnosis) getMapperFactory().getDiagnosisMapper().load(rs);
        if (diagnosis != null) {
            visit.setDiagnosis(diagnosis);
        }
        return visit;
    }

    private void fillStatement(PreparedStatement stmt, Visit visit) throws SQLException {
        stmt.setInt(1, visit.getClient().getId());
        stmt.setInt(2, visit.getDiagnosis().getId() > 0 ? visit.getDiagnosis().getId() : 0);
        stmt.setString(3, visit.getDescription());
        stmt.setTimestamp(4, new Timestamp(visit.getCreated().getTime()));
    }

    public void insert(Visit visit) throws SQLException {
        PreparedStatement stmt = getConnection().prepareStatement(inserSql);
        fillStatement(stmt, visit);
        stmt.executeUpdate();
        visit.setId(getGeneratedId(stmt));
    }

    public void update(Visit visit) throws SQLException {
        PreparedStatement stmt = getConnection().prepareStatement(updateSql);
        fillStatement(stmt, visit);
        stmt.setInt(5, visit.getId());
        stmt.executeUpdate();
    }

    public void persist(Visit visit) throws SQLException {
        if (visit.getId() == 0) {
            insert(visit);
        } else {
            update(visit);
        }
    }

    public void remove(Visit visit) throws SQLException {
        visit.getClient().removeVisit(visit);
        PreparedStatement stmt = getConnection().prepareStatement(removeSql);
        stmt.setInt(1, visit.getId());
        stmt.executeUpdate();
    }

    public void remove(ArrayList<Visit> visits) throws SQLException {
        if (visits != null) {
            Iterator<Visit> iterator = visits.iterator();
            while (iterator.hasNext()) {
                remove(iterator.next());
            }
        }
    }

    public Visit find(int id) throws SQLException {
        return (Visit) abstractFind(id);
    }

    public ArrayList<Visit> findAll() throws SQLException {
        return (ArrayList<Visit>) abstractFindAll();
    }

    public ArrayList<Visit> findAllByClient(int clientId) throws SQLException {
        PreparedStatement stmt = getConnection().prepareStatement(getFindAllByClientSql());
        stmt.setInt(1, clientId);
        ResultSet rs = stmt.executeQuery();
        ArrayList<Visit> result = new ArrayList<Visit>();
        while (rs.next()) {
            result.add((Visit) load(rs));
        }
        return result;
    }

    public ArrayList<Visit> search(SearchParams params) throws SQLException {
        ArrayList<String> where = new ArrayList<String>();
        ArrayList<Object> whereArgs = new ArrayList<Object>();
        if (params.getSearch().length() > 0) {
            String tbl = getMapperFactory().getClientMapper().getTable();
            where.add("(" + tbl + "_name LIKE ? OR " + tbl + "_address LIKE ? OR " + tbl + "_phone LIKE ?)");
            String arg = "%" + params.getSearch() + "%";
            whereArgs.add(arg);
            whereArgs.add(arg);
            whereArgs.add(arg);
        }
        if (params.getDate() != null) {
            where.add(getTable() + "_created > ?");
            whereArgs.add(params.getDateFrom());
            where.add(getTable() + "_created <= ?");
            whereArgs.add(params.getDateTo());
        }

        PreparedStatement stmt = getConnection().prepareStatement(
            getFindAllSql((!where.isEmpty() ? " WHERE " + StringUtils.join(where, " AND "): ""))
        );

        setWhereArgs(stmt, 1, whereArgs.iterator());
        ResultSet rs = stmt.executeQuery();
        ArrayList<Visit> result = new ArrayList<Visit>();
        while (rs.next()) {
            result.add((Visit) load(rs));
        }

        return result;
    }
}
