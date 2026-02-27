package dto.response;

import java.util.Set;
import model.PublicationType;

public record PublicationResponse(
        Integer id,
        String name,
        PublicationType publicationType,
        String theme,
        Set<Integer> categories,
        Set<Integer> journalists,
        Set<Integer> editors
) {
}