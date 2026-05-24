package com.cloud.publishing.common.dto.response;

import com.cloud.publishing.common.reference.Category;
import com.cloud.publishing.common.reference.PublicationType;
import java.util.List;

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