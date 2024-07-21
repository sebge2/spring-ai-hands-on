package be.sgerard.springai.configuration;

import org.springframework.ai.autoconfigure.vectorstore.pgvector.PgVectorStoreAutoConfiguration;
import org.springframework.ai.autoconfigure.vectorstore.pgvector.PgVectorStoreProperties;
import org.springframework.ai.mistralai.MistralAiEmbeddingModel;
import org.springframework.ai.vectorstore.PgVectorStore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @see PgVectorStoreAutoConfiguration
 */
@Configuration
public class VectorDbConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public PgVectorStore vectorStore(JdbcTemplate jdbcTemplate, MistralAiEmbeddingModel embeddingModel, PgVectorStoreProperties properties) {
        boolean initializeSchema = properties.isInitializeSchema();
        return new PgVectorStore(jdbcTemplate, embeddingModel, properties.getDimensions(), properties.getDistanceType(), properties.isRemoveExistingVectorStoreTable(), properties.getIndexType(), initializeSchema);
    }
}
