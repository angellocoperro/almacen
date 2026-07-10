package com.angel.almacen.service.Sucursal;


import com.angel.almacen.dto.socursales.SucursalRequest;
import com.angel.almacen.dto.socursales.SucursalResponse;
import com.angel.almacen.entities.Sucursal;
import com.angel.almacen.exceptions.RecursoNoEncontradoException;
import com.angel.almacen.mapper.SucursalMapper;
import com.angel.almacen.repository.SucursalRespository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class SucursalServiceImpl implements SucursalService {


    private final SucursalRespository sucursalRespository;
    private final SucursalMapper sucursalMapper;


    @Override
    @Transactional(readOnly = true)
    public List<SucursalResponse> listar() {
        log.info("Listando todas las sucursales");
        return sucursalRespository.findAll().stream()
                .map(sucursalMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SucursalResponse obtenerPorId(Long id) {
        return sucursalMapper.entidadAResponse(obtenerSucursalOException(id));
    }

    @Override
    public SucursalResponse registrar(SucursalRequest request) {

        log.info("Registrando sucursal...");

        validarDatosUnicos(request);

        Sucursal sucursal = sucursalMapper.requestAEntidad(request);
        sucursalRespository.save(sucursal);
        log.info("Nueva Sucursal {} registrada ", sucursal.getNombre());

        return sucursalMapper.entidadAResponse(sucursal);
    }

    @Override
    public SucursalResponse actualizar(long id, SucursalRequest request) {
        Sucursal sucursal = obtenerSucursalOException(id);

        log.info("Actualizando Sucursal con id: {} ", id);
        validarCambiosUnicos(request, id);

        sucursal.actualizar(
                request.nombre(),
                request.direccion()
        );

        log.info("Sucursal con id {} actualizada", id);

        return sucursalMapper.entidadAResponse(sucursal);

    }

    @Override
    public void eliminar(long id) {
        Sucursal sucursal = obtenerSucursalOException(id);

        sucursalRespository.delete(sucursal);

        log.info("Sucursal con id {} eliminada", id);
    }

    private Sucursal obtenerSucursalOException(Long id) {
        log.info("Buscando sucursal con id: {}", id);

        return sucursalRespository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException("Sucursal no encontrada con id: " + id));
    }

    private void validarDatosUnicos(SucursalRequest request) {
        if(sucursalRespository.existsByNombreIgnoreCase(request.nombre().trim()))
            throw new IllegalArgumentException("Ya existe una sucursal con el nombre de: "+ request.nombre());
    }

    private void validarCambiosUnicos(SucursalRequest request, Long id) {
        if(sucursalRespository.existsByNombreIgnoreCaseAndIdNot(request.nombre().trim(), id))
            throw new IllegalArgumentException("Ya existe una sucursal con el nombre de: "+ request.nombre());
    }

}
