package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;

public record Publication(
        int id,
        String name,
        @JsonProperty("publication_type")
        PublicationType publicationType,
        String theme,
        Set<Integer> categories,
        Set<Integer> journalists,
        Set<Integer> editors
) {
}