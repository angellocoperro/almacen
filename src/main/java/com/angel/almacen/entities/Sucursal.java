package com.angel.almacen.entities;

import com.angel.almacen.utils.StringCustomUrils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "SUCURSALES")
public class Sucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SUCURSAL")
    private Long id;

    @Column(name = "NOMBRE", length = 50, unique = true, nullable = false)
    private String nombre;

    @Column(name = "DIRECCION", length = 150, nullable = false)
    private String direccion;

    public void actualizar(String nombre, String direccion) {
        validarDatos(nombre, direccion);

        this.nombre = nombre.trim();
        this.direccion = direccion.trim();
    }

    private void validarDatos(String nombre, String direccion) {
        StringCustomUrils.validarTamanio(nombre, 5, 50,
                "El nombre es requerido y debe tener entre 5 y 50 caracteres");

        StringCustomUrils.validarTamanio(direccion, 10, 150,
                "La dirección es requerida y debe tener entre 10 y 150 caracteres");
    }

}
