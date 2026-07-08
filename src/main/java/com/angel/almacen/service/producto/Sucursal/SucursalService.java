package com.angel.almacen.service.producto.Sucursal;

import com.angel.almacen.dto.productos.ProductoRequest;
import com.angel.almacen.dto.productos.ProductoResponse;
import com.angel.almacen.dto.socursales.SucursalRequest;
import com.angel.almacen.dto.socursales.SucursalResponse;
import com.angel.almacen.entities.Sucursal;
import com.angel.almacen.repository.SucursalRespository;

import java.util.List;

public interface SucursalService {
    List<SucursalResponse> listar();
    SucursalResponse obtenerPorId(Long id);
    SucursalResponse registrar(SucursalRequest request);
    SucursalResponse actualizar(long id, SucursalRequest request);
    void eliminar(long id);

}
