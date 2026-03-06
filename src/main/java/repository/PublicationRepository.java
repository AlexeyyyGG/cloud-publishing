package repository;

import dto.response.PublicationGetDTO;
import java.util.List;
import model.Publication;

public interface PublicationRepository extends IRepository<Publication, Integer> {
    List<PublicationGetDTO> getAll();
}