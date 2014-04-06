package mapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.ArrayList;

import model.Diagnosis;

public class DiagnosisMapper extends AbstractMapper {
    private String inserSql = "INSERT INTO diagnosis (name, template) VALUES (?, ?)";
    private String updateSql = "UPDATE diagnosis SET name = ?, template = ? WHERE id = ?";
    private String removeSql = "DELETE FROM diagnosis WHERE id = ?";

    protected String getTable() {
        return "diagnosis";
    }

    protected String[] getFieldsArray() {
        return new String[] { "id", "name", "template" };
    }

    protected String getFindSql() {
        return "SELECT " + getFields() + " FROM diagnosis WHERE id = ?";
    }

    protected String getFindAllSql() {
        return "SELECT " + getFields() + " FROM diagnosis ORDER BY name";
    }

    public Diagnosis create() {
        return new Diagnosis();
    }
    public Diagnosis createNull() {
        return new model.Diagnosis.Null();
    }

    public Object doLoad(int id, ResultSet rs) throws SQLException {
        Diagnosis diagnosis = create();
        diagnosis.setId(id);
        diagnosis.setName(rs.getString(getTable() + "_name"));
        diagnosis.setTemplate(rs.getString(getTable() + "_template"));
        return diagnosis;
    }

    private void fillStatement(PreparedStatement stmt, Diagnosis diagnosis) throws SQLException {
        stmt.setString(1, diagnosis.getName());
        stmt.setString(2, diagnosis.getTemplate());
    }

    public void insert(Diagnosis diagnosis) throws SQLException {
        PreparedStatement stmt = getConnection().prepareStatement(inserSql);
        fillStatement(stmt, diagnosis);
        stmt.executeUpdate();
        diagnosis.setId(getGeneratedId(stmt));
    }

    public void update(Diagnosis diagnosis) throws SQLException {
        PreparedStatement stmt = getConnection().prepareStatement(updateSql);
        fillStatement(stmt, diagnosis);
        stmt.setInt(3, diagnosis.getId());
        stmt.executeUpdate();
    }

    public void persist(Diagnosis diagnosis) throws SQLException {
        if (diagnosis.getId() == 0) {
            insert(diagnosis);
        } else {
            update(diagnosis);
        }
    }

    public void remove(Diagnosis diagnosis) throws SQLException {
        PreparedStatement stmt = getConnection().prepareStatement(removeSql);
        stmt.setInt(1, diagnosis.getId());
        stmt.executeUpdate();
    }

    public Diagnosis find(int id) throws SQLException {
        return (Diagnosis) abstractFind(id);
    }

    public ArrayList<Diagnosis> findAll() throws SQLException {
        return (ArrayList<Diagnosis>) abstractFindAll();
    }
}
