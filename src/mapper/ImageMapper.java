package mapper;

import java.sql.ResultSet;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import model.Image;
import model.ValueHolder;
import model.ValueLoader;

public class ImageMapper extends AbstractMapper {
    private String selectContentSql = "SELECT content FROM " + getTable() + " WHERE id = ?";
    private String insertSql = "INSERT INTO " + getTable() + " (name, size, content) VALUES (?, ?, ?)";
    private String updateSql = "UPDATE " + getTable() + " SET name = ?, size = ?, content = ? WHERE id = ?";
    private String removeSql = "DELETE FROM " + getTable() + " WHERE id = ?";

    protected String getTable() {
        return "image";
    }

    protected String[] getFieldsArray() {
        return new String[] { "id", "name", "size" };
    }

    protected String getFindSql() {
        return "SELECT " + getFields() + " FROM " + getTable() + " WHERE id = ?";
    }

    protected String getFindAllSql() {
        return "SELECT " + getFields() + " FROM " + getTable();
    }

    protected String getFindAllByVisitSql() {
        return "";
    }

    public Image create() {
        return new Image();
    }

    public Object doLoad(int id, ResultSet rs) throws SQLException {
        Image image = create();
        image.setId(id);
        image.setName(rs.getString(getTable() + "_name"));
        image.setSize(rs.getInt(getTable() + "_size"));
        image.setContent(new ValueHolder(new ContentLoader(id)));
        return image;
    }

    private int fillStatement(PreparedStatement stmt, Image image) throws SQLException {
        stmt.setString(1, image.getName());
        stmt.setInt(2, image.getSize());
        stmt.setBytes(3, image.getContent());
        return 4;
    }

    public void insert(Image image) throws SQLException {
        PreparedStatement stmt = getConnection().prepareStatement(insertSql);
        fillStatement(stmt, image);
        stmt.executeUpdate();
        image.setId(getGeneratedId(stmt));
    }

    public void update(Image image) throws SQLException {
        PreparedStatement stmt = getConnection().prepareStatement(updateSql);
        int offset = fillStatement(stmt, image);
        stmt.setInt(offset, image.getId());
        stmt.executeUpdate();
    }

    public void persist(Image image) throws SQLException {
        if (image.getId() == 0) {
            insert(image);
        } else {
            update(image);
        }
    }

    public void remove(Image image) throws SQLException {
        PreparedStatement stmt = getConnection().prepareStatement(removeSql);
        stmt.setInt(1, image.getId());
        stmt.executeUpdate();
    }

    public Image find(int id) throws SQLException {
        return (Image) abstractFind(id);
    }

    public ArrayList<Image> findAllByVisit(int visitId) throws SQLException {
        ArrayList<Image> images = new ArrayList<Image>();
        PreparedStatement stmt = getConnection().prepareStatement(
            "SELECT " + getFields() + " FROM " + getTable() + " " +
            "INNER JOIN visitImage vi ON vi.imageId = image.id " +
            "INNER JOIN visit v ON vi.visitId = v.id " +
            "WHERE v.id = ?");
        stmt.setInt(1, visitId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            images.add((Image) load(rs));
        }
        return images;
    }

    public class ContentLoader implements ValueLoader {
        private int id;

        public ContentLoader(int id) {
            this.id = id;
        }

        public Object load() {
            try {
                PreparedStatement stmt = getConnection().prepareStatement(selectContentSql);
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                rs.next();
                return rs.getBytes(1);
            } catch (SQLException e) {

            }
            return null;
        }
    }
}
