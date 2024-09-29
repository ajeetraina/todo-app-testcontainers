package com.atomicjar.todos.config;

import com.atomicjar.todos.service.TodoEstimator;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIServicesConfiguration {

    @Bean
    @Qualifier("estimator")
    TodoEstimator estimator(ChatLanguageModel chatLanguageModel) {
        return AiServices.builder(TodoEstimator.class)
                .chatLanguageModel(chatLanguageModel)
                .build();
    }

    @Bean
    @Qualifier("ragged")
    TodoEstimator ragged(ChatLanguageModel chatLanguageModel, RetrievalAugmentor retrievalAugmentor) {
        return AiServices.builder(TodoEstimator.class)
                .chatLanguageModel(chatLanguageModel)
                .retrievalAugmentor(retrievalAugmentor)
                .build();
    }
}
