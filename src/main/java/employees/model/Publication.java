package employees.model;

import common.PublicationType;

public record Publication(
        String name,
        PublicationType type,
        String theme,
        String category
        ) {
}