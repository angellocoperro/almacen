package com.angel.almacen.dto.socursales;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SucursalRequest(

        @NotBlank(message = "El nombre es requerido")
        @Size(min = 5, max = 50, message = "el nombre es requerido y debe tener en 5 y 50 caracteres")
        String nombre,

        @NotBlank(message = "La direccion es requerida")
        @Size(min = 10, max = 150, message = "el nombre es requerido y debe tener en 10 y 150 caracteres")
        String direccion

) {
}
