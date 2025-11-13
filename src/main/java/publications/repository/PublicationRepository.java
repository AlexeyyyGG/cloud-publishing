package publications.repository;

import common.PublicationType;
import common.ObjectNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import publications.dto.PublicationGetDTO;
import publications.model.Publication;

@Repository
public class PublicationRepository {
    private final Connection connection;
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String PUBLICATION_TYPE = "publication_type";
    private static final String THEME = "theme";
    private static final String CATEGORIES = "categories";
    private static final String JOURNALISTS = "journalists";
    private static final String EDITORS = "editors";
    private static final String PUBLICATION_NOT_FOUND_MSG = "Publication not found";
    private static final String FAILED_CREATING_MSG = "Creating publication failed, no ID obtained.";
    private static final String FAILED_TO_ADD_MSG = "Failed to add publication";
    private static final String FAILED_TO_GET_MSG = "Failed to get";
    private static final String FAILED_TO_DELETE_MSG = "Failed to delete";
    private static final String FAILED_TO_CHECK_MESSAGE = "Failed to check if publication exists";
    private static final String FAILED_TO_UPDATE = "Error updating publication";
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
    public static final String SQL_DELETE = "DELETE FROM publications WHERE id=?";
    private static final String SQL_GET = """
            SELECT p.id, p.name, p.publication_type, p.theme,
                GROUP_CONCAT(DISTINCT c.id) AS categories,
                GROUP_CONCAT(DISTINCT j.id) AS journalists,
                GROUP_CONCAT(DISTINCT e.id) AS editors
            FROM publications p
            LEFT JOIN publication_categories pc ON p.id = pc.publication_id
            LEFT JOIN categories c ON pc.category_id = c.id
            LEFT JOIN publication_journalists p_j ON p.id = p_j.publication_id
            LEFT JOIN employees j ON p_j.employee_id = j.id
            LEFT JOIN publication_editors p_e ON p.id = p_e.publication_id
            LEFT JOIN employees e ON p_e.employee_id = e.id
            WHERE p.id = ?
            GROUP BY p.id, p.name, p.publication_type, p.theme;
            """;
    public static final String SQL_GET_ALL = """
            SELECT
                p.id,
                p.name,
                p.publication_type,
                p.theme,
                GROUP_CONCAT(c.name) AS categories
            FROM publications p
            LEFT JOIN publication_categories pc ON p.id = pc.publication_id
            LEFT JOIN categories c ON pc.category_id = c.id
            GROUP BY p.id, p.name, p.publication_type, p.theme;
            """;
    public static final String SQL_EXIST = "SELECT EXISTS(SELECT 1 FROM publications WHERE id = ?)";

    public PublicationRepository(Connection connection) {
        this.connection = connection;
    }

    public void add(Publication publication) {
        try (PreparedStatement statement = connection.prepareStatement(
                SQL_INSERT_PUBLICATION,
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
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_TO_ADD_MSG, e);
        }
    }

    public Publication get(int id) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_GET)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSetToPublication(resultSet);
                } else {
                    throw new ObjectNotFoundException(PUBLICATION_NOT_FOUND_MSG);
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
        List<PublicationGetDTO> publication = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                publication.add(resultSetToPublicationGetDTO(resultSet));
            }
            return publication;
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_TO_GET_MSG, e);
        }
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

    private Publication resultSetToPublication(ResultSet resultSet) throws SQLException {
        List<Integer> categories = parserInteger(resultSet.getString(CATEGORIES));
        List<Integer> journalists = parserInteger(resultSet.getString(JOURNALISTS));
        List<Integer> editors = parserInteger(resultSet.getString(EDITORS));
        return new Publication(
                resultSet.getInt(ID),
                resultSet.getString(NAME),
                PublicationType.valueOf(resultSet.getString(PUBLICATION_TYPE).toUpperCase()),
                resultSet.getString(THEME),
                categories,
                journalists,
                editors
        );
    }

    private List<Integer> parserInteger(String str) {
        if (str != null && !str.isEmpty()) {
            return Arrays.stream(str.split(","))
                    .map(string -> string.trim())
                    .map(string -> Integer.parseInt(string))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private List<String> parserString(String str) {
        if (str != null && !str.isEmpty()) {
            return Arrays.stream(str.split(","))
                    .map(string -> string.trim())
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private PublicationGetDTO resultSetToPublicationGetDTO(ResultSet resultSet)
            throws SQLException {
        List<String> categories = parserString(resultSet.getString(CATEGORIES));
        return new PublicationGetDTO(
                resultSet.getInt(ID),
                resultSet.getString(NAME),
                PublicationType.valueOf(resultSet.getString(PUBLICATION_TYPE).toUpperCase()),
                resultSet.getString(THEME),
                categories
        );
    }

    private void deleteConnections(String query, int publicationId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, publicationId);
            statement.executeUpdate();
        }
    }

    private void insertConnections(String query, int publicationId, List<Integer> ids)
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
}