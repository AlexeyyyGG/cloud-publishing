package com.cloud.publishing.service;

import static com.cloud.publishing.constants.publication.PublicationMessage.PUBLICATION_NOT_FOUND_MSG;

import com.cloud.publishing.dto.request.PublicationRequest;
import com.cloud.publishing.dto.response.PublicationResponse;
import com.cloud.publishing.exception.ObjectNotFoundException;
import java.util.List;
import com.cloud.publishing.mapper.PublicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cloud.publishing.dto.response.PublicationGetDTO;
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
    public PublicationResponse add(PublicationRequest request) {
        Publication publication = mapper.toEntity(request);
        Publication saved = repository.add(publication);
        return mapper.toResponse(saved);
    }

    @Override
    public PublicationResponse update(int id, PublicationRequest request) {
        Publication publication = mapper.toEntity(id, request);
        repository.update(publication);
        return mapper.toResponse(publication);
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