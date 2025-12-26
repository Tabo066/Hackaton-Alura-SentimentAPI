package com.aluracursos.sentimentapi.controller;

import com.aluracursos.sentimentapi.dto.SentimentRequest;
import com.aluracursos.sentimentapi.dto.SentimentResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/sentiment")
@RequestMapping("/analyze")
@Slf4j
public class SentimentController {

    @PostMapping
    public ResponseEntity<SentimentResponse> analyzeSentiment(@Valid @RequestBody SentimentRequest request) {
        log.info("Recibido texto: {}", request.getText());

        // Por ahora, devolvemos una predicción mock
        SentimentResponse response = new SentimentResponse("positivo", 0.87);

        log.info("Respuesta enviada: {}", response);
        return ResponseEntity.ok(response);
    }
}
