package com.atomicjar.todos.service;

import dev.langchain4j.model.output.structured.Description;

public class Estimation {

    @Description("The reason for the estimation")
    public String reason;

    @Description("The estimated hours")
    public double hours;
}
