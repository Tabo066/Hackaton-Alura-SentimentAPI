package com.aluracursos.sentimentapi.client.ds.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DsPredictRequest {

    private String text;
}
