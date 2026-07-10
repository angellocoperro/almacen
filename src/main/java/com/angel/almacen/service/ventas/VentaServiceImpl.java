package com.angel.almacen.service.ventas;

import com.angel.almacen.dto.ventas.DetalleVentaRequest;
import com.angel.almacen.dto.ventas.VentaRequest;
import com.angel.almacen.dto.ventas.VentaResponse;
import com.angel.almacen.entities.DetalleVenta;
import com.angel.almacen.entities.Producto;
import com.angel.almacen.entities.Sucursal;
import com.angel.almacen.entities.Venta;
import com.angel.almacen.enums.EstadoVenta;
import com.angel.almacen.exceptions.RecursoNoEncontradoException;
import com.angel.almacen.mapper.VentaMapper;
import com.angel.almacen.repository.ProductoRepository;
import com.angel.almacen.repository.SucursalRespository;
import com.angel.almacen.repository.VentaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class VentaServiceImpl implements VentaService{


    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;
    private final SucursalRespository sucursalRespository;
    private final VentaMapper ventaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<VentaResponse> listar() {
        log.info("Listando todas las ventas registradas...");

        List<Venta> ventas = ventaRepository.findByEstadoVenta(EstadoVenta.REGISTRADA);

        return ventas.stream().map(ventaMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<VentaResponse> listarCanceladas() {
        log.info("Listando historico de ventas canceladas...");

        List<Venta> ventas = ventaRepository.findByEstadoVenta(EstadoVenta.CANCELADA);

        return ventas.stream().map(ventaMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public VentaResponse obtenerPorId(Long id) {
        log.info("Buscando venta con id: {}", id);

        Venta venta = obtenerVentaOException(id);

        return ventaMapper.entidadAResponse(venta);
    }

    @Override
    public VentaResponse registrar(VentaRequest request) {
        log.info("Registrando nueva venta para la sucursal con id: {}", request.idSucursal());

        Sucursal sucursal = sucursalRespository.findById(request.idSucursal()).orElseThrow(
                () -> new RecursoNoEncontradoException(
                        "Sucursal no encontrada con id: " + request.idSucursal()));

        LocalDate fecha = parsearFecha(request.fecha());

        Venta venta = ventaMapper.requestAEntidad(request, EstadoVenta.REGISTRADA, fecha, sucursal);

        for (DetalleVentaRequest detalleRequest : request.productos()) {
            Producto producto = productoRepository.findById(detalleRequest.idProducto()).orElseThrow(
                    () -> new RecursoNoEncontradoException(
                            "Producto no encontrado con id: " + detalleRequest.idProducto()));

            // Validacion de existencias: si falla, se lanza excepcion y toda la
            // operacion se revierte gracias a @Transactional (rollback atomico).
            if (producto.getCantidad() < detalleRequest.cantidadProducto()) {
                throw new IllegalArgumentException(
                        "Stock insuficiente para el producto '" + producto.getNombre() +
                                "'. Disponible: " + producto.getCantidad() +
                                ", solicitado: " + detalleRequest.cantidadProducto());
            }

            // Fotografia del precio: se usa el precio actual del producto,
            // nunca el que pueda enviar el cliente.
            DetalleVenta detalleVenta = ventaMapper.detalleRequestAEntidad(
                    producto, detalleRequest.cantidadProducto());

            venta.agregarDetalle(detalleVenta);

            // Control de inventario
            producto.descontarCantidad(detalleRequest.cantidadProducto());
        }

        ventaRepository.save(venta);

        log.info("Venta registrada con id: {} para la sucursal '{}'", venta.getId(), sucursal.getNombre());

        return ventaMapper.entidadAResponse(venta);
    }

    @Override
    public VentaResponse cancelar(Long id) {
        log.info("Cancelando venta con id: {}", id);

        Venta venta = obtenerVentaOException(id);

        // Si ya esta cancelada, Venta#cancelar lanza IllegalStateException
        venta.cancelar();

        // Devolucion de stock al inventario original
        for (DetalleVenta detalle : venta.getDetalleVenta()) {
            detalle.getProducto().aumentarCantidad(detalle.getCantidadProducto());
        }

        ventaRepository.save(venta);

        log.info("Venta con id: {} cancelada y stock restituido", id);

        return ventaMapper.entidadAResponse(venta);
    }

    private Venta obtenerVentaOException(Long id) {
        return ventaRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException("Venta no encontrada con id: " + id));
    }

    private LocalDate parsearFecha(String fecha) {
        try {
            return LocalDate.parse(fecha);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "El formato de la fecha es invalido, se espera aaaa-MM-dd: " + fecha);
        }
    }
}
