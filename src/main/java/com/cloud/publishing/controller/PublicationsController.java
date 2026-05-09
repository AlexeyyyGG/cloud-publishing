package com.cloud.publishing.controller;

import com.cloud.publishing.constants.Parameters;
import com.cloud.publishing.constants.Urls;
import com.cloud.publishing.dto.request.PublicationRequest;
import com.cloud.publishing.dto.response.EmployeeShort;
import com.cloud.publishing.dto.response.PublicationResponse;
import com.cloud.publishing.mapper.PublicationMapper;
import com.cloud.publishing.model.Category;
import com.cloud.publishing.model.Publication;
import com.cloud.publishing.service.CategoryService;
import com.cloud.publishing.service.EmployeeService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cloud.publishing.dto.response.PublicationGetDTO;
import com.cloud.publishing.service.PublicationService;

@RestController
@PreAuthorize("hasRole('CHIEF_EDITOR')")
@RequestMapping(Urls.PUBLICATIONS)
public class PublicationsController {
    private final PublicationService publicationService;
    private final CategoryService categoryService;
    private final EmployeeService employeeService;
    private final PublicationMapper publicationMapper;
    private static final Logger logger = LoggerFactory.getLogger(PublicationsController.class);

    @Autowired
    public PublicationsController(
            PublicationService publicationService,
            CategoryService categoryService,
            EmployeeService employeeService,
            PublicationMapper publicationMapper
    ) {
        this.publicationService = publicationService;
        this.categoryService = categoryService;
        this.employeeService = employeeService;
        this.publicationMapper = publicationMapper;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PublicationGetDTO>> getAll() {
        logger.info("getAll called");
        List<Publication> publications = publicationService.getAll();
        List<Category> categories = categoryService.getAll();
        List<PublicationGetDTO> publicationGetDTOS = publications.stream()
                .map(publication -> {
                    List<Category> pubCategories = categories.stream()
                            .filter(c -> publication.categories().contains(c.id()))
                            .toList();
                    return publicationMapper.toGetDTO(publication, pubCategories);
                })
                .toList();
        logger.debug("Found {} publications", publications.size());
        return new ResponseEntity<>(publicationGetDTOS, HttpStatus.OK);
    }

    @GetMapping(value = Urls.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PublicationResponse> get(@PathVariable(Parameters.ID) int id) {
        logger.info("get called with id={}", id);
        Publication publication = publicationService.get(id);
        return ResponseEntity.status(HttpStatus.OK).body(assemblePublicationResponse(publication));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PublicationResponse> add(@RequestBody PublicationRequest request) {
        logger.info("add called with: {}", request);
        Publication publication = publicationService.add(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(assemblePublicationResponse(publication));
    }

    @PutMapping(value = Urls.ID, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PublicationResponse> update(
            @PathVariable(Parameters.ID) int id,
            @RequestBody PublicationRequest request
    ) {
        logger.info("update called for id {}", id);
        Publication publication = publicationService.update(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(assemblePublicationResponse(publication));
    }

    @DeleteMapping(value = {Urls.ID})
    public ResponseEntity<Void> delete(@PathVariable(Parameters.ID) int id) {
        logger.info("delete called with id {}", id);
        publicationService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private PublicationResponse assemblePublicationResponse(Publication publication) {
        List<Category> categories = categoryService.getByIds(publication.categories());
        List<EmployeeShort> journalists = employeeService.getByIds(publication.journalists());
        List<EmployeeShort> editors = employeeService.getByIds(publication.editors());
        return publicationMapper.toResponse(publication, categories, journalists, editors);
    }
}