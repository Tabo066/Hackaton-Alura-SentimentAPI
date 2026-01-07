package com.aluracursos.sentimentapi.client.ds;

import com.aluracursos.sentimentapi.client.ds.dto.DsPredictRequest;
import com.aluracursos.sentimentapi.client.ds.dto.DsPredictResponse;
import com.aluracursos.sentimentapi.exception.DsServiceUnavailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import java.time.Duration;

@Slf4j
@Component
public class DsClient {

    private final WebClient webClient;

   /* public DsClient() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8000")
                .build();
    }*/

    public DsClient(
            WebClient.Builder webClientBuilder,
            @Value("${ds.api.base-url}") String baseUrl
    ) {
        this.webClient = webClientBuilder
                .baseUrl(baseUrl)
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
        }catch(WebClientRequestException ex){
            //DS caido / timeout / error de red
            log.error("Error de conexion o timeout al llamar a DS", ex);
            throw new DsServiceUnavailableException(
                         "Servicio de analisis NO disponible",
                          ex
                      );
        }catch(WebClientResponseException ex){
            //DS respondio con error HTTP(4xx / 5xx)
            log.error("DS respondio con error HTTP. status={}, body={}",
                       ex.getStatusCode(),
                       ex.getResponseBodyAsString(),
                       ex
                      );
            throw new DsServiceUnavailableException("Servicio de analisis NO disponible",
                                                     ex
                                                   );
        }
    }
}




