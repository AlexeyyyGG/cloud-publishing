package com.cloud.publishing.service;

import static com.cloud.publishing.constants.publication.PublicationMessage.PUBLICATION_NOT_FOUND_MSG;

import com.cloud.publishing.dto.request.PublicationRequest;
import com.cloud.publishing.exception.ObjectNotFoundException;
import java.util.List;
import com.cloud.publishing.mapper.PublicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cloud.publishing.model.Publication;
import com.cloud.publishing.repository.PublicationRepository;

@Service
public class PublicationServiceImpl implements PublicationService {
    private final PublicationRepository repository;
    private final PublicationMapper mapper;

    @Autowired
    public PublicationServiceImpl(PublicationRepository repository, PublicationMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Publication add(PublicationRequest request) {
        Publication publication = mapper.toEntity(request);
        return repository.add(publication);
    }

    @Override
    public Publication update(int id, PublicationRequest request) {
        Publication publication = mapper.toEntity(id, request);
        repository.update(publication);
        return publication;
    }

    @Override
    public Publication get(int id) {
        return repository.get(id);
    }

    @Override
    public List<Publication> getAll() {
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