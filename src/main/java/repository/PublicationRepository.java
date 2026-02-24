package repository;

import static constants.publication.PublicationField.*;
import static constants.publication.PublicationMessage.*;
import static constants.publication.PublicationSQL.*;

import javax.sql.DataSource;
import model.PublicationType;
import exception.ObjectNotFoundException;
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
import org.springframework.stereotype.Repository;
import dto.PublicationGetDTO;
import model.Publication;

@Repository
public class PublicationRepository extends BaseRepository implements
        IRepository<Publication, Integer> {
    public PublicationRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void add(Publication publication) {
        doTransactional((connection) -> {
            int publicationId = insertPublication(publication);
            insertConnections(INSERT_PUB_CATEGORY, publicationId, publication.categories());
            insertConnections(INSERT_JOURNALIST, publicationId, publication.journalists());
            insertConnections(INSERT_EDITOR, publicationId, publication.editors());
        }, FAILED_TO_ADD_MSG);
    }

    @Override
    public Publication get(Integer id) {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        SQL_GET_BY_ID_PUBLICATION)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString(NAME);
                    PublicationType type = PublicationType.valueOf(
                            resultSet.getString(PUBLICATION_TYPE).toUpperCase());
                    String theme = resultSet.getString(THEME);
                    Set<Integer> categories = getCategories(id);
                    Set<Integer> journalists = getEmployeeIdsByPublicationId(id,
                            PUBLICATION_JOURNALISTS);
                    Set<Integer> editors = getEmployeeIdsByPublicationId(id, PUBLICATION_EDITORS);
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
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_TO_GET_MSG, e);
        }
    }

    @Override
    public void update(Publication publication) {
        doTransactional((connection) -> {
            int publicationId = updatePublication(publication);
            deleteConnections(DELETE_CATEGORY, publicationId);
            deleteConnections(DELETE_JOURNALIST, publicationId);
            deleteConnections(DELETE_EDITOR, publicationId);
            insertConnections(INSERT_PUB_CATEGORY, publicationId, publication.categories());
            insertConnections(INSERT_JOURNALIST, publicationId, publication.journalists());
            insertConnections(INSERT_EDITOR, publicationId, publication.editors());
        }, FAILED_TO_UPDATE);
    }

    @Override
    public void delete(Integer publicationId) {
        doTransactional((connection) -> {
            deleteConnections(DELETE_CATEGORY, publicationId);
            deleteConnections(DELETE_JOURNALIST, publicationId);
            deleteConnections(DELETE_EDITOR, publicationId);
            deleteConnections(SQL_DELETE, publicationId);
        }, FAILED_TO_DELETE_MSG);
    }

    public List<PublicationGetDTO> getAll() {
        List<PublicationGetDTO> publications = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_GET_PUBLICATION);
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

    @Override
    public boolean exists(Integer id) {
        return super.exists(id, SQL_EXIST, FAILED_TO_CHECK_MESSAGE);
    }

    private void deleteConnections(String query, int publicationId) throws SQLException {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, publicationId);
            statement.executeUpdate();
        }
    }

    private void insertConnections(String query, int publicationId, Set<Integer> ids)
            throws SQLException {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
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
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        SQL_GET_CATEGORIES_BY_PUBLICATION)) {
            statement.setInt(1, publicationId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    categories.add(resultSet.getInt(ID));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_TO_GET_CATEGORIES, e);
        }
        return categories;
    }

    private Map<Integer, List<String>> getCategoriesMap() throws SQLException {
        Map<Integer, List<String>> categoriesMap = new HashMap<>();
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL_CATEGORIES);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int pubId = resultSet.getInt(PUBLICATION_ID);
                String catName = resultSet.getString(NAME);
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
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, publicationId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    employees.add(resultSet.getInt(ID));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_TO_GET_EMP_ID + tableName, e);
        }
        return employees;
    }

    private Integer updatePublication(Publication publication) throws SQLException {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement updateStmt = connection.prepareStatement(UPDATE_PUBLICATION)) {
            updateStmt.setString(1, publication.name());
            updateStmt.setString(2, publication.publicationType().toString());
            updateStmt.setString(3, publication.theme());
            updateStmt.setInt(4, publication.id());
            updateStmt.executeUpdate();
        }
        return publication.id();
    }

    private Integer insertPublication(Publication publication) throws SQLException {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        SQL_INSERT_PUBLICATION,
                        Statement.RETURN_GENERATED_KEYS
                )) {
            statement.setString(1, publication.name());
            statement.setString(2, publication.publicationType().toString());
            statement.setString(3, publication.theme());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new RuntimeException(FAILED_CREATING_MSG);
                }
            }
        }
    }
}