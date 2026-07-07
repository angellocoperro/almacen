package com.angel.almacen.repository;


import com.angel.almacen.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {


} // FIN DE LA CLASE PRODUCTO REPOSITORY
