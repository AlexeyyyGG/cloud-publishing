package com.cloud.publishing.backend.repository;

import com.cloud.publishing.model.publication.Category;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
    private final DataSource dataSource;
    public static final String FAILED_TO_LOAD_CATEGORIES = "Не удалось загрузить категории";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String SQL_GET_ALL = "SELECT id, name FROM categories";

    public CategoryRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Category> getAll() {
        List<Category> categories = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SQL_GET_ALL)) {
            while (resultSet.next()) {
                categories.add(new Category(
                        resultSet.getInt(ID),
                        resultSet.getString(NAME)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_TO_LOAD_CATEGORIES, e);
        }
        return categories;
    }
}