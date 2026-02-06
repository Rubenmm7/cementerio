package com.ruben.cementerio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruben.cementerio.entity.Parcela;
import com.ruben.cementerio.repository.ParcelaRepository;

@Service
@Transactional
public class ParcelaService {

    private final ParcelaRepository parcelaRepository;

    public ParcelaService(ParcelaRepository parcelaRepository) {
        this.parcelaRepository = parcelaRepository;
    }

    public List<Parcela> listarPorZona(Long zonaId) {
        return parcelaRepository.findByZonaId(zonaId);
    }
    
    public Optional<Parcela> obtenerPorId(Long id) {
        return parcelaRepository.findById(id);
    }

    public Parcela guardar(Parcela parcela) {
        return parcelaRepository.save(parcela);
    }
}