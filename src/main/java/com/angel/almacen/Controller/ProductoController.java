package com.angel.almacen.Controller;
import com.angel.almacen.dto.productos.ProductoRequest;
import com.angel.almacen.dto.productos.ProductoResponse;
import com.angel.almacen.service.producto.ProductoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@AllArgsConstructor
@Validated
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoResponse>> listar() {
        return ResponseEntity.ok(productoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> obtenerPorId(
            @PathVariable @Positive(message = "El id debe ser positivo") Long id) {
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ProductoResponse> registrar(
            @Valid @RequestBody ProductoRequest request) {
        ProductoResponse producto = productoService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(producto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> actualizar(
            @PathVariable @Positive(message = "El id debe ser positivo") Long id,
            @Valid @RequestBody ProductoRequest request) {
        return ResponseEntity.ok(productoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable @Positive(message = "El id debe ser positivo") Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
