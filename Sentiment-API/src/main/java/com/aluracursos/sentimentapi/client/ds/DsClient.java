package com.aluracursos.sentimentapi.client.ds;

import com.aluracursos.sentimentapi.client.ds.dto.DsPredictRequest;
import com.aluracursos.sentimentapi.client.ds.dto.DsPredictResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;

@Component
@Slf4j
public class DsClient {

    private final WebClient webClient;

    public DsClient() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8000")
                .build();
    }

    public DsPredictResponse predict(DsPredictRequest request) {

        try{
            return webClient.post()
                    .uri("/predict")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(DsPredictResponse.class)
                    .timeout(Duration.ofSeconds(5))
                    .block();
        }
        catch(WebClientRequestException ex){
            //Error de conexion / timeout
            log.error("Error de conexion con servicio DS", ex);
            throw new RuntimeException("Servicio de analisis no disponible");
        }
        catch(WebClientResponseException ex){
            //Error HTTP(4xx / 5xx desde DS
            log.error("Error de respuesta desde DS:status={}", ex.getStatusCode(), ex);
            throw new RuntimeException("Error al procesar analisis de sentimiento");
        }
    }
}



/* se borro WebclientConfig, por que estaba en la ruta test
@Component
public class DsClient {

    private final WebClient dsWebClient;

    public DsClient(@Qualifier("dsWebClient") WebClient dsWebClient){
        this.dsWebClient = dsWebClient;
    }*/



    /* TEST
   public DsPredictResponse predict(DsPredictRequest request){
        // ⚠️ implementación real en el siguiente paso
        return null;
    }
}*/
