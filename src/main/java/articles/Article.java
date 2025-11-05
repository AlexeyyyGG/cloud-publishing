package articles;

import employees.model.Employee;
import java.util.List;

public record Article(
        int id,
        String name,
        String publicationName,
        String categoryName,
        String author,
        List<String> coAuthor) {
}