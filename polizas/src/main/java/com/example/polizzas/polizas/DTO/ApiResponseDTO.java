package com.example.polizzas.polizas.DTO;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({ "Meta", "Data" })
public class ApiResponseDTO<T> {
    private MetaDTO Meta;
    private T Data;

    public ApiResponseDTO(String status, T data) {
        this.Meta = new MetaDTO(status);
        this.Data = data;
    }
}