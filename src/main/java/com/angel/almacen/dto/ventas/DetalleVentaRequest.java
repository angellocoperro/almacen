package com.angel.almacen.dto.ventas;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DetalleVentaRequest(
        @NotNull(message = "El id del producto es requerido")
        @Positive(message = "El id del producto debe ser positivo")
        Long idProducto,

        @NotNull(message = "La cantidad del producto es requerida")
        @Positive(message = "La cantidad del producto debe ser positiva")
        Integer cantidadProducto
) {
}
