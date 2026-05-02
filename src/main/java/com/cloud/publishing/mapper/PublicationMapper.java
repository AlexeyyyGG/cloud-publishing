package com.cloud.publishing.mapper;

import com.cloud.publishing.dto.request.PublicationRequest;
import com.cloud.publishing.dto.response.EmployeeShort;
import com.cloud.publishing.dto.response.PublicationGetDTO;
import com.cloud.publishing.dto.response.PublicationResponse;
import com.cloud.publishing.model.Category;
import com.cloud.publishing.model.Publication;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PublicationMapper {
    @Mapping(target = "id", ignore = true)
    Publication toEntity(PublicationRequest request);

    @Mapping(target = "id", source = "id")
    Publication toEntity(int id, PublicationRequest request);

    @Mapping(target = "categories", source = "categories")
    @Mapping(target = "journalists", source = "journalists")
    @Mapping(target = "editors", source = "editors")
    PublicationResponse toResponse(
            Publication publication,
            List<Category> categories,
            List<EmployeeShort> journalists,
            List<EmployeeShort> editors
    );

    @Mapping(target = "categories", source = "categories")
    PublicationGetDTO toGetDTO(Publication publication, List<Category> categories);
}