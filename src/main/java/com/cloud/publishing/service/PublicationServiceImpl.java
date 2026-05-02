package com.cloud.publishing.service;

import static com.cloud.publishing.constants.publication.PublicationMessage.PUBLICATION_NOT_FOUND_MSG;

import com.cloud.publishing.dto.request.PublicationRequest;
import com.cloud.publishing.dto.response.EmployeeShort;
import com.cloud.publishing.dto.response.PublicationResponse;
import com.cloud.publishing.exception.ObjectNotFoundException;
import com.cloud.publishing.mapper.EmployeeMapper;
import com.cloud.publishing.model.Category;
import com.cloud.publishing.model.Type;
import com.cloud.publishing.repository.CategoryRepository;
import com.cloud.publishing.repository.EmployeeRepository;
import java.util.List;
import com.cloud.publishing.mapper.PublicationMapper;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cloud.publishing.dto.response.PublicationGetDTO;
import com.cloud.publishing.model.Publication;
import com.cloud.publishing.repository.PublicationRepository;

@Service
public class PublicationServiceImpl implements PublicationService {
    private final PublicationRepository publicationRepository;
    private final CategoryRepository categoryRepository;
    private final EmployeeRepository employeeRepository;
    private final PublicationMapper publicationMapper;
    private final EmployeeMapper employeeMapper;

    @Autowired
    public PublicationServiceImpl(
            PublicationRepository publicationRepository,
            CategoryRepository categoryRepository,
            EmployeeRepository employeeRepository,
            PublicationMapper publicationMapper,
            EmployeeMapper employeeMapper
    ) {
        this.publicationRepository = publicationRepository;
        this.categoryRepository = categoryRepository;
        this.employeeRepository = employeeRepository;
        this.publicationMapper = publicationMapper;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public PublicationResponse add(PublicationRequest request) {
        Publication publication = publicationMapper.toEntity(request);
        Publication saved = publicationRepository.add(publication);
        return assembleResponse(saved);
    }

    @Override
    public PublicationResponse update(int id, PublicationRequest request) {
        Publication publication = publicationMapper.toEntity(id, request);
        publicationRepository.update(publication);
        return assembleResponse(publication);
    }

    @Override
    public PublicationResponse get(int id) {
        Publication publication = publicationRepository.get(id);
        return assembleResponse(publication);
    }

    private PublicationResponse assembleResponse(Publication publication) {
        return publicationMapper.toResponse(
                publication,
                resolveCategories(publication.categories()),
                resolveEmployees(publication.journalists(), Type.JOURNALIST),
                resolveEmployees(publication.editors(), Type.EDITOR)
        );
    }

    @Override
    public List<PublicationGetDTO> getAll() {
        Map<Integer, Category> categoryMap = categoryRepository.getAll()
                .stream()
                .collect(Collectors.toMap(Category::id, c -> c));
        return publicationRepository.getAll().stream()
                .map(pub -> {
                    List<Category> categories = pub.categories().stream()
                            .map(categoryMap::get)
                            .filter(Objects::nonNull)
                            .toList();
                    return publicationMapper.toGetDTO(pub, categories);
                })
                .toList();
    }

    @Override
    public void delete(int id) {
        if (publicationRepository.exists(id)) {
            publicationRepository.delete(id);
        } else {
            throw new ObjectNotFoundException(PUBLICATION_NOT_FOUND_MSG);
        }
    }

    private List<Category> resolveCategories(Set<Integer> ids) {
        if (ids.isEmpty()) {
            return List.of();
        }
        Map<Integer, Category> map = categoryRepository.getAll()
                .stream()
                .collect(Collectors.toMap(Category::id, c -> c));
        return ids.stream()
                .map(map::get)
                .filter(Objects::nonNull)
                .toList();
    }

    private List<EmployeeShort> resolveEmployees(Set<Integer> ids, Type type) {
        if (ids.isEmpty()) {
            return List.of();
        }
        return employeeMapper.toShortList(employeeRepository.findByIdsAndType(ids, type));
    }
}