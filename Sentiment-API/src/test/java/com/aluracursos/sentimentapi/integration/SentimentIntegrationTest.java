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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
         stubFor(com.github.tomakehurst.wiremock.client.WireMock.post(urlEqualTo("/predict"))
                 .willReturn(aResponse()
                 .withStatus(200)
                 .withHeader("Content-Type","application/json")
                 .withBody("""
                         {
                            "texto":"Excelente servicio y muy rapido",
                            "sentimiento":"positivo",
                            "probabilidad":0.67,
                            "modelo":"sentimiento-es-v2"
                         }
                         """)));

         //aqui ira el MockMvc / WebTestClient (siguiente paso)
        // 2️⃣ Llamamos a TU API real
        mockMvc.perform(post("/analyze")
                        .contentType("application/json")
                        .content("""
                                {
                                  "text": "Excelente servicio y muy rápido"
                                }
                                """))

                // 3️⃣ Validaciones
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$.sentimiento").value("positivo"))
                .andExpect(jsonPath("$.prediccion").value("Excelente servicio y muy rapido"))
                .andExpect(jsonPath("$.probabilidad").value(0.67));
    }


    @Test
    void deberiaRetornar502_cuandoDsNoEstaDisponible() throws Exception {
        //No hay stubFor(...) simula que DS está “caído”.

        String requestBody = """
        {
          "text": "Excelente servicio"
        }
        """;

        mockMvc.perform(post("/analyze")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadGateway())
                .andExpect(jsonPath("$.error").value("Bad Gateway"))
                .andExpect(jsonPath("$.message")
                        .value("Servicio de análisis no disponible. Intente más tarde."));
    }

}


