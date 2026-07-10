package com.angel.almacen.dto.ventas;

import com.angel.almacen.entities.Sucursal;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.List;

public record VentaRequest(


        @NotNull(message = "La fecha es requerida")
        String fecha,

        @NotNull(message = "El id de la sucursal es requerida")
        @Positive(message = "El id de la sucursal deber ser positivo")
        Long idSucursal,

        @NotEmpty(message = "La lista de productos es requerida y no debe estar vacia")
        List<@Valid DetalleVentaRequest> productos

) {
}
