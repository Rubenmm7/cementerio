package com.ruben.cementerio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ruben.cementerio.entity.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    // Ver pagos de una factura específica
    List<Pago> findByFacturaId(Long facturaId);
}