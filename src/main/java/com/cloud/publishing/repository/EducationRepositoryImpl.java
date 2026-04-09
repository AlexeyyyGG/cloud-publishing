package com.cloud.publishing.repository;

import static com.cloud.publishing.constants.EducationConstants.*;

import com.cloud.publishing.model.Education;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;

@Repository
public class EducationRepositoryImpl implements EducationRepository {
    private final DataSource dataSource;

    public EducationRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Education get(int id) {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_GET_EDUCATIONS)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSetToEducation(resultSet);
                } else {
                    throw new RuntimeException(EDUCATION_NOT_FOUND);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Education> getAll() {
        List<Education> educations = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                educations.add(resultSetToEducation(resultSet));
            }
            return educations;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Education resultSetToEducation(ResultSet resultSet) throws SQLException {
        return new Education(
                resultSet.getInt(EDUCATION_ID),
                resultSet.getString(EDUCATION_NAME),
                resultSet.getString(EDUCATION_LABEL)
        );
    }
}