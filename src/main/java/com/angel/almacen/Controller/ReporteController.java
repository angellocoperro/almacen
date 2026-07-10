package com.angel.almacen.Controller;

import com.angel.almacen.dto.reportes.ReporteVentasSucursalResponse;
import com.angel.almacen.service.reportes.ReporteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@AllArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping("/ventas-sucursal")
    public ResponseEntity<List<ReporteVentasSucursalResponse>> obtenerReporteVentasPorSucursal() {
        return ResponseEntity.ok(reporteService.obtenerReporteVentasPorSucursal());
    }

}// FIN DE LA CLASE REPORTECONTROLLER


// http://localhost:8080/api/reportes/ventas-sucursal -- get
// http://localhost:8080/api/sucursales - post
/*
{
  "nombre": "Sucursal Centro",
  "direccion": "Avenida Siempre Viva 123"
}
 */


// - http://localhost:8080/api/ventas -post
/*
{
  "fecha": "2024-05-24",
  "idSucursal": 1,
  "productos": [
    {
      "idProducto": 2,
      "cantidadProducto": 1
    },
    {
      "idProducto": 3,
      "cantidadProducto": 5
    }
  ]
}

* */

// -- http://localhost:8080/api/ventas/3/cancelar -- patch


// --  http://localhost:8080/api/ventas/canceladas -- get

//

