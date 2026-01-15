package com.example.polizzas.polizas.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponseDTO {

    @JsonProperty("SKU")
    private int sku;

    @JsonProperty("Nombre")
    private String nombre;
}