package com.ruben.cementerio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ruben.cementerio.entity.Servicio;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {
}