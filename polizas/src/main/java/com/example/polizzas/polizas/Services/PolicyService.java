package com.example.polizzas.polizas.Services;

import com.example.polizzas.polizas.DTO.*;
import com.example.polizzas.polizas.Repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class PolicyService {

    @Autowired
    private PolicyRepository repository;

    // 1. CREAR (GRABAR)
    public ApiResponseDTO<Object> crearPoliza(int idEmpleado, int sku, int cantidad) {
        try {
            int idGenerado = repository.guardarPoliza(idEmpleado, sku, cantidad);

            Map<String, String> mensaje = new HashMap<>();
            mensaje.put("IDMensaje", "Se generó correctamente la poliza " + idGenerado);

            Map<String, Object> data = new HashMap<>();
            data.put("Mensaje", mensaje);

            return new ApiResponseDTO<>("OK", data);

        } catch (Exception e) {
            Map<String, String> errorData = new HashMap<>();
            errorData.put("Mensaje", "Ha ocurrido un error en los grabados de póliza: " + e.getMessage());

            return new ApiResponseDTO<>("FAILURE", errorData);
        }
    }

    // 2. CONSULTAR
    public ApiResponseDTO<Object> consultarPoliza(int idPoliza) {
        try {
            ViewDetailDTO detalle = repository.consultarPoliza(idPoliza);
            return new ApiResponseDTO<>("OK", detalle); // 'detalle' ya tiene la estructura anidada gracias al Mapper

        } catch (Exception e) {
            Map<String, String> errorData = new HashMap<>();
            errorData.put("Mensaje", "Ha ocurrido un error al consultar la póliza.");
            return new ApiResponseDTO<>("FAILURE", errorData);
        }
    }

    // 3. ACTUALIZAR
    public ApiResponseDTO<Object> actualizarPoliza(int idPoliza, int idEmpleado) {
        try {
            repository.actualizarPoliza(idPoliza, idEmpleado);

            Map<String, String> mensaje = new HashMap<>();
            mensaje.put("IDMensaje", "Se actualizó correctamente la poliza " + idPoliza);

            Map<String, Object> data = new HashMap<>();
            data.put("Mensaje", mensaje);

            return new ApiResponseDTO<>("OK", data);

        } catch (Exception e) {
            Map<String, String> errorData = new HashMap<>();
            errorData.put("Mensaje", "Ha ocurrido un error al intentar actualizar la póliza: " + e.getMessage());
            return new ApiResponseDTO<>("FAILURE", errorData);
        }
    }

    // 4. ELIMINAR
    public ApiResponseDTO<Object> eliminarPoliza(int idPoliza) {
        try {
            repository.eliminarPoliza(idPoliza);

            Map<String, String> mensaje = new HashMap<>();
            mensaje.put("IDMensaje", "Se eliminó correctamente la poliza " + idPoliza);

            Map<String, Object> data = new HashMap<>();
            data.put("Mensaje", mensaje);

            return new ApiResponseDTO<>("OK", data);

        } catch (Exception e) {
            Map<String, String> errorData = new HashMap<>();
            errorData.put("Mensaje", "Ha ocurrido un error al intentar eliminar la póliza.");
            return new ApiResponseDTO<>("FAILURE", errorData);
        }
    }
}