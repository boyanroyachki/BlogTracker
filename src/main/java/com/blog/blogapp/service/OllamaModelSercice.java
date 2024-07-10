package com.blog.blogapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface OllamaModelSercice {
    public String generateResponse(String prompt, String model) throws JsonProcessingException;
}
