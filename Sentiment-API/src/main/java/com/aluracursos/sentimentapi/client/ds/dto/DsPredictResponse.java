package com.aluracursos.sentimentapi.client.ds.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DsPredictResponse {

    @JsonProperty("texto")
    private String label;
    private String sentimiento;
    //private Double confidence;
    @JsonProperty("probabilidad")
    private Double probability;
    private String modelo;
}

