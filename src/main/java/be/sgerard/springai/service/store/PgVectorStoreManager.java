package be.sgerard.springai.service.store;

import be.sgerard.springai.model.document.DocumentEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.PgVectorStore;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PgVectorStoreManager implements StoreManager {

    public static final String DOCUMENT_ID = "document_id";

    private final PgVectorStore vectorStore;

    @Override
    @Transactional
    public void store(DocumentEntity entity, List<Document> documents) {
        documents.forEach(doc -> doc.getMetadata().put(DOCUMENT_ID, entity.getId()));

        vectorStore.add(documents);
    }

    @Override
    @Transactional
    public List<Document> find(String message, String documentId) {
        final SearchRequest request = SearchRequest.query(message);

        Optional.ofNullable(documentId)
                .ifPresent(doc -> request.withFilterExpression(
                        new FilterExpressionBuilder().eq(DOCUMENT_ID, doc)
                                .build()
                ));

        return this.vectorStore.similaritySearch(request);
    }

    @Override
    @Transactional
    public void delete(DocumentEntity entity) {
        vectorStore.delete(
                find("", entity.getId())
                        .stream()
                        .map(Document::getId)
                        .toList()
        );
    }
}
