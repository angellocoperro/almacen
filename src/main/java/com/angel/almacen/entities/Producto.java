package com.angel.almacen.entities;

import com.angel.almacen.enums.Categoria;
import com.angel.almacen.utils.StringCustomUrils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "PRODUCTOS")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRODUCTO")
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 30)
    private String nombre;

    @Column(name = "CATEGORIA", nullable = false)
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @Column(name = "PRECIO", nullable = false)
    private BigDecimal precio;

    @Column(name = "CANTIDAD", nullable = false)
    private Integer cantidad;

    public void aumentarCantidad(int cantidad){
        if(cantidad < 0)
            throw new IllegalArgumentException("El cantidad debe ser positiva");
        this.cantidad += cantidad;
    }


    // FALTA ESTO
    public void actualizar(String nombre, Categoria categoria, BigDecimal precio, Integer cantidad){
        validarDatos(nombre, categoria, precio, cantidad);

        this.nombre = nombre.trim();
        this.categoria = categoria;
        this.precio = precio;
        this.cantidad = cantidad;
    }


    public void descontarCantidad(int cantidad){
        if(cantidad < 0 || cantidad > this.cantidad)
            throw new IllegalArgumentException("El cantidad debe ser positiva y menor o igual a la cantidad actual");
        this.cantidad -= cantidad;
    }

    private void validarDatos(String nombre, Categoria categoria, BigDecimal precio, Integer cantidad) {

        StringCustomUrils.validarTamanio(nombre, 5, 30,
                "El nombre es requerido y debe tener entre 5 y 30 caracteres");

        if(categoria == null)
            throw new IllegalArgumentException("El categoria es requerida");

        if (precio == null || precio.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio es requerido y debe ser positivo");
        }

        if (cantidad == null || cantidad < 0) {
            throw new IllegalArgumentException("La cantidad es requerida y debe ser positiva");
        }
    }
}// FIN DE LA CLASE PRODUCTOS
