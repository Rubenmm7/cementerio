package com.ruben.cementerio.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruben.cementerio.entity.Concesion;
import com.ruben.cementerio.repository.ConcesionRepository;

@Service
@Transactional
public class ConcesionService {

    private final ConcesionRepository concesionRepository;

    public ConcesionService(ConcesionRepository concesionRepository) {
        this.concesionRepository = concesionRepository;
    }

    public List<Concesion> listarActivas() {
        return concesionRepository.findByActivaTrue();
    }
    
    public List<Concesion> listarPorCliente(Long clienteId) {
        return concesionRepository.findByClienteId(clienteId);
    }

    public Concesion guardar(Concesion concesion) {
        // Aquí podrías añadir lógica: verificar si la parcela ya está ocupada, etc.
        return concesionRepository.save(concesion);
    }
    
    public void cancelarConcesion(Long id) {
        concesionRepository.findById(id).ifPresent(c -> {
            c.setActiva(false);
            concesionRepository.save(c);
        });
    }
}