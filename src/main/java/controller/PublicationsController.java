package controller;

import static constants.Urls.ID;
import static constants.Urls.ID_PATH;
import static constants.Urls.PUBLICATIONS;

import dto.request.PublicationRequest;
import dto.response.PublicationResponse;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import dto.response.PublicationGetDTO;
import service.PublicationService;

@RestController
@RequestMapping(PUBLICATIONS)
public class PublicationsController {
    private final PublicationService service;
    private static final Logger logger = LoggerFactory.getLogger(PublicationsController.class);

    @Autowired
    public PublicationsController(PublicationService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PublicationGetDTO>> getAll() {
        logger.info("getAll called");
        List<PublicationGetDTO> publications = service.getAll();
        logger.debug("Found {} publications", publications.size());
        return new ResponseEntity<>(publications, HttpStatus.OK);
    }

    @GetMapping(value = ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PublicationResponse> get(@PathVariable(ID) int id) {
        logger.info("get called with id={}", id);
        PublicationResponse publication = service.get(id);
        return ResponseEntity.status(HttpStatus.OK).body(publication);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PublicationResponse> add(@RequestBody PublicationRequest request) {
        logger.info("add called with: {}", request);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.add(request));
    }

    @PutMapping(value = ID_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PublicationResponse> update(
            @PathVariable(ID) int id,
            @RequestBody PublicationRequest request
    ) {
        logger.info("update called for id {}", id);
        PublicationResponse updated = service.update(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping(value = {ID_PATH})
    public ResponseEntity<Void> delete(@PathVariable(ID) int id) {
        logger.info("delete called with id {}", id);
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}