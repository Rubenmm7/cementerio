package com.ruben.cementerio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ruben.cementerio.entity.Rol;
import com.ruben.cementerio.entity.TipoRol;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByTipo(TipoRol rol);
}

