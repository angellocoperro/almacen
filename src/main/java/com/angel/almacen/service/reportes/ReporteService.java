package com.angel.almacen.service.reportes;

import com.angel.almacen.dto.reportes.ReporteVentasSucursalResponse;

import java.util.List;

public interface ReporteService {

    List<ReporteVentasSucursalResponse> obtenerReporteVentasPorSucursal();

}
