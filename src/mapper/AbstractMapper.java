package mapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Date;

abstract class AbstractMapper {
    private Connection dbh;
    private MapperFactory mapperFactory;
    private String fields;
    private HashMap cache = new HashMap();

    public void setConnection(Connection value) {
        dbh = value;
    }

    public Connection getConnection() {
        return dbh;
    }

    public void setMapperFactory(MapperFactory value) {
        mapperFactory = value;
    }

    public MapperFactory getMapperFactory() {
        return mapperFactory;
    }

    abstract protected String getTable();
    abstract protected String[] getFieldsArray();
    abstract protected String getFindSql();
    abstract protected String getFindAllSql();
    abstract protected Object doLoad(int id, ResultSet rs) throws SQLException;

    protected String getFields() {
        if (fields == null) {
            String[] array = getFieldsArray();
            fields = "";
            for (int i = 0; i < array.length; i++) {
                fields += getTable() + "." + array[i] + " AS " + getTable() + "_" + array[i];
                if (i < array.length - 1) {
                    fields += ", ";
                }
            }
        }
        return fields;
    }

    protected Object load(ResultSet rs) throws SQLException {
        int id = rs.getInt(getTable() + "_id");
        if (cache.containsKey(id)) {
            return cache.get(id);
        }
        Object result = doLoad(id, rs);
        cache.put(id, result);
        return result;
    }

    protected ArrayList loadAll(ResultSet rs) throws SQLException {
        ArrayList result = new ArrayList();
        while (rs.next()) {
            result.add(load(rs));
        }
        return result;
    }

    protected Object abstractFind(int id) throws SQLException {
        Object result = cache.get(id);
        if (result != null) {
            return result;
        }
        PreparedStatement stmt = dbh.prepareStatement(getFindSql());
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        result = load(rs);
        return result;
    }

    protected ArrayList abstractFindAll() throws SQLException {
        return loadAll(dbh.prepareStatement(getFindAllSql()).executeQuery());
    }

    protected int getGeneratedId(PreparedStatement stmt) throws SQLException {
        ResultSet rs = stmt.getGeneratedKeys();
        rs.next();
        return rs.getInt(1);
    }

    protected void setWhereArgs(PreparedStatement stmt, int offset, Iterator<Object> iterator) throws SQLException {
        while (iterator.hasNext()) {
            Object arg = iterator.next();
            if (arg instanceof String) {
                stmt.setString(offset++, (String) arg);
            } else if (arg instanceof Integer) {
                stmt.setInt(offset++, (Integer) arg);
            } else if (arg instanceof Date) {
                stmt.setTimestamp(offset++, new Timestamp(((Date) arg).getTime()));
            }
        }
    }
}
