package com.ruben.cementerio.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruben.cementerio.entity.Zona;
import com.ruben.cementerio.repository.ZonaRepository;

@Service
@Transactional
public class ZonaService {

    private final ZonaRepository zonaRepository;

    public ZonaService(ZonaRepository zonaRepository) {
        this.zonaRepository = zonaRepository;
    }

    public List<Zona> listarPorCementerio(Long cementerioId) {
        return zonaRepository.findByCementerioId(cementerioId);
    }

    public Zona guardar(Zona zona) {
        return zonaRepository.save(zona);
    }
    
    public void eliminar(Long id) {
        zonaRepository.deleteById(id);
    }
}