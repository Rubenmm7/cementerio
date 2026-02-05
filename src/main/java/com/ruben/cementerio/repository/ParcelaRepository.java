package com.ruben.cementerio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ruben.cementerio.entity.Parcela;

@Repository
public interface ParcelaRepository extends JpaRepository<Parcela, Long> {
    List<Parcela> findByZonaId(Long zonaId);
    List<Parcela> findByCementerioId(Long cementerioId);
}