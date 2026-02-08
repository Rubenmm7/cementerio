package com.ruben.cementerio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruben.cementerio.dto.CementerioDTO;
import com.ruben.cementerio.entity.Ayuntamiento;
import com.ruben.cementerio.entity.Cementerio;
import com.ruben.cementerio.repository.AyuntamientoRepository;
import com.ruben.cementerio.repository.CementerioRepository;

@Service
@Transactional
public class CementerioService {

    private final CementerioRepository cementerioRepository;
    private final AyuntamientoRepository ayuntamientoRepository;

    public CementerioService(CementerioRepository cementerioRepository, 
                             AyuntamientoRepository ayuntamientoRepository) {
        this.cementerioRepository = cementerioRepository;
        this.ayuntamientoRepository = ayuntamientoRepository;
    }

    public List<Cementerio> listarTodos() {
        return cementerioRepository.findAll();
    }

    public Optional<Cementerio> obtenerPorId(Long id) {
        return cementerioRepository.findById(id);
    }

    public Cementerio crear(CementerioDTO dto) {
        Cementerio cementerio = new Cementerio();
        return mapearYGuardar(cementerio, dto);
    }

    public Optional<Cementerio> actualizar(Long id, CementerioDTO dto) {
        return cementerioRepository.findById(id)
                .map(cementerioExistente -> mapearYGuardar(cementerioExistente, dto));
    }

    private Cementerio mapearYGuardar(Cementerio cementerio, CementerioDTO dto) {
        cementerio.setNombre(dto.getNombre());
        cementerio.setDireccion(dto.getDireccion());
        cementerio.setPoblacion(dto.getPoblacion());
        cementerio.setProvincia(dto.getProvincia());
        cementerio.setCodigoPostal(dto.getCodigoPostal());
        cementerio.setEmailContacto(dto.getEmailContacto());
        cementerio.setImagenRuta(dto.getImagenRuta());

        // BUSCAR Y ASIGNAR AYUNTAMIENTO
        if (dto.getAyuntamientoId() != null) {
            Ayuntamiento ayuntamiento = ayuntamientoRepository.findById(dto.getAyuntamientoId())
                    .orElseThrow(() -> new RuntimeException("Ayuntamiento no encontrado con ID: " + dto.getAyuntamientoId()));
            cementerio.setAyuntamiento(ayuntamiento);
        } else {
            throw new IllegalArgumentException("El ID del ayuntamiento es obligatorio");
        }

        return cementerioRepository.save(cementerio);
    }

    public void eliminar(Long id) {
        cementerioRepository.deleteById(id);
    }
}