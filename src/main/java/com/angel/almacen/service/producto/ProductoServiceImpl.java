package com.angel.almacen.service.producto;


import com.angel.almacen.dto.productos.ProductoRequest;
import com.angel.almacen.dto.productos.ProductoResponse;
import com.angel.almacen.entities.Producto;
import com.angel.almacen.enums.Categoria;
import com.angel.almacen.exceptions.RecursoNoEncontradoException;
import com.angel.almacen.mapper.ProductoMapper;
import com.angel.almacen.repository.ProductoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class ProductoServiceImpl implements ProductoService{

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponse> listar(
            String nombre, String categoria,
            BigDecimal precioMin, BigDecimal precioMax
    ) {
        log.info("Listando productos con filtros -> nombre: {}, categoria: {}, precioMin: {}, precioMax: {}",
                nombre, categoria, precioMin, precioMax);

        Specification<Producto> specification = conFiltros(
                nombre, categoria, precioMin, precioMax
        );

        return productoRepository.findAll(specification).stream()
                .map(productoMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponse obtenerPorId(long id) {
        Producto producto = obtenerProductoPorIdOException(id);
        return productoMapper.entidadAResponse(producto);
    }

    @Override
    public ProductoResponse registrar(ProductoRequest request) {
        log.info("Registrando nuevo producto...");

        Categoria categoria = Categoria.obtenerCategoriaPorDescripcion(request.categoria());
        Producto producto = productoMapper.requestAEntidad(request, categoria);
        productoRepository.save(producto);
        log.info("Nuevo producto '{}' registrado con id {}", producto.getNombre(), producto.getId());

        return productoMapper.entidadAResponse(producto);
    }

    @Override
    public ProductoResponse actualizar(long id, ProductoRequest request) {
        log.info("Actualizando producto con id: {}", id);
        Producto producto = obtenerProductoPorIdOException(id);

        Categoria categoria = Categoria.obtenerCategoriaPorDescripcion(request.categoria());

        producto.actualizar(
                request.nombre(),
                categoria,
                request.precio(),
                request.cantidad()
        );

        productoRepository.save(producto);
        log.info("Producto con id: {} actualizado", id);

        return productoMapper.entidadAResponse(producto);
    }

    @Override
    public void eliminar(long id) {
        log.info("Eliminando producto con id: {}", id);
        Producto producto = obtenerProductoPorIdOException(id);
        productoRepository.delete(producto);
        log.info("Producto con id: {} eliminado", id);
    }

    private Producto obtenerProductoPorIdOException(long id) {
        log.info("Buscando producto con id: {}", id);
        return productoRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException("Producto no encontrado con id: " + id));
    }

    private Specification<Producto> conNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return null;
        }
        String patron = "%" + nombre.trim().toLowerCase() + "%";
        return (root, query, cb) -> cb.like(cb.lower(root.get("nombre")), patron);
    }

    private Specification<Producto> conCategoria(String categoria) {
        if (categoria == null || categoria.isBlank()) {
            return null;
        }

        Categoria categoriaEnum;
        try {
            categoriaEnum = Categoria.obtenerCategoriaPorDescripcion(categoria.trim());
        } catch (RuntimeException e) {
            return (root, query, cb) -> cb.disjunction();
        }

        Categoria categoriaFinal = categoriaEnum;
        return (root, query, cb) -> cb.equal(root.get("categoria"), categoriaFinal);
    }

    private Specification<Producto> conPrecioMin(BigDecimal precioMin) {
        if (precioMin == null) {
            return null;
        }
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("precio"), precioMin);
    }

    private Specification<Producto> conPrecioMax(BigDecimal precioMax) {
        if (precioMax == null) {
            return null;
        }
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("precio"), precioMax);
    }

    private Specification<Producto> conFiltros(
            String nombre, String categoria, BigDecimal precioMin, BigDecimal precioMax) {

        return Specification.where(conNombre(nombre))
                .and(conCategoria(categoria))
                .and(conPrecioMin(precioMin))
                .and(conPrecioMax(precioMax));
    }

}
