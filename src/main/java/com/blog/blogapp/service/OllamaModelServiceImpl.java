package com.blog.blogapp.service;

import com.blog.blogapp.constant.Constants;
import com.blog.blogapp.model.OllamaResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@Component
public class OllamaModelServiceImpl implements OllamaModelSercice{

    @Override
    public String generateResponse(String prompt, String model) throws JsonProcessingException {
        String x = "";

        HttpClient httpClient = HttpClient.newHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> values = new HashMap<>();
        values.put("model", model);
        values.put("prompt", prompt);


        String requestBody = objectMapper.writeValueAsString(values);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Constants.API_URL))
                .header("Content-Type", Constants.CONTENT_TYPE_JSON)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();


        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String[] jsonStrings = response.body().split("\n");

            for (int i = 0; i < jsonStrings.length - 1; i++) {
                OllamaResponse ol = objectMapper.readValue(jsonStrings[i], OllamaResponse.class);

                x +=ol.getResponse();
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("Error during HTTP request: " + e.getMessage());
        }

        return x;

    }
}
