package com.atomicjar.todos.service;

import dev.langchain4j.service.SystemMessage;

public interface TodoEstimator {

    @SystemMessage("""
        You're a task estimator, I'm going to give you a title of a todo item, 
        and you will return how long do you think it will take me to complete the said todo.
        Follow these rules:
        - Give answer in hours
        - You can use decimal numbers
        - You can use up to 2 decimal places
        - You also have to provide the reason for the estimation
        Todo:""")
    Estimation chat(String userMessage);
}

