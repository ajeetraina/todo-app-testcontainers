package com.atomicjar.todos.service;

import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@AiService
public interface TodoEstimator {

    @SystemMessage("You're a task estimator, I'm going to give you a title of a todo item, and you will return how long do you think it will take me to complete the said todo. Give answer in hours. Only respond with the estimate, no other text or explanation please. Todo:")
    String chat(String userMessage);
}


