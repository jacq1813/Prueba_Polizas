package com.example.polizzas.polizas.Mapper;

import com.example.polizzas.polizas.DTO.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.*;

/*
Clase que mapea los resultados de las consulta SQL a un objeto ViewDetailDTO.
 */

@Component
public class PolicyMapper implements RowMapper<ViewDetailDTO> {

        @Override
        public ViewDetailDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

                PolicyResponseDTO poliza = PolicyResponseDTO.builder()
                                .idPoliza(rs.getInt("id_poliza"))
                                .cantidad(rs.getInt("cantidad_poliza"))
                                .build();

                EmployeeResponseDTO empleado = EmployeeResponseDTO.builder()
                                .nombre(rs.getString("nombre_empleado"))
                                .apellido(rs.getString("apellido_empleado"))
                                .build();

                InventoryResponseDTO articulo = InventoryResponseDTO.builder()
                                .sku(rs.getInt("sku_articulo"))
                                .nombre(rs.getString("nombre_articulo"))
                                .build();

                return ViewDetailDTO.builder()
                                .poliza(poliza)
                                .empleado(empleado)
                                .detalleArticulo(articulo)
                                .build();
        }
}