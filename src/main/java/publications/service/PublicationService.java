package publications.service;

import common.ObjectNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import publications.dto.PublicationGetDTO;
import publications.model.Publication;
import publications.repository.PublicationRepository;

@Service
public class PublicationService {
    private final PublicationRepository repository;
    private static final String PUBLICATION_NOT_FOUND_MSG = "Publication not found";

    public PublicationService(PublicationRepository repository) {
        this.repository = repository;
    }

    public void addPublication(Publication publication) {
        repository.add(publication);
    }

    public void updatePublication(int id, Publication request) {
        Publication existingPublication = repository.get(id);
        if (existingPublication == null) {
            throw new ObjectNotFoundException(PUBLICATION_NOT_FOUND_MSG);
        }
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
    }

    public Publication getPublication(int id) {
        if (repository.exists(id)) {
            return repository.get(id);
        } else {
            throw new ObjectNotFoundException(PUBLICATION_NOT_FOUND_MSG);
        }
    }

    public List<PublicationGetDTO> getAllPublications() {
        return repository.getAll();
    }

    public void deletePublication(int id) {
        if (repository.exists(id)) {
            repository.delete(id);
        } else {
            throw new ObjectNotFoundException(PUBLICATION_NOT_FOUND_MSG);
        }
    }
}