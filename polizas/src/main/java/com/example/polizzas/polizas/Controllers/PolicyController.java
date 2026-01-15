package com.example.polizzas.polizas.Controllers; // Ajusta a tu paquete real

import com.example.polizzas.polizas.DTO.ApiResponseDTO;
import com.example.polizzas.polizas.Services.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
Clase controladora que maneja las operaciones CRUD para las pólizas.
*/
import java.util.Map;

@RestController
@RequestMapping("/api/polizas")
@CrossOrigin(origins = "http://localhost:4200")
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    // 1. CREAR (GRABAR) - POST
    @PostMapping
    public ResponseEntity<ApiResponseDTO<Object>> crearPoliza(@RequestBody Map<String, Integer> request) {

        int idEmpleado = request.get("idEmpleado");
        int sku = request.get("sku");
        int cantidad = request.get("cantidad");

        ApiResponseDTO<Object> response = policyService.crearPoliza(idEmpleado, sku, cantidad);

        return ResponseEntity.ok(response);
    }

    // 2. CONSULTAR - GET
    @GetMapping("/{idPoliza}")
    public ResponseEntity<ApiResponseDTO<Object>> consultarPoliza(@PathVariable int idPoliza) {

        ApiResponseDTO<Object> response = policyService.consultarPoliza(idPoliza);
        return ResponseEntity.ok(response);

    }

    // 3. ACTUALIZAR - PUT
    @PutMapping
    public ResponseEntity<ApiResponseDTO<Object>> actualizarPoliza(@RequestBody Map<String, Integer> request) {
        int idPoliza = request.get("idPoliza");
        int nuevoIdEmpleado = request.get("idEmpleado");

        ApiResponseDTO<Object> response = policyService.actualizarPoliza(idPoliza, nuevoIdEmpleado);
        return ResponseEntity.ok(response);
    }

    // 4. ELIMINAR - DELETE
    @DeleteMapping("/{idPoliza}")
    public ResponseEntity<ApiResponseDTO<Object>> eliminarPoliza(@PathVariable int idPoliza) {
        ApiResponseDTO<Object> response = policyService.eliminarPoliza(idPoliza);
        return ResponseEntity.ok(response);
    }
}