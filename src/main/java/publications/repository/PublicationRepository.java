package publications.repository;

import common.PublicationType;
import common.ObjectNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import publications.dto.PublicationGetDTO;
import publications.model.Publication;

@Repository
public class PublicationRepository {
    private final Connection connection;
    private static final Logger logger = LoggerFactory.getLogger(PublicationRepository.class);
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String PUBLICATION_TYPE = "publication_type";
    private static final String THEME = "theme";
    private static final String PUBLICATION_ID = "publication_id";
    private static final String PUBLICATION_JOURNALISTS = "publication_journalists";
    private static final String PUBLICATION_EDITORS = "publication_editors";
    private static final String PUBLICATION_NOT_FOUND_MSG = "Publication not found";
    private static final String FAILED_CREATING_MSG = "Creating publication failed, no ID obtained.";
    private static final String FAILED_TO_ADD_MSG = "Failed to add publication";
    private static final String FAILED_TO_GET_MSG = "Failed to get";
    private static final String FAILED_TO_DELETE_MSG = "Failed to delete";
    private static final String FAILED_TO_CHECK_MESSAGE = "Failed to check if publication exists";
    private static final String FAILED_TO_UPDATE = "Error updating publication";
    private static final String FAILED_TO_GET_EMP_ID = "Failed to get employee IDs from table: ";
    private static final String FAILED_TO_GET_CATEGORIES = "Failed to get categories";
    private static final String SQL_INSERT_PUBLICATION =
            "INSERT INTO publications (name, publication_type, theme) VALUES (?, ?, ?)";
    private static final String INSERT_PUB_CATEGORY =
            "INSERT INTO publication_categories (publication_id, category_id) VALUES (?, ?)";
    private static final String INSERT_JOURNALIST =
            "INSERT INTO publication_journalists (publication_id, employee_id) VALUES (?, ?)";
    private static final String INSERT_EDITOR =
            "INSERT INTO publication_editors (publication_id, employee_id) VALUES (?, ?)";
    private static final String UPDATE_PUBLICATION =
            "UPDATE publications SET name=?, publication_type=?, theme=? WHERE id=?";
    private static final String DELETE_CATEGORY =
            "DELETE FROM publication_categories WHERE publication_id = ?";
    private static final String DELETE_JOURNALIST =
            "DELETE FROM publication_journalists WHERE publication_id = ?";
    private static final String DELETE_EDITOR =
            "DELETE FROM publication_editors WHERE publication_id = ?";
    private static final String SQL_DELETE = "DELETE FROM publications WHERE id=?";
    private static final String SQL_GET_PUBLICATION = "SELECT id, name, publication_type, theme FROM publications";
    private static final String SQL_GET_ALL_CATEGORIES = """
            SELECT publication_id, c.name
            FROM publication_categories pc
            JOIN categories c ON pc.category_id = c.id;
            """;
    private static final String SQL_EXIST = "SELECT EXISTS(SELECT 1 FROM publications WHERE id = ?)";
    private static final String SQL_GET_EMPLOYEE = """
            SELECT e.id
            FROM employees e
            JOIN %s p ON e.id = p.employee_id WHERE p.publication_id = ?
            """;
    private static final String SQL_GET_CATEGORIES_BY_PUBLICATION = """
            SELECT c.id
            FROM categories c
            JOIN publication_categories pc ON c.id = pc.category_id WHERE pc.publication_id = ?
            """;
    private static final String SQL_GET_BY_ID_PUBLICATION =
            "SELECT id, name, publication_type, theme FROM publications WHERE id = ?";

    public PublicationRepository(Connection connection) {
        this.connection = connection;
    }

    public void add(Publication publication) {
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_PUBLICATION,
                    Statement.RETURN_GENERATED_KEYS
            )) {
                statement.setString(1, publication.name());
                statement.setString(2, publication.publicationType().toString());
                statement.setString(3, publication.theme());
                statement.executeUpdate();
                int publicationId;
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        publicationId = generatedKeys.getInt(1);
                    } else {
                        throw new RuntimeException(FAILED_CREATING_MSG);
                    }
                }
                insertConnections(INSERT_PUB_CATEGORY, publicationId, publication.categories());
                insertConnections(INSERT_JOURNALIST, publicationId, publication.journalists());
                insertConnections(INSERT_EDITOR, publicationId, publication.editors());
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(FAILED_TO_ADD_MSG, e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_TO_ADD_MSG, e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.error("Error during recovery of autoCommit", e);
            }
        }
    }

    public Publication get(int id) {
        try {
            try (PreparedStatement ps = connection.prepareStatement(SQL_GET_BY_ID_PUBLICATION)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String name = rs.getString(NAME);
                        PublicationType type = PublicationType.valueOf(
                                rs.getString(PUBLICATION_TYPE).toUpperCase());
                        String theme = rs.getString(THEME);
                        Set<Integer> categories = getCategories(id);
                        Set<Integer> journalists = getEmployeeIdsByPublicationId(id,
                                PUBLICATION_JOURNALISTS);
                        Set<Integer> editors = getEmployeeIdsByPublicationId(id,
                                PUBLICATION_EDITORS);
                        return new Publication(
                                id,
                                name,
                                type,
                                theme,
                                categories,
                                journalists,
                                editors
                        );
                    } else {
                        throw new ObjectNotFoundException(PUBLICATION_NOT_FOUND_MSG);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_TO_GET_MSG, e);
        }
    }

    public void update(Publication publication) {
        try {
            try (PreparedStatement updateStmt = connection.prepareStatement(UPDATE_PUBLICATION)) {
                updateStmt.setString(1, publication.name());
                updateStmt.setString(2, publication.publicationType().toString());
                updateStmt.setString(3, publication.theme());
                updateStmt.setInt(4, publication.id());
                updateStmt.executeUpdate();
            }
            int publicationId = publication.id();
            deleteConnections(DELETE_CATEGORY, publicationId);
            deleteConnections(DELETE_JOURNALIST, publicationId);
            deleteConnections(DELETE_EDITOR, publicationId);
            insertConnections(INSERT_PUB_CATEGORY, publicationId, publication.categories());
            insertConnections(INSERT_JOURNALIST, publicationId, publication.journalists());
            insertConnections(INSERT_EDITOR, publicationId, publication.editors());
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_TO_UPDATE, e);
        }
    }

    public void delete(int id) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_TO_DELETE_MSG, e);
        }
    }

    public List<PublicationGetDTO> getAll() {
        List<PublicationGetDTO> publications = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_GET_PUBLICATION);
                ResultSet resultSet = statement.executeQuery()
        ) {
            Map<Integer, List<String>> categoriesMap = getCategoriesMap();
            while (resultSet.next()) {
                int id = resultSet.getInt(ID);
                String name = resultSet.getString(NAME);
                PublicationType type = PublicationType.valueOf(
                        resultSet.getString(PUBLICATION_TYPE).toUpperCase());
                String theme = resultSet.getString(THEME);
                List<String> categories = categoriesMap.get(id);
                publications.add(new PublicationGetDTO(id, name, type, theme, categories));
            }
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_TO_GET_MSG, e);
        }
        return publications;
    }

    public boolean exists(int id) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_EXIST)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean(1);
                }
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_TO_CHECK_MESSAGE, e);
        }
    }

    private void deleteConnections(String query, int publicationId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, publicationId);
            statement.executeUpdate();
        }
    }

    private void insertConnections(String query, int publicationId, Set<Integer> ids)
            throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (Integer id : ids) {
                statement.setInt(1, publicationId);
                statement.setInt(2, id);
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private Set<Integer> getCategories(int publicationId) {
        Set<Integer> categories = new HashSet<>();
        try (PreparedStatement statement = connection.prepareStatement(
                SQL_GET_CATEGORIES_BY_PUBLICATION)) {
            statement.setInt(1, publicationId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    categories.add(rs.getInt(ID));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_TO_GET_CATEGORIES, e);
        }
        return categories;
    }

    private Map<Integer, List<String>> getCategoriesMap() throws SQLException {
        Map<Integer, List<String>> categoriesMap = new HashMap<>();
        try (PreparedStatement psCat = connection.prepareStatement(SQL_GET_ALL_CATEGORIES);
                ResultSet rsCat = psCat.executeQuery()) {
            while (rsCat.next()) {
                int pubId = rsCat.getInt(PUBLICATION_ID);
                String catName = rsCat.getString(NAME);
                if (categoriesMap.containsKey(pubId)) {
                    categoriesMap.get(pubId).add(catName);
                } else {
                    List<String> categoryNames = new ArrayList<>();
                    categoryNames.add(catName);
                    categoriesMap.put(pubId, categoryNames);
                }
            }
        }
        return categoriesMap;
    }

    private Set<Integer> getEmployeeIdsByPublicationId(int publicationId, String tableName) {
        Set<Integer> employees = new HashSet<>();
        String sql = String.format(SQL_GET_EMPLOYEE, tableName);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, publicationId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    employees.add(rs.getInt(ID));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_TO_GET_EMP_ID + tableName, e);
        }
        return employees;
    }
}