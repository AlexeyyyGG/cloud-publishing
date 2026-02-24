package constants.employee;

public class EmployeeSQL {
    private EmployeeSQL() {
    }

    public static final String SQL_INSERT = """
            INSERT INTO employees(first_name, last_name, middle_name, email, password, gender,
            birth_year, address, education, type, is_chief_editor) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""";
    public static final String SQL_UPDATE_WITH_PASSWORD = """
            UPDATE employees SET first_name=?, last_name=?, middle_name=?, email=?, password=?,
            gender=?, birth_year=?, address=?, education=?, type=? WHERE id=?""";
    public static final String SQL_UPDATE_WITHOUT_PASSWORD = """
            UPDATE employees SET first_name=?, last_name=?, middle_name=?, email=?,
            gender=?, birth_year=?, address=?, education=?, type=? WHERE id=?""";
    public static final String SQL_GET = "SELECT * FROM employees WHERE id=?";
    public static final String SQL_LIST = "SELECT * FROM employees";
    public static final String SQL_DELETE = "DELETE FROM employees WHERE id=?";
    public static final String SQL_EXIST = "SELECT EXISTS(SELECT 1 FROM employees WHERE id = ?)";
    public static final String SQL_EXIST_CE =
            "SELECT EXISTS(SELECT 1 FROM employees WHERE is_chief_editor = TRUE)";
}
