package com.example.polizzas.polizas.Models;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Empleado")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee {
    @Id
    private int IdEmpleado;
    private String Nombre;
    private String Apellido;
    private String Puesto;
}
