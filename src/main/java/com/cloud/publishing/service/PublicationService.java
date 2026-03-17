package com.cloud.publishing.service;

import com.cloud.publishing.dto.request.PublicationRequest;
import com.cloud.publishing.dto.response.PublicationGetDTO;
import com.cloud.publishing.dto.response.PublicationResponse;
import java.util.List;

/**
 * Service interface for managing publications. Provides operations for creating, updating,
 * retrieving and deleting publications.
 */
public interface PublicationService {
    /**
     * Creates a new publication.
     *
     * @param request {@link PublicationRequest} containing publication data
     * @return created {@link PublicationResponse}
     */
    PublicationResponse add(PublicationRequest request);

    /**
     * Updates an existing publication.
     *
     * @param id      identifier of the publication to update
     * @param request {@link PublicationRequest} containing updated publication data
     * @return updated {@link PublicationResponse}
     */
    PublicationResponse update(int id, PublicationRequest request);

    /**
     * Returns publication by id.
     *
     * @param id identifier of the publication
     * @return {@link PublicationResponse}
     */
    PublicationResponse get(int id);

    /**
     * Returns list of all publications.
     *
     * @return list of {@link PublicationGetDTO}
     */
    List<PublicationGetDTO> getAll();

    /**
     * Deletes publication by id.
     *
     * @param id identifier of the publication to delete
     */
    void delete(int id);
}