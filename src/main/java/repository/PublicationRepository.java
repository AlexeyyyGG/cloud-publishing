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
import dto.response.PublicationGetDTO;
import model.Publication;

@Repository
public class PublicationRepository extends BaseRepository implements
        IRepository<Publication, Integer> {
    public PublicationRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Publication add(Publication publication) {
        return doTransactional((connection) -> {
            int publicationId = insertPublication(connection, publication);
            try (PreparedStatement stmtCategory = prepareInsertConnections(
                    connection,
                    INSERT_PUB_CATEGORY,
                    publicationId,
                    publication.categories()
            );
                    PreparedStatement stmtJournalist = prepareInsertConnections(
                            connection,
                            INSERT_JOURNALIST,
                            publicationId,
                            publication.journalists()
                    );
                    PreparedStatement stmtEditor = prepareInsertConnections(
                            connection,
                            INSERT_EDITOR,
                            publicationId,
                            publication.editors()
                    )
            ) {
                stmtCategory.executeBatch();
                stmtJournalist.executeBatch();
                stmtEditor.executeBatch();
            }
            return new Publication(
                    publicationId,
                    publication.name(),
                    publication.publicationType(),
                    publication.theme(),
                    publication.categories(),
                    publication.journalists(),
                    publication.editors()
            );
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
                    Set<Integer> categories = getCategories(connection, id);
                    Set<Integer> journalists = getEmployeeIdsByPublicationId(
                            connection,
                            id,
                            PUBLICATION_JOURNALISTS
                    );
                    Set<Integer> editors = getEmployeeIdsByPublicationId(
                            connection,
                            id,
                            PUBLICATION_EDITORS
                    );
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
            int publicationId = updatePublication(connection, publication);
            try (PreparedStatement stmtDeleteCategory = prepareDeleteConnections(
                    connection,
                    DELETE_CATEGORY,
                    publicationId
            );
                    PreparedStatement stmtDeleteJournalist = prepareDeleteConnections(
                            connection,
                            DELETE_JOURNALIST,
                            publicationId
                    );
                    PreparedStatement stmtDeleteEditor = prepareDeleteConnections(
                            connection,
                            DELETE_EDITOR,
                            publicationId
                    );
                    PreparedStatement stmtInsertCategory = prepareInsertConnections(
                            connection,
                            INSERT_PUB_CATEGORY,
                            publicationId,
                            publication.categories()
                    );
                    PreparedStatement stmtInsertJournalist = prepareInsertConnections(
                            connection,
                            INSERT_JOURNALIST,
                            publicationId,
                            publication.journalists()
                    );
                    PreparedStatement stmtInsertEditor = prepareInsertConnections(
                            connection,
                            INSERT_EDITOR,
                            publicationId,
                            publication.editors()
                    )
            ) {
                stmtDeleteCategory.executeUpdate();
                stmtDeleteJournalist.executeUpdate();
                stmtDeleteEditor.executeUpdate();
                stmtInsertCategory.executeBatch();
                stmtInsertJournalist.executeBatch();
                stmtInsertEditor.executeBatch();
            }
        }, FAILED_TO_UPDATE);
    }

    @Override
    public void delete(Integer publicationId) {
        doTransactional((connection) -> {
            try (PreparedStatement stmtDeleteCategory = prepareDeleteConnections(
                    connection,
                    DELETE_CATEGORY,
                    publicationId
            );
                    PreparedStatement stmtDeleteJournalist = prepareDeleteConnections(
                            connection,
                            DELETE_JOURNALIST,
                            publicationId
                    );
                    PreparedStatement stmtDeleteEditor = prepareDeleteConnections(
                            connection,
                            DELETE_EDITOR,
                            publicationId
                    );
                    PreparedStatement stmtDeletePublication = prepareDeleteConnections(
                            connection,
                            SQL_DELETE,
                            publicationId
                    )
            ) {
                stmtDeleteCategory.executeUpdate();
                stmtDeleteJournalist.executeUpdate();
                stmtDeleteEditor.executeUpdate();
                stmtDeletePublication.executeUpdate();
            }
        }, FAILED_TO_DELETE_MSG);
    }

    public List<PublicationGetDTO> getAll() {
        List<PublicationGetDTO> publications = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_GET_PUBLICATION);
                ResultSet resultSet = statement.executeQuery()
        ) {
            Map<Integer, List<String>> categoriesMap = getCategoriesMap(connection);
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

    private PreparedStatement prepareDeleteConnections(
            Connection connection,
            String query,
            int publicationId
    ) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, publicationId);
        return statement;
    }

    private PreparedStatement prepareInsertConnections(
            Connection connection,
            String query,
            int publicationId,
            Set<Integer> ids
    ) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        for (Integer id : ids) {
            statement.setInt(1, publicationId);
            statement.setInt(2, id);
            statement.addBatch();
        }
        return statement;
    }

    private Set<Integer> getCategories(Connection connection, int publicationId) {
        Set<Integer> categories = new HashSet<>();
        try (PreparedStatement statement = connection.prepareStatement(
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

    private Map<Integer, List<String>> getCategoriesMap(Connection connection) throws SQLException {
        Map<Integer, List<String>> categoriesMap = new HashMap<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL_CATEGORIES);
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

    private Set<Integer> getEmployeeIdsByPublicationId(
            Connection connection,
            int publicationId,
            String tableName
    ) {
        Set<Integer> employees = new HashSet<>();
        String sql = String.format(SQL_GET_EMPLOYEE, tableName);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
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

    private Integer updatePublication(
            Connection connection,
            Publication publication
    ) throws SQLException {
        try (PreparedStatement updateStmt = connection.prepareStatement(UPDATE_PUBLICATION)) {
            updateStmt.setString(1, publication.name());
            updateStmt.setString(2, publication.publicationType().toString());
            updateStmt.setString(3, publication.theme());
            updateStmt.setInt(4, publication.id());
            updateStmt.executeUpdate();
        }
        return publication.id();
    }

    private Integer insertPublication(
            Connection connection,
            Publication publication
    ) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
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