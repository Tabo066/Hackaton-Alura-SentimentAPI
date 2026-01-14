package com.aluracursos.sentimentapi.controller;

import com.aluracursos.sentimentapi.client.ds.DsClient;
import com.aluracursos.sentimentapi.dto.SentimentResponse;
import com.aluracursos.sentimentapi.service.SentimentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.aluracursos.sentimentapi.dto.SentimentRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Controller slice test.
 * Does not load filters, WebClient or WireMock.
 */
@WebMvcTest(SentimentController.class)
class SentimentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    //@MockBean
    @MockBean
    private SentimentService sentimentService;

    @MockBean
    private DsClient dsClient;

    //JSON inline
    @Test
    void shouldReturn200WhenRequestIsValid() throws Exception {
        SentimentResponse mockResponse = new SentimentResponse("positivo", 0.87);

        when(sentimentService.analyze(any(SentimentRequest.class)))
                .thenReturn(mockResponse);
        mockMvc.perform(post("/analyze")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""" 
                                {
                                  "text": "Este producto es excelente y funciona muy bien" 
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prediccion").value("positivo"))
                .andExpect(jsonPath("$.probabilidad").value(0.87));
    }

    //JSON inline
    @Test
    void shouldReturn400WhenTextTooShort() throws Exception {

        mockMvc.perform(post("/analyze")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                      "text": "short"
                                    }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.text")
                        .value("El texto debe tener al menos 10 caracteres"));
    }
}