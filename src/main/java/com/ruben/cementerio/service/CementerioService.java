package com.ruben.cementerio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruben.cementerio.entity.Cementerio;
import com.ruben.cementerio.repository.CementerioRepository;

@Service
@Transactional
public class CementerioService {

    private final CementerioRepository cementerioRepository;

    public CementerioService(CementerioRepository cementerioRepository) {
        this.cementerioRepository = cementerioRepository;
    }

    public List<Cementerio> listarTodos() {
        return cementerioRepository.findAll();
    }

    public Optional<Cementerio> obtenerPorId(Long id) {
        return cementerioRepository.findById(id);
    }

    public Cementerio guardar(Cementerio cementerio) {
        return cementerioRepository.save(cementerio);
    }

    public void eliminar(Long id) {
        cementerioRepository.deleteById(id);
    }
}