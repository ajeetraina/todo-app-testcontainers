package com.atomicjar.todos.config;

import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.injector.DefaultContentInjector;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.pgvector.PgVectorEmbeddingStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.List;

@Configuration
public class EmbeddingsConfiguration {

    @Bean
    EmbeddingStore embeddingStore(JdbcTemplate jdbcTemplate) throws SQLException {
        //TODO how to get the connection URL from @ServiceConnection?
        String datasourceUrl = jdbcTemplate.getDataSource().getConnection().getMetaData().getURL();
        String host = datasourceUrl.split("//")[1].split(":")[0];
        String port = datasourceUrl.split(":")[3].split("/")[0];
        String database = datasourceUrl.split("/")[3].split("\\?")[0];

        return PgVectorEmbeddingStore.builder()
                .host(host)
                .port(Integer.valueOf(port))
                .database(database)
                .user("test")
                .password("test")
                .table("embeddings")
                .dimension(384)
                .build();
    }

    @Bean
    EmbeddingModel embeddingModel() {
        return new AllMiniLmL6V2EmbeddingModel();
    }

    @Bean
    EmbeddingStoreIngestor embeddingStoreIngestor(EmbeddingStore embeddingStore, EmbeddingModel embeddingModel) {
        return EmbeddingStoreIngestor.builder()
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();
    }

    @Bean
    RetrievalAugmentor retrievalAugmentor(EmbeddingStore embeddingStore, EmbeddingModel embeddingModel) {
        return DefaultRetrievalAugmentor
                .builder()
                .contentRetriever(EmbeddingStoreContentRetriever.builder()
                        .embeddingStore(embeddingStore)
                        .embeddingModel(embeddingModel)
                        .maxResults(1)
                        .minScore(0.75)
                        .build())
                .contentInjector(DefaultContentInjector.builder()
                        .metadataKeysToInclude(List.of("real duration"))
                        .build())
                .build();
    }
}
