package employees.repository;

import common.ObjectNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import employees.model.Employee;
import employees.model.Gender;
import employees.model.Type;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepository {
    private final Connection connection;
    private static final String ID = "id";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String MIDDLE_NAME = "middle_name";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String GENDER = "gender";
    private static final String BIRTH_YEAR = "birth_year";
    private static final String ADDRESS = "address";
    private static final String EDUCATION = "education";
    private static final String TYPE = "type";
    private static final String IS_CHIEF_EDITOR = "is_chief_editor";
    private static final String SQL_INSERT = """
            INSERT INTO employees(first_name, last_name, middle_name, email, password, gender,
            birth_year, address, education, type, is_chief_editor) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""";
    private static final String SQL_UPDATE_WITH_PASSWORD = """
            UPDATE employees SET first_name=?, last_name=?, middle_name=?, email=?, password=?,
            gender=?, birth_year=?, address=?, education=?, type=? WHERE id=?""";
    private static final String SQL_UPDATE_WITHOUT_PASSWORD = """
            UPDATE employees SET first_name=?, last_name=?, middle_name=?, email=?,
            gender=?, birth_year=?, address=?, education=?, type=? WHERE id=?""";
    private static final String SQL_GET = "SELECT * FROM employees WHERE id=?";
    private static final String SQL_LIST = "SELECT * FROM employees";
    private static final String SQL_DELETE = "DELETE FROM employees WHERE id=?";
    private static final String SQL_EXIST = "SELECT EXISTS(SELECT 1 FROM employees WHERE id = ?)";
    private static final String SQL_EXIST_CE =
            "SELECT EXISTS(SELECT 1 FROM employees WHERE is_chief_editor = TRUE)";
    private static final String FAILED_TO_ADD_MSG = "Failed to add";
    private static final String FAILED_TO_UPDATE_WITH_ID_MSG = "Failed to update employee with id, %d";
    private static final String FAILED_TO_GET_MSG = "Failed to get";
    private static final String FAILED_TO_LIST_MSG = "Failed to getAll";
    private static final String FAILED_TO_DELETE_MSG = "Failed to delete";
    private static final String FAILED_TO_CHECK_MESSAGE = "Failed to check if employee exists";
    private static final String FAILED_TO_CHECK_EXISTING_CE_MSG = "Error checking for existing chief editor";
    private static final String EMPLOYEE_NOT_FOUND_MSG = "Employee not found";

    public EmployeeRepository(Connection connection) {
        this.connection = connection;
    }

    public void add(Employee employee) {
        try (PreparedStatement statement = connection.prepareStatement(
                SQL_INSERT,
                Statement.RETURN_GENERATED_KEYS
        )) {
            statement.setString(1, employee.firstName());
            statement.setString(2, employee.lastName());
            statement.setString(3, employee.middleName());
            statement.setString(4, employee.email());
            statement.setString(5, employee.password());
            statement.setString(6, employee.gender().toString());
            statement.setInt(7, employee.birthYear());
            statement.setString(8, employee.address());
            statement.setString(9, employee.education());
            statement.setString(10, employee.type().toString());
            statement.setBoolean(11, employee.isChiefEditor());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_TO_ADD_MSG, e);
        }
    }

    public void update(Employee employee) {
        boolean updatePassword =
                employee.password() != null && !employee.password().isEmpty();
        String sql;
        if (updatePassword) {
            sql = SQL_UPDATE_WITH_PASSWORD;
        } else {
            sql = SQL_UPDATE_WITHOUT_PASSWORD;
        }
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, employee.firstName());
            statement.setString(2, employee.lastName());
            statement.setString(3, employee.middleName());
            statement.setString(4, employee.email());
            if (updatePassword) {
                statement.setString(5, employee.password());
                statement.setString(6, employee.gender().name());
                statement.setObject(7, employee.birthYear());
                statement.setString(8, employee.address());
                statement.setString(9, employee.education());
                statement.setString(10, employee.type().name());
                statement.setInt(11, employee.id());
            } else {
                statement.setString(5, employee.gender().name());
                statement.setObject(6, employee.birthYear());
                statement.setString(7, employee.address());
                statement.setString(8, employee.education());
                statement.setString(9, employee.type().name());
                statement.setInt(10, employee.id());
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_TO_UPDATE_WITH_ID_MSG + employee.id(), e);
        }
    }

    public Employee get(int id) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_GET)) {
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

    public List<Employee> getAll() {
        List<Employee> employees = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_LIST);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                employees.add(resultSetToEmployee(resultSet));
            }
            return employees;
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_TO_LIST_MSG, e);
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

    public boolean existsChiefEditor() {
        try (PreparedStatement statement = connection.prepareStatement(SQL_EXIST_CE);
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