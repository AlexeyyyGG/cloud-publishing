package model;

import java.util.List;

public record Article(
        String name,
        PublicationType publicationType,
        String category,
        Employee author,
        List<Employee> coAuthor) {
}