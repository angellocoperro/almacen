package com.angel.almacen.service.producto;

import com.angel.almacen.dto.productos.ProductoRequest;
import com.angel.almacen.dto.productos.ProductoResponse;

import java.math.BigDecimal;
import java.util.List;

public interface ProductoService {
    List<ProductoResponse> listar(
            String nombre, String categoria,
            BigDecimal precioMin, BigDecimal precioMax
    );
    ProductoResponse obtenerPorId(long id);
    ProductoResponse registrar(ProductoRequest request);
    ProductoResponse actualizar(long id, ProductoRequest request);
    void eliminar(long id);

}
