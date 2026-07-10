package com.angel.almacen.dto.ventas;

import com.angel.almacen.dto.socursales.SucursalResponse;

import java.math.BigDecimal;
import java.util.List;


public record VentaResponse(
        Long id,
        String fecha,
        String estado,
        SucursalResponse sucursal,
        List<DetalleVentaResponse> detalles,
        BigDecimal total
) {

}
