package com.angel.almacen.dto.productos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductoRequest(

        @NotBlank(message = "El nombre es requerido")
        @Size(min = 5, max = 30, message = "el nombre es requerido y debe tener en 5 y 30 caracteres")
        String nombre,

        @NotBlank(message = "la categoria es requerido")
        String categoria,

        @NotNull(message = "el precio es requerido")
        @Positive(message = "El precio de debe ser positivo")
        BigDecimal precio,

        @NotNull(message = "la cantidad es requerida")
        @Positive(message = "la cantidad de debe ser positiva")
        Integer cantidad
) {


}
