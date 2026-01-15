package com.example.polizzas.polizas.Models;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Inventario")
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Inventory {
    @Id
    private int SKU;
    private String Nombre;
    private int Cantidad;

}
