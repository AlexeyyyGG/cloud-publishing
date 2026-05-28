package com.cloud.publishing.common.dto.response;

import com.cloud.publishing.model.publication.Category;
import com.cloud.publishing.model.publication.PublicationType;
import java.util.List;

public record PublicationGetDTO(
        int id,
        String name,
        PublicationType publicationType,
        String theme,
        List<Category> categories
) {
}