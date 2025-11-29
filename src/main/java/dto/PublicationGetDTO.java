package dto;

import model.PublicationType;
import java.util.List;

public record PublicationGetDTO(
        int id,
        String name,
        PublicationType publicationType,
        String theme,
        List<String> category
) {
}