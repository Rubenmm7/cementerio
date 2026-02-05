package com.ruben.cementerio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ruben.cementerio.entity.Concesion;

@Repository
public interface ConcesionRepository extends JpaRepository<Concesion, Long> {
    // Buscar concesiones de un cliente
    List<Concesion> findByClienteId(Long clienteId);
    
    // Buscar concesiones activas
    List<Concesion> findByActivaTrue();
}