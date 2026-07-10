package com.angel.almacen.Controller;

import com.angel.almacen.dto.ventas.VentaRequest;
import com.angel.almacen.dto.ventas.VentaResponse;
import com.angel.almacen.service.ventas.VentaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@AllArgsConstructor
@Validated
public class VentaController {

    private final VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<VentaResponse>> listar() {
        return ResponseEntity.ok(ventaService.listar());
    }

    @GetMapping("/canceladas")
    public ResponseEntity<List<VentaResponse>> listarCanceladas() {
        return ResponseEntity.ok(ventaService.listarCanceladas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponse> obtenerPorId(
            @PathVariable @Positive(message = "El id debe ser positivo") Long id) {
        return ResponseEntity.ok(ventaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<VentaResponse> registrar(
            @Valid @RequestBody VentaRequest request) {
        VentaResponse venta = ventaService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(venta);
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<VentaResponse> cancelar(
            @PathVariable @Positive(message = "El id debe ser positivo") Long id) {
        return ResponseEntity.ok(ventaService.cancelar(id));
    }

}// FIN DE LA CLASE VENTACONTROLLER
