package com.cloud.publishing.backend.repository;

import com.cloud.publishing.model.publication.Publication;
import java.util.List;

public interface PublicationRepository extends IRepository<Publication, Integer> {
    List<Publication> getAll();
}