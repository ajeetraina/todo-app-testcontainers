package com.atomicjar.todos;

import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.ollama.OllamaContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class ContainersConfig {

    @Bean
    @ServiceConnection
    @RestartScope
    PostgreSQLContainer<?> postgreSQLContainer(){
        return new PostgreSQLContainer<>("pgvector/pgvector:pg16").withLabel("com.testcontainers.desktop.service", "vector");
    }

    @Bean
    OllamaContainer ollama(DynamicPropertyRegistry properties) {
        OllamaContainer ollamaContainer = new OllamaContainer(
                DockerImageName.parse("ilopezluna/llama3.2:0.3.12-3b")
                        .asCompatibleSubstituteFor("ollama/ollama")
        ).withReuse(true);

        properties.add("langchain4j.ollama.chat-model.base-url", ollamaContainer::getEndpoint);
        properties.add("langchain4j.ollama.chat-model.model-name", () -> "llama3.2:3b");

        return ollamaContainer;

//            String ollamaWithModelImagename = "ollama-with-llama3.1:8b";
//        try {
//            return new OllamaContainer(
//                    DockerImageName.parse(ollamaWithModelImagename)
//                            .asCompatibleSubstituteFor("ollama/ollama")
//            );
//        }
//        catch (Exception e) {
//            OllamaContainer ollamaContainer = new OllamaContainer("ollama/ollama:0.3.12");
//            ollamaContainer.start();
//
//            ollamaContainer.execInContainer("ollama", "pull", "llama3.1:8b");
//            ollamaContainer.commitToImage(ollamaWithModelImagename);
//            return ollamaContainer;
//        }
    }

}
