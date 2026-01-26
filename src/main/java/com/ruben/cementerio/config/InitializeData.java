package com.ruben.cementerio.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ruben.cementerio.entity.Rol;
import com.ruben.cementerio.entity.TipoRol;
import com.ruben.cementerio.repository.RolRepository;

// Clase para inicializar datos de prueba al arrancar la aplicación

@Component
public class InitializeData implements CommandLineRunner {

    private final RolRepository rolRepository;

    public InitializeData(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Crear roles si no existen
        if (rolRepository.findByTipo(TipoRol.SUPERADMIN).isEmpty()) {
            rolRepository.save(Rol.builder().tipo(TipoRol.SUPERADMIN).build());
        }
        if (rolRepository.findByTipo(TipoRol.OPERADOR).isEmpty()) {
            rolRepository.save(Rol.builder().tipo(TipoRol.OPERADOR).build());
        }
        if (rolRepository.findByTipo(TipoRol.CLIENTE).isEmpty()) {
            rolRepository.save(Rol.builder().tipo(TipoRol.CLIENTE).build());
        }
    }
}
