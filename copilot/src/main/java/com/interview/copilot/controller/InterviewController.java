package com.interview.copilot.controller;



import com.interview.copilot.config.ResumeParser;
import com.interview.copilot.service.GeminiService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
@RequestMapping("/api/interview")
@RequiredArgsConstructor
public class InterviewController {
    private final GeminiService geminiService;
    private final ResumeParser resumeParser;
    private final StringRedisTemplate redisTemplate;

    @PostMapping("/ask")
    public Mono<ResponseEntity<String>> askQuestion(
            @RequestParam String question,
            @RequestParam String sessionId) {

        String resumeContext = redisTemplate.opsForValue().get(sessionId);
        if (resumeContext == null) {
            resumeContext = "No resume context provided";
        }

        String prompt = String.format(
                "Act as a coding interview coach. Answer concisely in this format:\n" +
                        "Approach: [strategy]\nSolution Code: [code]\nComplexity: [analysis]\n\n" +
                        "Resume Context: %s\nQuestion: %s",
                resumeContext, question
        );

        return geminiService.generateAnswer(prompt)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error generating answer: " + e.getMessage())
                ));
    }

    @PostMapping("/upload-resume")
    public ResponseEntity<String> uploadResume(
            @RequestParam("file") MultipartFile file,
            @RequestParam String sessionId) throws IOException {

        String text = resumeParser.extractTextFromPdf(file);
        redisTemplate.opsForValue().set(sessionId, text);
        return ResponseEntity.ok("Resume context updated successfully");
    }

    @GetMapping
    public String test(){
        return "API is working...";
    }

}