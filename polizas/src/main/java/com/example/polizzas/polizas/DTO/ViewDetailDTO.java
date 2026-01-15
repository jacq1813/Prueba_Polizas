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
public class ViewDetailDTO {

    @JsonProperty("Poliza")
    private PolicyResponseDTO poliza;

    @JsonProperty("Empleado")
    private EmployeeResponseDTO empleado;

    @JsonProperty("Detalle Articulo")
    private InventoryResponseDTO detalleArticulo;
}