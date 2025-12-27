package com.aluracursos.sentimentapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SentimentResponse {
    private String prediccion;
    private double probabilidad;
}
