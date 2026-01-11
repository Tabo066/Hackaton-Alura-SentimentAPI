package com.aluracursos.sentimentapi.service;
import com.aluracursos.sentimentapi.client.ds.DsClient;
import com.aluracursos.sentimentapi.client.ds.dto.DsPredictRequest;
import com.aluracursos.sentimentapi.client.ds.dto.DsPredictResponse;
import com.aluracursos.sentimentapi.dto.SentimentRequest;
import com.aluracursos.sentimentapi.dto.SentimentResponse;
import com.aluracursos.sentimentapi.exception.DsServiceUnavailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j   //inyectando el logger
@Service
public class SentimentService {

    //conectando DsClient con SentimentService
    private final DsClient dsClient;

    public SentimentService(DsClient dsClient){
        this.dsClient = dsClient;
    }

    public SentimentResponse analyze(SentimentRequest request){
        try{
            DsPredictResponse dsResponse =
                    dsClient.predict(new DsPredictRequest(request.getText()));
            //logeo del trace_id
            log.info("DS response received. trace_id={}", dsResponse.getTraceId());
            return mapToSentimentResponse(dsResponse);
        } catch (Exception ex) {
            log.error("Error calling DS service", ex);
            throw new DsServiceUnavailableException(
                    "Error al comunicarse con el servicio DS",
                    ex
            );
        }
    }

    private SentimentResponse mapToSentimentResponse( DsPredictResponse dsResponse){
        return new SentimentResponse( dsResponse.getSentimiento(),
                                      dsResponse.getProbabilidad()
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