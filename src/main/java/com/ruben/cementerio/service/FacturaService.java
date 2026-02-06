package com.ruben.cementerio.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruben.cementerio.entity.Factura;
import com.ruben.cementerio.repository.FacturaRepository;

@Service
@Transactional
public class FacturaService {

    private final FacturaRepository facturaRepository;

    public FacturaService(FacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

    public Factura crearFactura(Factura factura) {
        if (factura.getNumeroFactura() == null) {
            factura.setNumeroFactura("FACT-" + UUID.randomUUID().toString().substring(0, 8));
        }
        factura.setFechaEmision(LocalDateTime.now());
        return facturaRepository.save(factura);
    }

    public List<Factura> listarPorCliente(Long clienteId) {
        return facturaRepository.findByClienteId(clienteId);
    }
    
    public List<Factura> listarTodas() {
        return facturaRepository.findAll();
    }
}