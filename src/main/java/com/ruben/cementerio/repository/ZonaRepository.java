package com.ruben.cementerio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ruben.cementerio.entity.Zona;

@Repository
public interface ZonaRepository extends JpaRepository<Zona, Long> {
    List<Zona> findByCementerioId(Long cementerioId);
}