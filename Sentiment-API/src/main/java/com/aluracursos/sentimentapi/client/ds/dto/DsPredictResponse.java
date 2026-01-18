package com.aluracursos.sentimentapi.client.ds.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DsPredictResponse {
    private String texto;
    //private String sentimiento;
    private String prediccion;
    private Double probabilidad;
    private String modelo;
    @JsonProperty("trace_id")
    private String traceId;
}

