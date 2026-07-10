package com.angel.almacen.repository;

import com.angel.almacen.dto.reportes.ReporteVentasSucursalResponse;
import com.angel.almacen.entities.Venta;
import com.angel.almacen.enums.EstadoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta,Long> {

    List<Venta> findByEstadoVenta(EstadoVenta estadoVenta);

    /**
     * Reporte agregado de rendimiento economico por sucursal.
     * Se calcula completamente en la base de datos (SUM/GROUP BY) para
     * evitar traer todas las filas de detalle a memoria.
     * Solo se contabilizan las ventas en estado REGISTRADA (no canceladas).
     */
    @Query("""
            SELECT new com.angel.almacen.dto.reportes.ReporteVentasSucursalResponse(
                s.id,
                s.nombre,
                COALESCE(SUM(dv.cantidadProducto * dv.precioProducto), 0),
                COALESCE(SUM(dv.cantidadProducto), 0)
            )
            FROM Sucursal s
            LEFT JOIN Venta v ON v.sucursal = s AND v.estadoVenta = :estado
            LEFT JOIN DetalleVenta dv ON dv.venta = v
            GROUP BY s.id, s.nombre
            ORDER BY s.nombre
            """)
    List<ReporteVentasSucursalResponse> obtenerReporteVentasPorSucursal(@Param("estado") EstadoVenta estado);

} // FIN DE LA CLASE VENTAREPOSITORY
