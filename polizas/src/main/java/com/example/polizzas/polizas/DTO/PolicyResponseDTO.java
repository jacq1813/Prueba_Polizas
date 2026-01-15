package com.example.polizzas.polizas.DTO;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PolicyResponseDTO {

    @JsonProperty("IDPoliza")
    private int idPoliza;

    @JsonProperty("Cantidad")
    private int cantidad;
}