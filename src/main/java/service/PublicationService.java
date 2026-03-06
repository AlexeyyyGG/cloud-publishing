package service;

import dto.request.PublicationRequest;
import dto.response.PublicationGetDTO;
import dto.response.PublicationResponse;
import java.util.List;

/**
 * Service interface for managing publications. Provides operations for creating, updating,
 * retrieving and deleting publications.
 */
public interface PublicationService {
    /**
     * Creates a new publication.
     *
     * @param request object containing publication data
     * @return created publication
     */
    PublicationResponse add(PublicationRequest request);

    /**
     * Updates an existing publication.
     *
     * @param id      identifier of the publication to update
     * @param request object containing updated publication data
     * @return updated publication
     */
    PublicationResponse update(int id, PublicationRequest request);

    /**
     * Returns publication by id.
     *
     * @param id identifier of the publication
     * @return publication
     */
    PublicationResponse get(int id);

    /**
     * Returns list of all publications.
     *
     * @return list of publications
     */
    List<PublicationGetDTO> getAll();

    /**
     * Deletes publication by id.
     *
     * @param id identifier of the publication to delete
     */
    void delete(int id);
}