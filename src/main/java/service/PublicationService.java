package service;

import dto.request.PublicationRequest;
import dto.response.PublicationResponse;
import exception.ObjectNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import dto.response.PublicationGetDTO;
import model.Publication;
import repository.PublicationRepository;

@Service
public class PublicationService {
    private final PublicationRepository repository;
    private static final String PUBLICATION_NOT_FOUND_MSG = "Publication not found";

    public PublicationService(PublicationRepository repository) {
        this.repository = repository;
    }

    public PublicationResponse add(PublicationRequest request) {
        Publication publication = new Publication(
                null,
                request.name(),
                request.publicationType(),
                request.theme(),
                request.categories(),
                request.journalists(),
                request.editors()
        );
        Publication saved = repository.add(publication);
        return toResponse(saved);
    }

    public PublicationResponse update(int id, PublicationRequest request) {
        Publication existingPublication = repository.get(id);
        Publication updatedPublication = new Publication(
                existingPublication.id(),
                request.name(),
                request.publicationType(),
                request.theme(),
                request.categories(),
                request.journalists(),
                request.editors()
        );
        repository.update(updatedPublication);
        return toResponse(updatedPublication);
    }

    public PublicationResponse get(int id) {
       Publication existingPublication = repository.get(id);
       return toResponse(existingPublication);
    }

    public List<PublicationGetDTO> getAll() {
        return repository.getAll();
    }

    public void delete(int id) {
        if (repository.exists(id)) {
            repository.delete(id);
        } else {
            throw new ObjectNotFoundException(PUBLICATION_NOT_FOUND_MSG);
        }
    }

    private PublicationResponse toResponse(Publication publication) {
        return new PublicationResponse(
                publication.id(),
                publication.name(),
                publication.publicationType(),
                publication.theme(),
                publication.categories(),
                publication.journalists(),
                publication.editors()
        );
    }
}