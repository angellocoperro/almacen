package com.angel.almacen.mapper;


import com.angel.almacen.dto.productos.ProductoRequest;
import com.angel.almacen.dto.productos.ProductoResponse;
import com.angel.almacen.entities.Producto;
import org.springframework.stereotype.Component;
import com.angel.almacen.enums.Categoria;

@Component
public class ProductoMapper {

    public Producto requestAEntidad(ProductoRequest request, Categoria categoria) {
        if(request == null) return null;

        return Producto.builder()
                .nombre(request.nombre().trim())
                .categoria(categoria)
                .precio(request.precio())
                .cantidad(request.cantidad())
                .build();
    }

    public ProductoResponse entidadAResponse(Producto producto){
        if(producto == null) return null;

        return new ProductoResponse(
          producto.getId(),
          producto.getNombre(),
          producto.getCategoria().getDescripcion(),
          producto.getPrecio(),
          producto.getCantidad()
        );
    }

}// FIN DE LA CLASE PRODUCTOMAPPER
