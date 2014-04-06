package mapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;

import model.Client;
import model.Visit;
import model.ValueHolder;
import model.ValueLoader;
import model.SearchParams;

public class ClientMapper extends AbstractMapper {
    private String insertSql = "INSERT INTO client (name, address, birthday, notes, phone, created) VALUES (?, ?, ?, ?, ?, ?)";
    private String updateSql = "UPDATE client SET name = ?, address = ?, birthday = ?, phone = ?, notes = ?, created = ? WHERE id = ?";
    private String removeSql = "DELETE FROM client WHERE id = ?";

    protected String getTable() {
        return "client";
    }

    protected String[] getFieldsArray() {
        return new String[] {"id", "name", "address", "birthday", "notes", "phone", "created" };
    }

    protected String getFindSql() {
        return "SELECT " + getFields() + " FROM client WHERE id = ?";
    }

    protected String getFindAllSql() {
        return getFindAllSql("");
    }

    protected String getFindAllSql(String where) {
        return "SELECT " + getFields() + " FROM client " + where + " ORDER BY created DESC";
    }

    public Client create() {
        return new Client();
    }

    protected Object doLoad(int id, ResultSet rs) throws SQLException {
        Client client = create();
        client.setId(id);
        client.setName(rs.getString(getTable() + "_name"));
        client.setAddress(rs.getString(getTable() + "_address"));
        client.setBirthday(rs.getString(getTable() + "_birthday"));
        client.setPhone(rs.getString(getTable() + "_phone"));
        client.setNotes(rs.getString(getTable() + "_notes"));
        client.setCreated(rs.getTimestamp(getTable() + "_created"));
        client.setVisits(new ValueHolder(new VisitsLoader(id)));
        return client;
    }

    public class VisitsLoader implements ValueLoader {
        private int id;
        public VisitsLoader(int id) {
            this.id = id;
        }
        public Object load() {
            try {
                return getMapperFactory().getVisitMapper().findAllByClient(id);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return null;
        }
    }

    public Client find(int id) throws SQLException {
        return (Client) abstractFind(id);
    }

    public ArrayList<Client> findAll() throws SQLException {
        return (ArrayList<Client>) abstractFindAll();
    }

    protected void persistVisits(Client client) throws SQLException {
        ArrayList<Visit> visits = client.getVisits();
        if (visits != null) {
            Iterator<Visit> iterator = visits.iterator();
            while (iterator.hasNext()) {
                Visit visit = iterator.next();
                visit.setClient(client);
                getMapperFactory().getVisitMapper().persist(visit);
            }
        }
    }

    private int fillStatement(PreparedStatement stmt, Client client) throws SQLException {
        stmt.setString(1, client.getName());
        stmt.setString(2, client.getAddress());
        stmt.setString(3, client.getBirthday());
        stmt.setString(4, client.getPhone());
        stmt.setString(5, client.getNotes());
        stmt.setTimestamp(6, new Timestamp(client.getCreated().getTime()));
        return 7;
    }

    public void insert(Client client) throws SQLException {
        PreparedStatement stmt = getConnection().prepareStatement(insertSql);
        fillStatement(stmt, client);
        stmt.executeUpdate();
        client.setId(getGeneratedId(stmt));
        persistVisits(client);
    }

    public void update(Client client) throws SQLException {
        PreparedStatement stmt = getConnection().prepareStatement(updateSql);
        int position = fillStatement(stmt, client);
        stmt.setInt(position, client.getId());
        stmt.executeUpdate();
        persistVisits(client);
    }

    public void persist(Client client) throws SQLException {
        if (client.getId() == 0) {
            insert(client);
        } else {
            update(client);
        }
    }

    public void remove(Client client) throws SQLException {
        getMapperFactory().getVisitMapper().remove(client.getVisits());
        PreparedStatement stmt = getConnection().prepareStatement(removeSql);
        stmt.setInt(1, client.getId());
        stmt.executeUpdate();
    }

    public ArrayList<Client> search(SearchParams params) throws SQLException {
        ArrayList<String> where = new ArrayList<String>();
        ArrayList<Object> whereArgs = new ArrayList<Object>();
        String tbl = getTable();
        if (params.getSearch().length() > 0) {
            where.add("(" + tbl + "_name LIKE ? OR " + tbl + "_address LIKE ? OR " + tbl + "_phone LIKE ?)");
            String arg = "%" + params.getSearch() + "%";
            whereArgs.add(arg);
            whereArgs.add(arg);
            whereArgs.add(arg);
        }
        if (params.getDate() != null) {
            where.add(tbl + "_created > ?");
            whereArgs.add(params.getDateFrom());
            where.add(tbl + "_created <= ?");
            whereArgs.add(params.getDateTo());
        }

        PreparedStatement stmt = getConnection().prepareStatement(
            getFindAllSql((!where.isEmpty() ? " WHERE " + StringUtils.join(where, " AND "): ""))
        );

        setWhereArgs(stmt, 1, whereArgs.iterator());
        ResultSet rs = stmt.executeQuery();
        ArrayList<Client> result = new ArrayList<Client>();
        while (rs.next()) {
            result.add((Client) load(rs));
        }

        return result;
    }
}
