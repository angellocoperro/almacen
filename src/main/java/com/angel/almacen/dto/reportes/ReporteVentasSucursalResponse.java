package com.angel.almacen.dto.reportes;

import java.math.BigDecimal;

public record ReporteVentasSucursalResponse(
        Long idSucursal,
        String nombreSucursal,
        BigDecimal totalFacturado,
        Long cantidadProductosVendidos
) {
}
