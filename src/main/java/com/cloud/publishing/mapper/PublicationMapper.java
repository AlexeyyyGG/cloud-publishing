package com.cloud.publishing.mapper;

import com.cloud.publishing.dto.request.PublicationRequest;
import com.cloud.publishing.dto.response.PublicationResponse;
import com.cloud.publishing.model.Publication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PublicationMapper {
    @Mapping(target = "id", ignore = true)
    Publication toEntity(PublicationRequest request);

    @Mapping(target = "id", source = "id")
    Publication toEntity(int id, PublicationRequest request);

    PublicationResponse toResponse(Publication publication);
}