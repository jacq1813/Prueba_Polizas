
package com.example.polizzas.polizas.Models;

import java.time.LocalDate;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "polizas")
@Entity

public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int IdPoliza;

    private int Cantidad;
    private LocalDate Fecha;

    @ManyToOne
    @JoinColumn(name = "empleadogenero", referencedColumnName = "idEmpleado")
    private Employee empleado;

    @ManyToOne
    @JoinColumn(name = "sku", referencedColumnName = "sku")
    private Inventory inventario;

    private boolean Eliminado;

}
