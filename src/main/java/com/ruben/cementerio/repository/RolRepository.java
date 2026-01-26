package com.ruben.cementerio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ruben.cementerio.entity.Rol;
import com.ruben.cementerio.entity.TipoRol;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByTipo(TipoRol rol);
    
    @Modifying
    @Query("DELETE FROM Rol r WHERE r.tipo NOT IN ('SUPERADMIN', 'OPERADOR', 'CLIENTE')")
    void deleteInvalidRoles();
}

