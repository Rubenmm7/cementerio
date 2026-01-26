package com.ruben.cementerio.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ruben.cementerio.entity.Rol;
import com.ruben.cementerio.entity.TipoRol;
import com.ruben.cementerio.entity.User;
import com.ruben.cementerio.repository.RolRepository;
import com.ruben.cementerio.repository.UserRepository;

@Component
public class InitializeData implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public InitializeData(RolRepository rolRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.rolRepository = rolRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        // Limpiar roles inválidos que no están en el enum TipoRol
        rolRepository.deleteInvalidRoles();

        // Crear roles
        Rol roleAdmin = rolRepository.findByTipo(TipoRol.SUPERADMIN)
                .orElseGet(() -> rolRepository.save(Rol.builder().tipo(TipoRol.SUPERADMIN).build()));
        
        Rol roleOperador = rolRepository.findByTipo(TipoRol.OPERADOR)
                .orElseGet(() -> rolRepository.save(Rol.builder().tipo(TipoRol.OPERADOR).build()));
        
        Rol roleCliente = rolRepository.findByTipo(TipoRol.CLIENTE)
                .orElseGet(() -> rolRepository.save(Rol.builder().tipo(TipoRol.CLIENTE).build()));

        // Crear usuarios de prueba
        if (userRepository.findByEmail("admin@cementerio.com").isEmpty()) {
            User admin = User.builder()
                    .nombre("Admin User")
                    .email("admin@cementerio.com")
                    .password(passwordEncoder.encode("admin123"))
                    .telefono("600000000")
                    .direccion("Calle Admin 1")
                    .rol(roleAdmin)
                    .build();
            userRepository.save(admin);
        }

        if (userRepository.findByEmail("operador@cementerio.com").isEmpty()) {
            User operador = User.builder()
                    .nombre("Operador User")
                    .email("operador@cementerio.com")
                    .password(passwordEncoder.encode("operador123"))
                    .telefono("611111111")
                    .direccion("Calle Operador 1")
                    .rol(roleOperador)
                    .build();
            userRepository.save(operador);
        }

        if (userRepository.findByEmail("cliente@cementerio.com").isEmpty()) {
            User cliente = User.builder()
                    .nombre("Cliente User")
                    .email("cliente@cementerio.com")
                    .password(passwordEncoder.encode("cliente123"))
                    .telefono("622222222")
                    .direccion("Calle Cliente 1")
                    .rol(roleCliente)
                    .build();
            userRepository.save(cliente);
        }
    }
}
