package mapper;

import dto.request.PublicationRequest;
import dto.response.PublicationResponse;
import model.Publication;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PublicationMapper {
    @Mapping(target = "id", ignore = true)
    Publication toEntity(PublicationRequest request);

    @Mapping(target = "id", source = "id")
    Publication toEntity(int id, PublicationRequest request);

    PublicationResponse toResponse(Publication publication);
}