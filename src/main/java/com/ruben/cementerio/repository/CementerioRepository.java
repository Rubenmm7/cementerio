package com.ruben.cementerio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ruben.cementerio.entity.Cementerio;

@Repository
public interface CementerioRepository extends JpaRepository<Cementerio, Long> {
    List<Cementerio> findByAyuntamientoId(Long ayuntamientoId);
}