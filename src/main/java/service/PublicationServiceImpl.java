package service;

import dto.request.PublicationRequest;
import dto.response.PublicationResponse;
import exception.ObjectNotFoundException;
import java.util.List;
import mapper.PublicationMapper;
import org.springframework.stereotype.Service;
import dto.response.PublicationGetDTO;
import model.Publication;
import repository.PublicationRepository;

@Service
public class PublicationServiceImpl implements PublicationService {
    private final PublicationRepository repository;
    private final PublicationMapper mapper;
    private static final String PUBLICATION_NOT_FOUND_MSG = "Publication not found";

    public PublicationServiceImpl(PublicationRepository repository, PublicationMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public PublicationResponse add(PublicationRequest request) {
        Publication publication = mapper.toEntity(request);
        Publication saved = repository.add(publication);
        return mapper.toResponse(saved);
    }

    @Override
    public PublicationResponse update(int id, PublicationRequest request) {
        Publication existingPublication = repository.get(id);
        mapper.update(request, existingPublication);
        repository.update(existingPublication);
        return mapper.toResponse(existingPublication);
    }

    @Override
    public PublicationResponse get(int id) {
        Publication existingPublication = repository.get(id);
        return mapper.toResponse(existingPublication);
    }

    @Override
    public List<PublicationGetDTO> getAll() {
        return repository.getAll();
    }

    @Override
    public void delete(int id) {
        if (repository.exists(id)) {
            repository.delete(id);
        } else {
            throw new ObjectNotFoundException(PUBLICATION_NOT_FOUND_MSG);
        }
    }
}