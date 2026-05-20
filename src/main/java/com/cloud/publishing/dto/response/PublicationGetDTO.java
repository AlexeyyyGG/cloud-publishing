package com.cloud.publishing.dto.response;

import com.cloud.publishing.model.Category;
import com.cloud.publishing.model.PublicationType;
import java.util.List;

public record PublicationGetDTO(
        int id,
        String name,
        PublicationType publicationType,
        String theme,
        List<Category> categories
) {
}