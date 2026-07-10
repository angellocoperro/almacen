package com.angel.almacen.service.ventas;

import com.angel.almacen.dto.ventas.VentaRequest;
import com.angel.almacen.dto.ventas.VentaResponse;

import java.util.List;


public interface VentaService {

    List<VentaResponse> listar();
    List<VentaResponse> listarCanceladas();
    VentaResponse obtenerPorId(Long id);
    VentaResponse registrar(VentaRequest request);
    VentaResponse cancelar(Long id);

}
