package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import model.Gender;
import model.Type;

public class EmployeeUpdateRequest {
    private int id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("middle_name")
    private String middleName;
    private String email;
    private String password;
    private String passwordConfirm;
    private Gender gender;
    @JsonProperty("birth_year")
    private int birthYear;
    private String address;
    private String education;
    private Type type;
    private boolean isChiefEditor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getEmail() {
        return email;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public Gender getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getEducation() {
        return education;
    }

    public Type getType() {
        return type;
    }

    public boolean isChiefEditor() {
        return isChiefEditor;
    }
}