package com.ruben.cementerio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruben.cementerio.entity.Difunto;
import com.ruben.cementerio.repository.DifuntoRepository;

@Service
@Transactional
public class DifuntoService {

    private final DifuntoRepository difuntoRepository;

    public DifuntoService(DifuntoRepository difuntoRepository) {
        this.difuntoRepository = difuntoRepository;
    }

    public List<Difunto> listarTodos() {
        return difuntoRepository.findAll();
    }

    // Método para buscador público
    public List<Difunto> buscar(String nombre, String apellidos) {
        return difuntoRepository.findByNombreContainingIgnoreCaseOrApellidosContainingIgnoreCase(nombre, apellidos);
    }

    public Optional<Difunto> obtenerPorId(Long id) {
        return difuntoRepository.findById(id);
    }

    public Difunto guardar(Difunto difunto) {
        return difuntoRepository.save(difunto);
    }

    public void eliminar(Long id) {
        difuntoRepository.deleteById(id);
    }
}