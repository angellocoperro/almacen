package com.angel.almacen.service.reportes;

import com.angel.almacen.dto.reportes.ReporteVentasSucursalResponse;
import com.angel.almacen.enums.EstadoVenta;
import com.angel.almacen.repository.VentaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ReporteServiceImpl implements ReporteService {

    private final VentaRepository ventaRepository;

    @Override
    public List<ReporteVentasSucursalResponse> obtenerReporteVentasPorSucursal() {
        log.info("Generando reporte de rendimiento economico por sucursal...");

        // Agregacion resuelta con SUM/GROUP BY en la base de datos (no en Java streams)
        // para que la eficiencia no dependa del volumen de detalles en memoria.
        return ventaRepository.obtenerReporteVentasPorSucursal(EstadoVenta.REGISTRADA);
    }
}
