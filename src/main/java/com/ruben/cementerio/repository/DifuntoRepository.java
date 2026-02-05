package com.ruben.cementerio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ruben.cementerio.entity.Difunto;

@Repository
public interface DifuntoRepository extends JpaRepository<Difunto, Long> {
    // Buscador por DNI o Nombre
    List<Difunto> findByDni(String dni);
    List<Difunto> findByNombreContainingIgnoreCaseOrApellidosContainingIgnoreCase(String nombre, String apellidos);
    
    // Ver quién está en una parcela
    List<Difunto> findByParcelaId(Long parcelaId);
}