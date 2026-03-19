package com.cloud.publishing.repository;

import com.cloud.publishing.dto.response.PublicationGetDTO;
import java.util.List;
import com.cloud.publishing.model.Publication;

public interface PublicationRepository extends IRepository<Publication, Integer> {
    List<PublicationGetDTO> getAll();
}