package controller;

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
import dto.PublicationGetDTO;
import model.Publication;
import service.PublicationService;

@RestController
@RequestMapping(PublicationsController.PUBLICATIONS)
public class PublicationsController {
    static final String PUBLICATIONS = "/publications";
    private static final String ID_PATH = "/{id}";
    private static final String ID = "id";
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
    public ResponseEntity<Publication> get(@PathVariable(ID) int id) {
        logger.info("get called with id={}", id);
        Publication publication = service.get(id);
        return new ResponseEntity<>(publication, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> add(@RequestBody Publication publication) {
        logger.info("add called with: {}", publication);
        service.add(publication);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(value = ID_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(
            @PathVariable(ID) int id,
            @RequestBody Publication publication
    ) {
        logger.info("update called for id {}", id);
        service.update(id, publication);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping(value = {ID_PATH})
    public ResponseEntity<Void> delete(@PathVariable(ID) int id) {
        logger.info("delete called with id {}", id);
        service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}