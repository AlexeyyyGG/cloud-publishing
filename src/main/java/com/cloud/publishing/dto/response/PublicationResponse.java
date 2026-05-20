package com.cloud.publishing.dto.response;

import com.cloud.publishing.model.Category;
import java.util.List;
import com.cloud.publishing.model.PublicationType;

public record PublicationResponse(
        Integer id,
        String name,
        PublicationType publicationType,
        String theme,
        List<Category> categories,
        List<EmployeeShort> journalists,
        List<EmployeeShort> editors
) {
}