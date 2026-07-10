package com.angel.almacen.mapper;
// @Author: Angel de Jesus Apolinar Oregon

import com.angel.almacen.dto.ventas.DetalleVentaResponse;
import com.angel.almacen.dto.ventas.VentaRequest;
import com.angel.almacen.dto.ventas.VentaResponse;
import com.angel.almacen.entities.DetalleVenta;
import com.angel.almacen.entities.Producto;
import com.angel.almacen.entities.Sucursal;
import com.angel.almacen.entities.Venta;
import com.angel.almacen.enums.EstadoVenta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
public class VentaMapper {

    private final SucursalMapper sucursalMapper;

    public Venta requestAEntidad(VentaRequest request, EstadoVenta estadoVenta, LocalDate fecha, Sucursal sucursal) {
        if(request == null) return null;

        return Venta.builder()
                .estadoVenta(estadoVenta)
                .fecha(fecha)
                .sucursal(sucursal)
                .build();
    }// FIN DEL METODO  RequestAEntidad

    /**
     * Crea el detalle de venta "fotografiando" el precio actual del producto,
     * sin confiar en ningun precio enviado por el cliente.
     */
    public DetalleVenta detalleRequestAEntidad(Producto producto, Integer cantidadProducto) {
        return DetalleVenta.builder()
                .producto(producto)
                .cantidadProducto(cantidadProducto)
                .precioProducto(producto.getPrecio())
                .build();
    }

    public DetalleVentaResponse detalleAResponse(DetalleVenta detalleVenta) {
        if(detalleVenta == null) return null;

        return new DetalleVentaResponse(
                detalleVenta.getProducto().getId(),
                detalleVenta.getProducto().getNombre(),
                detalleVenta.getCantidadProducto(),
                detalleVenta.getPrecioProducto(),
                detalleVenta.subtotal()
        );
    }

    public VentaResponse entidadAResponse(Venta venta){
        if(venta == null) return null;

        List<DetalleVentaResponse> detalles = venta.getDetalleVenta() == null
                ? List.of()
                : venta.getDetalleVenta().stream().map(this::detalleAResponse).toList();

        BigDecimal total = detalles.stream()
                .map(DetalleVentaResponse::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new VentaResponse(
                venta.getId(),
                venta.getFecha().toString(),
                venta.getEstadoVenta().getDescripcion(),
                sucursalMapper.entidadAResponse(venta.getSucursal()),
                detalles,
                total
        );

    }// FIN METODO entidadAResponse



}// FIN DE LA CLASE VentaMapper
