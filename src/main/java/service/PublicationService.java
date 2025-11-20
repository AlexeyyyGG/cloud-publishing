package service;

import exception.ObjectNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import dto.PublicationGetDTO;
import model.Publication;
import repository.PublicationRepository;

@Service
public class PublicationService {
    private final PublicationRepository repository;
    private static final String PUBLICATION_NOT_FOUND_MSG = "Publication not found";

    public PublicationService(PublicationRepository repository) {
        this.repository = repository;
    }

    public void add(Publication publication) {
        repository.add(publication);
    }

    public void update(int id, Publication request) {
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

    public Publication get(int id) {
        if (repository.exists(id)) {
            return repository.get(id);
        } else {
            throw new ObjectNotFoundException(PUBLICATION_NOT_FOUND_MSG);
        }
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
}