package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import model.Employee;
import model.Gender;
import model.Type;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepository {
    private final Connection connection;
    private static final String COL_ID = "id";
    private static final String COL_FIRST_NAME = "first_name";
    private static final String COL_LAST_NAME = "last_name";
    private static final String COL_MIDDLE_NAME = "middle_name";
    private static final String COL_EMAIL = "email";
    private static final String COL_PASSWORD = "password";
    private static final String COL_GENDER = "gender";
    private static final String COL_BIRTH_YEAR = "birth_year";
    private static final String COL_ADDRESS = "address";
    private static final String COL_EDUCATION = "education";
    private static final String COL_TYPE = "type";
    private static final String COL_IS_CHIEF_EDITOR = "is_chief_editor";
    private static final String SQL_INSERT = """
            INSERT INTO EMPLOYEES(first_name, last_name, middle_name, email, password, gender,
            birth_year, address, education, type, is_chief_editor) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""";
    private static final String SQL_UPDATE_WITH_PASSWORD = """
            UPDATE EMPLOYEES SET first_name=?, last_name=?, middle_name=?, email=?, password=?,
            gender=?, birth_year=?, address=?, education=?, type=? WHERE id=?""";
    private static final String SQL_UPDATE_WITHOUT_PASSWORD = """
            UPDATE EMPLOYEES SET first_name=?, last_name=?, middle_name=?, email=?,
            gender=?, birth_year=?, address=?, education=?, type=? WHERE id=?""";
    private static final String SQL_GET = "SELECT * FROM EMPLOYEES WHERE id=?";
    private static final String SQL_LIST = "SELECT * FROM EMPLOYEES";
    private static final String SQL_DELETE = "DELETE FROM EMPLOYEES WHERE id=?";
    private static final String SQL_EXIST = "SELECT EXISTS(SELECT 1 FROM EMPLOYEES WHERE id = ?)";
    private static final String SQL_EXIST_CE =
            "SELECT EXISTS(SELECT 1 FROM EMPLOYEES WHERE is_chief_editor = TRUE)";
    private static final String FAILED_TO_RESET_CE_MSG = "Failed to reset chief editor";
    private static final String FAILED_TO_ADD_MSG = "Failed to add";
    private static final String FAILED_TO_UPDATE_WITH_ID_MSG = "Failed to update employee with id, %d";
    private static final String FAILED_TO_GET_MSG = "Failed to get";
    private static final String FAILED_TO_LIST_MSG = "Failed to list";
    private static final String FAILED_TO_DELETE_MSG = "Failed to delete";
    private static final String FAILED_TO_CHECK_MESSAGE = "Failed to check if employee exists";
    private static final String FAILED_TO_CHECK_EXISTING_CE_MSG = "Error checking for existing chief editor";
    private static final String EMPLOYEE_NOT_FOUND_MSG = "Employee not found";

    public EmployeeRepository(Connection connection) {
        this.connection = connection;
    }

    public void resetChiefEditorExcept(int excludeId) {
        String sql = "UPDATE EMPLOYEES SET is_chief_editor = FALSE WHERE is_chief_editor = TRUE AND id <> ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, excludeId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_TO_RESET_CE_MSG, e);
        }
    }

    public void add(Employee employee) {
        if (employee.isChiefEditor()) {
            resetChiefEditorExcept(-1);
        }
        try (PreparedStatement statement = connection.prepareStatement(
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
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                resultSet.next();
                employee.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_TO_ADD_MSG, e);
        }
    }

    public void update(Employee employee) {
        boolean updatePassword =
                employee.getPassword() != null && !employee.getPassword().isEmpty();
        String sql;
        if (updatePassword) {
            sql = SQL_UPDATE_WITH_PASSWORD;
        } else {
            sql = SQL_UPDATE_WITHOUT_PASSWORD;
        }
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
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

    public Employee get(int id) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_GET)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSetToEmployee(resultSet);
                } else {
                    throw new NoSuchElementException(EMPLOYEE_NOT_FOUND_MSG);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_TO_GET_MSG, e);
        }
    }

    public List<Employee> list() {
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

    public boolean exist(int id) {
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
        Employee employee = new Employee();
        employee.setId(resultSet.getInt(COL_ID));
        employee.setFirstName(resultSet.getString(COL_FIRST_NAME));
        employee.setLastName(resultSet.getString(COL_LAST_NAME));
        employee.setMiddleName(resultSet.getString(COL_MIDDLE_NAME));
        employee.setEmail(resultSet.getString(COL_EMAIL));
        employee.setPassword(resultSet.getString(COL_PASSWORD));
        employee.setGender(Gender.valueOf(resultSet.getString(COL_GENDER).toUpperCase()));
        employee.setBirthYear(resultSet.getInt(COL_BIRTH_YEAR));
        employee.setAddress(resultSet.getString(COL_ADDRESS));
        employee.setEducation(resultSet.getString(COL_EDUCATION));
        employee.setType(Type.valueOf(resultSet.getString(COL_TYPE).toUpperCase()));
        employee.setChiefEditor(resultSet.getBoolean(COL_IS_CHIEF_EDITOR));
        return employee;
    }
}