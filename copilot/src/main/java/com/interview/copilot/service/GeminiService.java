package com.interview.copilot.service;

import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GeminiService {
    private final WebClient webClient;
    private static final String API_KEY = "YOUR_API_KEY";
    private static final String BASE_URL = "API_URL";
    public GeminiService() {
        this.webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

  public Mono<String> generateAnswer(String prompt) {
        GeminiRequest request = new GeminiRequest(
                List.of(new Content(List.of(new Part(prompt))))
        );

        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("key", API_KEY)
                        .build())
                .bodyValue(request)
                .retrieve()
                .bodyToMono(GeminiResponse.class)
                .map(response -> response.getCandidates().get(0).getContent().getParts().get(0).getText());
    }


    // DTO Classes
    @Data
    static class GeminiRequest {
        private final List<Content> contents;
    }

    @Data
    static class Content {
        private final List<Part> parts;
    }

    @Data
    static class Part {
        private final String text;
    }

    @Data
    static class GeminiResponse {
        private List<Candidate> candidates;
    }

    @Data
    static class Candidate {
        private Content content;
    }
}