package com.cloud.publishing.repository;

import java.util.List;
import com.cloud.publishing.model.Publication;

public interface PublicationRepository extends IRepository<Publication, Integer> {
    List<Publication> getAll();
}