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

    public Optional<Cementerio> actualizar(Long id, Cementerio cementerioActualizado) {
        return cementerioRepository.findById(id)
                .map(cementerioExistente -> {
                    cementerioExistente.setNombre(cementerioActualizado.getNombre());
                    cementerioExistente.setDireccion(cementerioActualizado.getDireccion());
                    cementerioExistente.setPoblacion(cementerioActualizado.getPoblacion());
                    cementerioExistente.setProvincia(cementerioActualizado.getProvincia());
                    cementerioExistente.setCodigoPostal(cementerioActualizado.getCodigoPostal());
                    cementerioExistente.setEmailContacto(cementerioActualizado.getEmailContacto());
                    cementerioExistente.setImagenRuta(cementerioActualizado.getImagenRuta());
                    return cementerioRepository.save(cementerioExistente);
                });
    }

    public void eliminar(Long id) {
        cementerioRepository.deleteById(id);
    }
}