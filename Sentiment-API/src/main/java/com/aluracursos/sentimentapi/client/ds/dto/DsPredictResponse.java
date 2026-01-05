package com.aluracursos.sentimentapi.client.ds.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DsPredictResponse {

    private String label;
    //private Double confidence;
    private Double probability;
}

