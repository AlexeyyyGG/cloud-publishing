package model;

public class Employee {
    private Integer id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String password;
    private Gender gender;
    private int birthYear;
    private String address;
    private String education;
    private Type type;
    private boolean chiefEditor;

    public Employee(
            Integer id,
            String firstName,
            String lastName,
            String middleName,
            String email,
            String password,
            Gender gender,
            int birthYear,
            String address,
            String education,
            Type type,
            boolean chiefEditor) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.birthYear = birthYear;
        this.address = address;
        this.education = education;
        this.type = type;
        this.chiefEditor = chiefEditor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isChiefEditor() {
        return chiefEditor;
    }

    public void setChiefEditor(boolean chiefEditor) {
        this.chiefEditor = chiefEditor;
    }
}