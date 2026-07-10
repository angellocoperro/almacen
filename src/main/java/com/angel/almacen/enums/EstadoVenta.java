package com.angel.almacen.enums;


import com.angel.almacen.exceptions.RecursoNoEncontradoException;
import com.angel.almacen.utils.StringCustomUrils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
@Getter
public enum EstadoVenta {

    REGISTRADA(1L, "Registrada"),
    CANCELADA(0L, "Cancelada"),;



    private final Long codigo;
    private  final String descripcion;


    public static EstadoVenta obtenerEstadoVentaPorDescripcion(String descripcion){

        StringCustomUrils.validarNoVacio(descripcion, "La descripcion es requerida");

        String descripcionNormalizada = StringCustomUrils.quitarAcentos(descripcion.trim());

        for(EstadoVenta estadoVenta : values()){
            if(StringCustomUrils.quitarAcentos(estadoVenta.descripcion).equalsIgnoreCase(descripcionNormalizada)){
                return estadoVenta;
            }
        }

        throw new RecursoNoEncontradoException("No existe una categoria con la descripcion: "+ descripcion);
    }

    public static EstadoVenta obtenerEstadoVentaPorCodigo(Long codigo){
        for(EstadoVenta estadoVenta : values()){
            if(Objects.equals(estadoVenta.codigo, codigo)){
                return estadoVenta;
            }
        }
        throw new RecursoNoEncontradoException("No existe un estado de venta con el codigo: "+ codigo);
    }
}
