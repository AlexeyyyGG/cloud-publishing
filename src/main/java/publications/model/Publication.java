package publications.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import common.PublicationType;
import java.util.List;

public record Publication(
        int id,
        String name,
        @JsonProperty("publication_type")
        PublicationType publicationType,
        String theme,
        List<Integer> categories,
        List<Integer> journalists,
        List<Integer> editors
) {
}