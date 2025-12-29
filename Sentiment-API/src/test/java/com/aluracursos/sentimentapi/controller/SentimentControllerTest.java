package com.aluracursos.sentimentapi.controller;

import com.aluracursos.sentimentapi.dto.SentimentResponse;
import com.aluracursos.sentimentapi.service.SentimentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.aluracursos.sentimentapi.dto.SentimentRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest
//@AutoConfigureMockMvc
@WebMvcTest(SentimentController.class)
class SentimentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SentimentService sentimentService;

    //JSON inline
    @Test void shouldReturn200WhenRequestIsValid() throws Exception {
        SentimentResponse mockResponse = new SentimentResponse("positivo", 0.87);

        when(sentimentService.analyze(any(SentimentRequest.class)))
                .thenReturn(mockResponse); mockMvc.perform(post("/analyze")
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

    /*@Test
    void shouldReturn200WhenValidInput() throws Exception {
        SentimentRequest request = new SentimentRequest();
        request.setText("Me encanta este producto, es excelente");

        when(sentimentService.analyze(any(SentimentRequest.class)))
                .thenReturn(new SentimentResponse("positivo", 0.87));

        mockMvc.perform(post("/analyze")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$.prediccion").value("Positivo"))
                .andExpect(jsonPath("$.prediccion").value("positivo"))
                .andExpect(jsonPath("$.probabilidad").value(0.87));
    }*/

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
                        .value("El texto debe tener entre 10 y 500 caracteres"));
    }

    /*@Test
    void shouldReturn400WhenTextTooShort() throws Exception {
        SentimentRequest request = new SentimentRequest();
        request.setText("short");

        //mockMvc.perform(post("/sentiment")
        mockMvc.perform(post("/analyze")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.text")
                .value("El texto debe tener entre 10 y 500 caracteres"));
    }*/
}