package model;

public record Publication(
        String name,
        PublicationType type,
        String theme,
        String category
        ) {
}