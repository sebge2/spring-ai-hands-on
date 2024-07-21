package be.sgerard.springai.service.document;

import be.sgerard.springai.model.document.DocumentEntity;
import be.sgerard.springai.repository.DocumentRepository;
import be.sgerard.springai.service.store.StoreManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentManagerImpl implements DocumentManager {

    private final StoreManager storeManager;
    private final DocumentRepository documentRepository;
    private final TokenTextSplitter textSplitter = new TokenTextSplitter();

    @Override
    @Transactional
    public List<DocumentEntity> findAll() {
        return documentRepository.findAll();
    }

    @Override
    @Transactional
    public DocumentEntity upload(Resource pdf) {
        final List<Document> documents = readDocuments(pdf);

        final DocumentEntity entity = documentRepository.save(
                new DocumentEntity(pdf.getFilename())
                        .setNumberPages(documents.size())
        );

        storeManager.store(entity, documents);

        return entity;
    }

    @Override
    @Transactional
    public Optional<DocumentEntity> deleteById(String id) {
        return documentRepository
                .findById(id)
                .map(doc -> {
                    storeManager.delete(doc);
                    documentRepository.delete(doc);

                    return doc;
                });
    }

    /**
     * Reads the specified {@link Resource pdf} and returns {@link Document documents} (1 document per PDF page).
     */
    private List<Document> readDocuments(Resource pdf) {
        final PdfDocumentReaderConfig readerConfig = PdfDocumentReaderConfig.builder()
                .withPageExtractedTextFormatter(new ExtractedTextFormatter.Builder().build())
                .build();

        final PagePdfDocumentReader documentReader = new PagePdfDocumentReader(pdf, readerConfig);

        return textSplitter.apply(documentReader.get());
    }
}
