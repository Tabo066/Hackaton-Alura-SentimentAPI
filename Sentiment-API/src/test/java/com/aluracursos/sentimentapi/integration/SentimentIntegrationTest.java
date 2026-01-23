package com.aluracursos.sentimentapi.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWireMock(port = 0)
@AutoConfigureMockMvc
public class SentimentIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deberiaRetornarSentimientoPositivo_cuandoDsResponde200() throws Exception{
        // 1️⃣ Simulamos la respuesta del DS
        //stubFor(com.github.tomakehurst.wiremock.client.WireMock.post(urlEqualTo("/predict"))
        //stubFor(com.github.tomakehurst.wiremock.client.WireMock.post(urlEqualTo("/analyze"))
        stubFor(com.github.tomakehurst.wiremock.client.WireMock.post(urlEqualTo("/v2/analyze"))
                 .willReturn(aResponse()
                 .withStatus(200)
                 .withHeader("Content-Type","application/json")
                         .withBody("""
                         {
                            "prediccion":"positivo",
                            "probabilidad":0.67                           
                         }
                         """)));
                        /* .withBody("""
                         {
                           "texto":"Excelente servicio y muy rapido",
                            "sentimiento":"positivo",
                            "probabilidad":0.67,
                           "modelo":"sentimiento-es-v2"
                         }
                         """)));*/

        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/analyze")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("X-Trace-Id", "trace-test-500")
                                .content("""
                                         {
                                           "text": "Excelente servicio y muy rapido"
                                         }
                                         """))
                // 3️⃣ Validaciones
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prediccion").value("positivo"))
                .andExpect(jsonPath("$.probabilidad").value(0.67))
                .andExpect(header().exists("X-Trace-Id"));
    }

    @Test
    void deberiaRetornar503_cuandoDsFalla() throws Exception {

        //stubFor(post(urlEqualTo("/predict"))
        //stubFor(post(urlEqualTo("/analyze"))
        stubFor(post(urlEqualTo("/v2/analyze"))

                .willReturn(aResponse()
                        .withStatus(500)
                )
        );

        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/analyze")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("X-Trace-Id", "trace-test-500")
                                .content("""
                                         {
                                           "text": "Excelente servicio"
                                         }
                                         """))
                //.andExpect(status().isServiceUnavailable())
                .andExpect(status().isBadGateway())
                .andExpect(header().string("X-Trace-Id", "trace-test-500"))
                .andExpect(jsonPath("$.message")
                        .value("Servicio de análisis no disponible. Intente más tarde."));
    }
}


