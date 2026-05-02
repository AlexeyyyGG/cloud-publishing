package com.cloud.publishing.repository;

import com.cloud.publishing.model.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepositoryImpl extends BaseRepository implements CategoryRepository {
    public static final String FAILED_TO_LOAD_CATEGORIES = "Не удалось загрузить категории";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String SQL_GET_ALL = "SELECT id, name FROM categories";

    public CategoryRepositoryImpl(DataSource dataSource) {
        super(dataSource);
    }

    public List<Category> getAll() {
        List<Category> categories = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL);
                ResultSet resultSet = statement.executeQuery()) {
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