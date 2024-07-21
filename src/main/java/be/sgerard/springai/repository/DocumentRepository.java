package be.sgerard.springai.repository;

import be.sgerard.springai.model.document.DocumentEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * {@link CrudRepository Repository} of {@link DocumentEntity documents}.
 */
public interface DocumentRepository extends CrudRepository<DocumentEntity, String> {

    @Override
    List<DocumentEntity> findAll();

}
