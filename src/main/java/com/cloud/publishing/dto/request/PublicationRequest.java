package com.cloud.publishing.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import com.cloud.publishing.model.PublicationType;

public record PublicationRequest(
        String name,
        @JsonProperty("publication_type")
        PublicationType publicationType,
        String theme,
        Set<Integer> categories,
        Set<Integer> journalists,
        Set<Integer> editors
) {
}