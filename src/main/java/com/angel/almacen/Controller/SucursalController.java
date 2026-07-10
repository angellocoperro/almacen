package com.angel.almacen.Controller;


import com.angel.almacen.dto.socursales.SucursalRequest;
import com.angel.almacen.dto.socursales.SucursalResponse;
import com.angel.almacen.service.Sucursal.SucursalService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/sucursales")
@AllArgsConstructor
@Validated
public class SucursalController {

    private final SucursalService sucursalService;

    @GetMapping
    public ResponseEntity<List<SucursalResponse>> listar() {
        return ResponseEntity.ok(sucursalService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SucursalResponse> obtenerPorId(
            @PathVariable @Positive(message = "El id debe ser positivo") Long id) {
        return ResponseEntity.ok(sucursalService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<SucursalResponse> registrar(
            @Valid @RequestBody SucursalRequest request) {
        SucursalResponse sucursal = sucursalService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(sucursal);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SucursalResponse> actualizar(
            @PathVariable @Positive(message = "El id debe ser positivo") Long id,
            @Valid @RequestBody SucursalRequest request) {
        return ResponseEntity.ok(sucursalService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable @Positive(message = "El id debe ser positivo") Long id) {
        sucursalService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}// FIN DE LA CLASE SUCURSALCONTROLLER
