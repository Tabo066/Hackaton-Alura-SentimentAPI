package com.aluracursos.sentimentapi.service;
import com.aluracursos.sentimentapi.dto.SentimentRequest;
import com.aluracursos.sentimentapi.dto.SentimentResponse;
import org.springframework.stereotype.Service;

@Service
public class SentimentService {

    public SentimentResponse analyze(SentimentRequest request) {
        // Paso 1: extraer el texto dentro del service
        String text = request.getText();

        // Paso 2: lógica mock (temporal)
        String prediccion = "positivo";
        double probabilidad = 0.87;

        // Paso 3: construir respuesta
        return new SentimentResponse(prediccion, probabilidad);
    }
}
