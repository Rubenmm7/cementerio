package com.ruben.cementerio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ruben.cementerio.entity.ImplementacionService;

@Repository
public interface ImplementacionServiceRepository extends JpaRepository<ImplementacionService, Long> {
    List<ImplementacionService> findByFacturaId(Long facturaId);
}