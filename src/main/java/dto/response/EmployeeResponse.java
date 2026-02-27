package dto.response;

import model.Gender;
import model.Type;

public record EmployeeResponse(
        Integer id,
        String firstName,
        String lastName,
        String middleName,
        String email,
        Gender gender,
        int birthYear,
        String address,
        String education,
        Type type,
        boolean isChiefEditor
) {
}