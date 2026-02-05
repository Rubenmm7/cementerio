package com.ruben.cementerio.config;

import com.ruben.cementerio.entity.*;
import com.ruben.cementerio.entity.parcela.EstadoParcela;
import com.ruben.cementerio.entity.parcela.TipoParcela;
import com.ruben.cementerio.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;

@Component
public class InitializeData implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final UserRepository userRepository;
    private final AyuntamientoRepository ayuntamientoRepository;
    private final CementerioRepository cementerioRepository;
    private final ZonaRepository zonaRepository;
    private final ParcelaRepository parcelaRepository;
    private final DifuntoRepository difuntoRepository;
    private final ConcesionRepository concesionRepository;
    private final PasswordEncoder passwordEncoder;

    public InitializeData(RolRepository rolRepository, UserRepository userRepository,
                          AyuntamientoRepository ayuntamientoRepository, CementerioRepository cementerioRepository,
                          ZonaRepository zonaRepository, ParcelaRepository parcelaRepository,
                          DifuntoRepository difuntoRepository, ConcesionRepository concesionRepository,
                          PasswordEncoder passwordEncoder) {
        this.rolRepository = rolRepository;
        this.userRepository = userRepository;
        this.ayuntamientoRepository = ayuntamientoRepository;
        this.cementerioRepository = cementerioRepository;
        this.zonaRepository = zonaRepository;
        this.parcelaRepository = parcelaRepository;
        this.difuntoRepository = difuntoRepository;
        this.concesionRepository = concesionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        // 1. Roles
        try { rolRepository.deleteInvalidRoles(); } catch (Exception e) {}

        Rol roleAdmin = rolRepository.findByTipo(TipoRol.SUPERADMIN)
                .orElseGet(() -> rolRepository.save(Rol.builder().tipo(TipoRol.SUPERADMIN).build()));
        Rol roleOperador = rolRepository.findByTipo(TipoRol.OPERADOR)
                .orElseGet(() -> rolRepository.save(Rol.builder().tipo(TipoRol.OPERADOR).build()));
        Rol roleCliente = rolRepository.findByTipo(TipoRol.CLIENTE)
                .orElseGet(() -> rolRepository.save(Rol.builder().tipo(TipoRol.CLIENTE).build()));

        // 2. Usuarios
        User admin = userRepository.findByEmail("admin@cementerio.com").orElseGet(() -> {
            User u = User.builder()
                    .nombre("Admin General")
                    .email("admin@cementerio.com")
                    .password(passwordEncoder.encode("admin123"))
                    .telefono("600000000") // Añadido por si es obligatorio
                    .direccion("Oficina Central")
                    .roles(Set.of(roleAdmin))
                    .build();
            return userRepository.save(u);
        });

        User cliente = userRepository.findByEmail("cliente@familia.com").orElseGet(() -> {
            User u = User.builder()
                    .nombre("Juan Pérez")
                    .email("cliente@familia.com")
                    .password(passwordEncoder.encode("cliente123"))
                    .telefono("666777888")
                    .direccion("Calle Falsa 123")
                    .roles(Set.of(roleCliente))
                    .build();
            return userRepository.save(u);
        });

        // 3. Estructura del Cementerio
        if (ayuntamientoRepository.count() == 0) {
            
            // A. Ayuntamiento (¡OJO! Aquí añadí el teléfono obligatorio)
            Ayuntamiento ayto = Ayuntamiento.builder()
                    .nombre("Ayuntamiento de Springfield")
                    .direccion("Plaza Mayor 1")
                    .telefono("911223344") // <--- ESTO FALTABA
                    .build();
            ayuntamientoRepository.save(ayto);

            // B. Cementerio
            Cementerio cementerio = Cementerio.builder()
                    .nombre("Cementerio Municipal El Descanso")
                    .direccion("Carretera del Norte km 5")
                    .poblacion("Springfield")
                    .ayuntamiento(ayto)
                    .build();
            cementerioRepository.save(cementerio);

            // C. Zona
            Zona zonaNorte = Zona.builder()
                    .nombre("Patio Norte - Los Olivos")
                    .numeroFilas(5)
                    .numeroColumnas(10)
                    .cementerio(cementerio)
                    .build();
            zonaRepository.save(zonaNorte);

            // D. Parcelas
            Parcela nicho1 = Parcela.builder()
                    .fila(1)
                    .columna(1)
                    .tipo(TipoParcela.NICHO)
                    .estado(EstadoParcela.OCUPADO)
                    .capacidad(3)
                    .zona(zonaNorte)
                    .build();
            parcelaRepository.save(nicho1);

            // 4. Datos de Negocio
            
            // Difunto
            Difunto abuelo = Difunto.builder()
                    .nombre("Antonio")
                    .apellidos("García López")
                    .dni("12345678A")
                    .fechaDefuncion(LocalDate.now().minusYears(2))
                    .fechaEnterramiento(LocalDate.now().minusYears(2).plusDays(1))
                    .parcela(nicho1)
                    .biografia("Amado abuelo.")
                    .build();
            difuntoRepository.save(abuelo);

            // Concesión
            Concesion contrato = Concesion.builder()
                    .cliente(cliente)
                    .parcela(nicho1)
                    .fechaInicio(LocalDate.now().minusYears(2))
                    .fechaFin(LocalDate.now().plusYears(48))
                    .precio(1500.00)
                    .activa(true)
                    .build();
            concesionRepository.save(contrato);
            
            System.out.println("✅ DATOS DE PRUEBA CARGADOS CORRECTAMENTE");
        }
    }
}