package com.aluracursos.sentimentapi.service;
import com.aluracursos.sentimentapi.client.ds.DsClient;
import com.aluracursos.sentimentapi.client.ds.dto.DsPredictRequest;
import com.aluracursos.sentimentapi.client.ds.dto.DsPredictResponse;
import com.aluracursos.sentimentapi.dto.SentimentRequest;
import com.aluracursos.sentimentapi.dto.SentimentResponse;
import org.springframework.stereotype.Service;

@Service
public class SentimentService {

    //conectando DsClient con SentimentService
    private final DsClient dsClient;

    public SentimentService(DsClient dsClient){
        this.dsClient = dsClient;
    }

    public SentimentResponse analyze(SentimentRequest request){
        DsPredictResponse dsResponse =
                dsClient.predict(new DsPredictRequest(request.getText()));
        return new SentimentResponse(
                dsResponse.getLabel(),
                //dsResponse.getSentimiento(),
                dsResponse.getProbability()
        );
    }
}

//sin conectar a DSClient
    /*public SentimentResponse analyze(SentimentRequest request) {
        // Paso 1: extraer el texto dentro del service
        String text = request.getText();

        // Paso 2: lógica mock (temporal)
        String prediccion = "positivo";
        double probabilidad = 0.87;

        // Paso 3: construir respuesta
        return new SentimentResponse(prediccion, probabilidad);
    }*/


/*    // ⚠️ SOLO PARA PRUEBA DE LOGS 500
public SentimentResponse analyze(SentimentRequest request) {

        // ⚠️ SOLO PARA PRUEBA DE LOGS 500
        throw new RuntimeException("Error interno de prueba");

    }
*/