package com.aluracursos.sentimentapi.service;

import com.aluracursos.sentimentapi.dto.SentimentResponse;
import org.springframework.stereotype.Service;

@Service
public class SentimentService {

    public SentimentResponse analyze(String text) {
        // Mock por ahora
        return new SentimentResponse("positivo", 0.87);
    }
}
