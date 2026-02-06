package com.ruben.cementerio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruben.cementerio.entity.Ayuntamiento;
import com.ruben.cementerio.repository.AyuntamientoRepository;

@Service
@Transactional
public class AyuntamientoService {

    private final AyuntamientoRepository ayuntamientoRepository;

    public AyuntamientoService(AyuntamientoRepository ayuntamientoRepository) {
        this.ayuntamientoRepository = ayuntamientoRepository;
    }

    public List<Ayuntamiento> listarTodos() {
        return ayuntamientoRepository.findAll();
    }

    public Optional<Ayuntamiento> obtenerPorId(Long id) {
        return ayuntamientoRepository.findById(id);
    }

    public Ayuntamiento guardar(Ayuntamiento ayuntamiento) {
        return ayuntamientoRepository.save(ayuntamiento);
    }

    public void eliminar(Long id) {
        ayuntamientoRepository.deleteById(id);
    }
}