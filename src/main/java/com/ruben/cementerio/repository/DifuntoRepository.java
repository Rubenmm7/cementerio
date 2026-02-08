package com.ruben.cementerio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ruben.cementerio.entity.Difunto;

@Repository
public interface DifuntoRepository extends JpaRepository<Difunto, Long> {
@Query("SELECT d FROM Difunto d " +
           "LEFT JOIN d.parcela p " +
           "LEFT JOIN p.zona z " +
           "LEFT JOIN z.cementerio c " +
           "LEFT JOIN c.ayuntamiento a " +
           "WHERE (:ayuntamientoId IS NULL OR a.id = :ayuntamientoId) " +
           "AND (:cementerioId IS NULL OR c.id = :cementerioId) " +
           "AND (:nombre IS NULL OR LOWER(d.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) " +
           "AND (:apellido IS NULL OR LOWER(d.apellidos) LIKE LOWER(CONCAT('%', :apellido, '%')))")
    List<Difunto> buscarAvanzado(
        @Param("ayuntamientoId") Long ayuntamientoId,
        @Param("cementerioId") Long cementerioId,
        @Param("nombre") String nombre,
        @Param("apellido") String apellido
    );


    @Query("SELECT d FROM Difunto d WHERE d.parcela.zona.cementerio.id = :cementerioId")
    List<Difunto> findByCementerioId(@Param("cementerioId") Long cementerioId);
    List<Difunto> findByDni(String dni);
    List<Difunto> findByNombreContainingIgnoreCaseOrApellidosContainingIgnoreCase(String nombre, String apellidos);
    List<Difunto> findByParcelaId(Long parcelaId);
}