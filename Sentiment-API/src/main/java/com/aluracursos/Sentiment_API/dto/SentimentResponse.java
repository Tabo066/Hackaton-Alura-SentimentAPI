package com.aluracursos.Sentiment_API.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SentimentResponse {
    private String prevision;
    private double probabilidad;
}
