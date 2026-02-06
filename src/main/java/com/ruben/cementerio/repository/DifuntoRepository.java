package com.ruben.cementerio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ruben.cementerio.entity.Difunto;

@Repository
public interface DifuntoRepository extends JpaRepository<Difunto, Long> {
    
    @Query("SELECT d FROM Difunto d WHERE d.parcela.zona.cementerio.id = :cementerioId")
    List<Difunto> findByCementerioId(@Param("cementerioId") Long cementerioId);
    List<Difunto> findByDni(String dni);
    List<Difunto> findByNombreContainingIgnoreCaseOrApellidosContainingIgnoreCase(String nombre, String apellidos);
    List<Difunto> findByParcelaId(Long parcelaId);
}