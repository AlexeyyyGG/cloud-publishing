package repository;

import static constants.employee.EmployeeField.*;
import static constants.employee.EmployeeMessage.*;
import static constants.employee.EmployeeSQL.*;

import exception.ObjectNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import model.Employee;
import model.Gender;
import model.Type;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepositoryImpl extends BaseRepository implements EmployeeRepository {
    public EmployeeRepositoryImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Employee add(Employee employee) {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        SQL_INSERT,
                        Statement.RETURN_GENERATED_KEYS
                )) {
            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setString(3, employee.getMiddleName());
            statement.setString(4, employee.getEmail());
            statement.setString(5, employee.getPassword());
            statement.setString(6, employee.getGender().toString());
            statement.setInt(7, employee.getBirthYear());
            statement.setString(8, employee.getAddress());
            statement.setString(9, employee.getEducation());
            statement.setString(10, employee.getType().toString());
            statement.setBoolean(11, employee.isChiefEditor());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating employee failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Integer id = generatedKeys.getInt(1);
                    return new Employee(
                            id,
                            employee.getFirstName(),
                            employee.getLastName(),
                            employee.getMiddleName(),
                            employee.getEmail(),
                            employee.getPassword(),
                            employee.getGender(),
                            employee.getBirthYear(),
                            employee.getAddress(),
                            employee.getEducation(),
                            employee.getType(),
                            employee.isChiefEditor()
                    );
                } else {
                    throw new SQLException("Creating employee failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_TO_ADD_MSG, e);
        }
    }

    @Override
    public void update(Employee employee) {
        boolean updatePassword =
                employee.getPassword() != null && !employee.getPassword().isEmpty();
        String sql;
        if (updatePassword) {
            sql = SQL_UPDATE_WITH_PASSWORD;
        } else {
            sql = SQL_UPDATE_WITHOUT_PASSWORD;
        }
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setString(3, employee.getMiddleName());
            statement.setString(4, employee.getEmail());
            if (updatePassword) {
                statement.setString(5, employee.getPassword());
                statement.setString(6, employee.getGender().name());
                statement.setObject(7, employee.getBirthYear());
                statement.setString(8, employee.getAddress());
                statement.setString(9, employee.getEducation());
                statement.setString(10, employee.getType().name());
                statement.setInt(11, employee.getId());
            } else {
                statement.setString(5, employee.getGender().name());
                statement.setObject(6, employee.getBirthYear());
                statement.setString(7, employee.getAddress());
                statement.setString(8, employee.getEducation());
                statement.setString(9, employee.getType().name());
                statement.setInt(10, employee.getId());
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_TO_UPDATE_WITH_ID_MSG + employee.getId(), e);
        }
    }

    @Override
    public Employee get(Integer id) {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_GET)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSetToEmployee(resultSet);
                } else {
                    throw new ObjectNotFoundException(EMPLOYEE_NOT_FOUND_MSG);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_TO_GET_MSG, e);
        }
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_LIST);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                employees.add(resultSetToEmployee(resultSet));
            }
            return employees;
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_TO_LIST_MSG, e);
        }
    }

    @Override
    public void delete(Integer id) {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_TO_DELETE_MSG, e);
        }
    }

    @Override
    public boolean exists(Integer id) {
        return super.exists(id, SQL_EXIST, FAILED_TO_CHECK_MESSAGE);
    }

    @Override
    public boolean existsChiefEditor() {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_EXIST_CE);
                ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_TO_CHECK_EXISTING_CE_MSG, e);
        }
        return false;
    }

    private Employee resultSetToEmployee(ResultSet resultSet) throws SQLException {
        return new Employee(
                resultSet.getInt(ID),
                resultSet.getString(FIRST_NAME),
                resultSet.getString(LAST_NAME),
                resultSet.getString(MIDDLE_NAME),
                resultSet.getString(EMAIL),
                resultSet.getString(PASSWORD),
                Gender.valueOf(resultSet.getString(GENDER).toUpperCase()),
                resultSet.getInt(BIRTH_YEAR),
                resultSet.getString(ADDRESS),
                resultSet.getString(EDUCATION),
                Type.valueOf(resultSet.getString(TYPE).toUpperCase()),
                resultSet.getBoolean(IS_CHIEF_EDITOR)
        );
    }
}