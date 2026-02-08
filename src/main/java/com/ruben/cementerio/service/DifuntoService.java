package com.ruben.cementerio.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruben.cementerio.dto.DifuntoDTO;
import com.ruben.cementerio.entity.Difunto;
import com.ruben.cementerio.entity.Parcela;
import com.ruben.cementerio.repository.DifuntoRepository;
import com.ruben.cementerio.repository.ParcelaRepository;

@Service
@Transactional
public class DifuntoService {

    private final DifuntoRepository difuntoRepository;
    private final ParcelaRepository parcelaRepository;
    private final ModelMapper modelMapper;

    public DifuntoService(DifuntoRepository difuntoRepository, ParcelaRepository parcelaRepository, ModelMapper modelMapper) {
        this.difuntoRepository = difuntoRepository;
        this.parcelaRepository = parcelaRepository;
        this.modelMapper = modelMapper;
    }

    public List<Difunto> listarTodos() {
        return difuntoRepository.findAll();
    }

    public List<Difunto> buscarAvanzado(Long ayuntamientoId, Long cementerioId, String nombre, String apellido) {
        return difuntoRepository.buscarAvanzado(ayuntamientoId, cementerioId, nombre, apellido);
    }

    public Difunto guardarDesdeDTO(DifuntoDTO dto) {
        Difunto difunto = modelMapper.map(dto, Difunto.class);

        if (dto.getParcelaId() != null) {
            Parcela parcela = parcelaRepository.findById(dto.getParcelaId())
                    .orElseThrow(() -> new RuntimeException("Parcela no encontrada con ID: " + dto.getParcelaId()));
            difunto.setParcela(parcela);
        }

        return difuntoRepository.save(difunto);
    }

    public List<Difunto> buscar(String nombre, String apellidos) {
        return difuntoRepository.findByNombreContainingIgnoreCaseOrApellidosContainingIgnoreCase(nombre, apellidos);
    }

    public Optional<Difunto> obtenerPorId(Long id) {
        return difuntoRepository.findById(id);
    }

    public void eliminar(Long id) {
        difuntoRepository.deleteById(id);
    }

    public Optional<Difunto> actualizar(Long id, DifuntoDTO dto) {
        return difuntoRepository.findById(id)
            .map(difuntoExistente -> {
                difuntoExistente.setNombre(dto.getNombre());
                difuntoExistente.setApellidos(dto.getApellidos());
                difuntoExistente.setDni(dto.getDni());
                difuntoExistente.setFechaNacimiento(dto.getFechaNacimiento());
                difuntoExistente.setFechaDefuncion(dto.getFechaDefuncion());
                difuntoExistente.setFechaEnterramiento(dto.getFechaEnterramiento());
                difuntoExistente.setBiografia(dto.getBiografia());
                difuntoExistente.setFotoUrl(dto.getFotoUrl());
                
                if (dto.getParcelaId() != null) {
                    Parcela parcela = parcelaRepository.findById(dto.getParcelaId())
                        .orElseThrow(() -> new RuntimeException("Parcela no encontrada con ID: " + dto.getParcelaId()));
                    difuntoExistente.setParcela(parcela);
                } else {
                    difuntoExistente.setParcela(null);
                }

                return difuntoRepository.save(difuntoExistente);
            });
    }
}