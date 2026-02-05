package com.ruben.cementerio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ruben.cementerio.entity.Ayuntamiento;

@Repository
public interface AyuntamientoRepository extends JpaRepository<Ayuntamiento, Long> {
}