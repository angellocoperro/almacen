package com.angel.almacen.repository;

import com.angel.almacen.entities.Producto;
import com.angel.almacen.entities.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SucursalRespository extends JpaRepository<Sucursal, Long> {

    // SELECT COUNT(*) FROM SUCURSALES WHERE = ?
    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCaseAndIdNot(String nombre,  Long id);



    //Optional<Sucursal> findByNombre(String nombre);

}
