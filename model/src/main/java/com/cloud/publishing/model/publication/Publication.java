package com.cloud.publishing.model.publication;


import java.util.Set;

public record Publication(
        Integer id,
        String name,
        PublicationType publicationType,
        String theme,
        Set<Integer> categories,
        Set<Integer> journalists,
        Set<Integer> editors
) {
}