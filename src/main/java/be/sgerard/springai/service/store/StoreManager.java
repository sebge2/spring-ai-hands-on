package be.sgerard.springai.service.store;

import be.sgerard.springai.model.document.DocumentEntity;
import org.springframework.ai.document.Document;

import java.util.List;


public interface StoreManager {

    void store(DocumentEntity entity, List<Document> documents);

    List<Document> find(String message, String documentId);

    void delete(DocumentEntity entity);
}
