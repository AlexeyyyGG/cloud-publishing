package com.cloud.publishing.backend.repository;

import java.util.List;
import com.cloud.publishing.backend.model.Publication;

public interface PublicationRepository extends IRepository<Publication, Integer> {
    List<Publication> getAll();
}