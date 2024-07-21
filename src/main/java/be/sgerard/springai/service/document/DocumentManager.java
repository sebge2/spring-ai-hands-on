package be.sgerard.springai.service.document;

import be.sgerard.springai.model.document.DocumentEntity;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Optional;

public interface DocumentManager {

    List<DocumentEntity> findAll();

    DocumentEntity upload(Resource doc);

    Optional<DocumentEntity> deleteById(String id);
}
