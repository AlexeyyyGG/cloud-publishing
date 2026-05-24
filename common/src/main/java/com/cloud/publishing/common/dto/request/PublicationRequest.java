package com.cloud.publishing.common.dto.request;

import com.cloud.publishing.common.reference.PublicationType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

public record PublicationRequest(
        @NotBlank(message = "Название обязательно для заполнения")
        @Size(min = 2, max = 30, message = "Название должно содержать от 2 до 30 символов")
        String name,
        @JsonProperty("publication_type")
        @NotNull(message = "Необходимо указать тип публикации")
        com.cloud.publishing.common.reference.PublicationType publicationType,
        @NotEmpty(message = "Тематика обязательна к заполнению")
        String theme,
        @NotEmpty(message = "Необходимо указать категории")
        Set<Integer> categories,
        @NotEmpty(message = "Необходимо указать журналистов")
        Set<Integer> journalists,
        @NotEmpty(message = "Необходимо указать редакторов")
        Set<Integer> editors
) {
    public static PublicationRequest empty() {
        return new PublicationRequest(
                null,
                PublicationType.NEWSPAPER,
                null,
                new HashSet<>(),
                new HashSet<>(),
                new HashSet<>()
        );
    }
}