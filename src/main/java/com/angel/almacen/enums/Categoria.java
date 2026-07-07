package com.angel.almacen.enums;

import com.angel.almacen.exceptions.RecursoNoEncontradoException;
import com.angel.almacen.utils.StringCustomUrils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Categoria {

    ALIMENTO("Alimentos"),
    HIGIENE("Higienes"),
    JUGUETE("Juguetes"),
    ELECTRONICA("Electrónica"),
    ROPA("Ropa"),
    ACCESORIO("Accesorio"),
    FARMACIA("Farmacia"),;

    private final String descripcion;

    public static Categoria obtenerCategoriaPorDescripcion(String descripcion){
        StringCustomUrils.validarNoVacio(descripcion, "La descripcion es requerida");

        String descripcionNormalizada = StringCustomUrils.quitarAcentos(descripcion.trim());
        for(Categoria categoria : values()){
            if(StringCustomUrils.quitarAcentos(categoria.getDescripcion()).equalsIgnoreCase(descripcionNormalizada)){
                return categoria;
            }
        }

        throw new RecursoNoEncontradoException("No existe una categoria con la descripcion: "+ descripcion);
    }


}
