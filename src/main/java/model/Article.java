package model;

import java.util.List;

public record Article(
        int id,
        String name,
        String publicationName,
        String categoryName,
        String author,
        List<String> coAuthor) {
}