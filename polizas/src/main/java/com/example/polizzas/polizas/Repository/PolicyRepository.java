package com.example.polizzas.polizas.Repository;

import com.example.polizzas.polizas.DTO.ViewDetailDTO;
import com.example.polizzas.polizas.Mapper.PolicyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/*
Clase encargada de interactuar con la base de datos para operaciones relacionadas con pólizas
*/

@Repository
public class PolicyRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final PolicyMapper polizaMapper = new PolicyMapper();

    public int guardarPoliza(int idEmpleado, int sku, int cantidad) {

        return jdbcTemplate.execute(
                "call sp_CrearPoliza(?, ?, ?, ?, ?)",
                (org.springframework.jdbc.core.CallableStatementCallback<Integer>) cs -> {

                    cs.setInt(1, idEmpleado);
                    cs.setInt(2, sku);
                    cs.setInt(3, cantidad);
                    cs.setDate(4, new java.sql.Date(System.currentTimeMillis()));

                    cs.setInt(5, 0);

                    cs.registerOutParameter(5, java.sql.Types.INTEGER);

                    cs.execute();

                    return cs.getInt(5);
                });
    }

    public ViewDetailDTO consultarPoliza(int idPoliza) {
        String sql = "SELECT * FROM sp_ConsultarPoliza(?)";

        return jdbcTemplate.queryForObject(sql, polizaMapper, idPoliza);
    }

    public void actualizarPoliza(int idPoliza, int nuevoIdEmpleado) {
        String sql = "CALL sp_ActualizarPoliza(?, ?)";
        jdbcTemplate.update(sql, idPoliza, nuevoIdEmpleado);
    }

    public void eliminarPoliza(int idPoliza) {
        String sql = "CALL sp_EliminarPoliza(?)";
        jdbcTemplate.update(sql, idPoliza);
    }
}