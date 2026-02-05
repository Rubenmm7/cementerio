package com.ruben.cementerio.config;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ruben.cementerio.entity.Ayuntamiento;
import com.ruben.cementerio.entity.Cementerio;
import com.ruben.cementerio.entity.Cliente;
import com.ruben.cementerio.entity.Concesion;
import com.ruben.cementerio.entity.Parcela;
import com.ruben.cementerio.entity.Rol;
import com.ruben.cementerio.entity.TipoRol;
import com.ruben.cementerio.entity.User;
import com.ruben.cementerio.entity.Zona;
import com.ruben.cementerio.entity.parcela.EstadoParcela;
import com.ruben.cementerio.entity.parcela.TipoParcela;
import com.ruben.cementerio.repository.AyuntamientoRepository;
import com.ruben.cementerio.repository.CementerioRepository;
import com.ruben.cementerio.repository.ClienteRepository;
import com.ruben.cementerio.repository.ConcesionRepository;
import com.ruben.cementerio.repository.DifuntoRepository;
import com.ruben.cementerio.repository.ParcelaRepository;
import com.ruben.cementerio.repository.RolRepository;
import com.ruben.cementerio.repository.UserRepository;
import com.ruben.cementerio.repository.ZonaRepository;

@Component
public class InitializeData implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final UserRepository userRepository;
    private final ClienteRepository clienteRepository;
    private final AyuntamientoRepository ayuntamientoRepository;
    private final CementerioRepository cementerioRepository;
    private final ZonaRepository zonaRepository;
    private final ParcelaRepository parcelaRepository;
    private final DifuntoRepository difuntoRepository;
    private final ConcesionRepository concesionRepository;
    private final PasswordEncoder passwordEncoder;

    public InitializeData(RolRepository rolRepository, UserRepository userRepository, ClienteRepository clienteRepository,
                          AyuntamientoRepository ayuntamientoRepository, CementerioRepository cementerioRepository,
                          ZonaRepository zonaRepository, ParcelaRepository parcelaRepository,
                          DifuntoRepository difuntoRepository, ConcesionRepository concesionRepository,
                          PasswordEncoder passwordEncoder) {
        this.rolRepository = rolRepository;
        this.userRepository = userRepository;
        this.clienteRepository = clienteRepository;
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

        //Limpieza y Roles
        try { rolRepository.deleteInvalidRoles(); } catch (Exception e) {}

        Rol roleAdmin = rolRepository.findByTipo(TipoRol.SUPERADMIN)
                .orElseGet(() -> rolRepository.save(Rol.builder().tipo(TipoRol.SUPERADMIN).build()));
        Rol roleCliente = rolRepository.findByTipo(TipoRol.CLIENTE)
                .orElseGet(() -> rolRepository.save(Rol.builder().tipo(TipoRol.CLIENTE).build()));

        // 2. Estructura Base (Ayuntamiento)
        if (ayuntamientoRepository.count() == 0) {
            Ayuntamiento ayto = Ayuntamiento.builder()
                    .nombre("Ayuntamiento Springfield")
                    .direccion("Plaza Mayor")
                    .telefono("911223344")
                    .build();
            ayuntamientoRepository.save(ayto);

            Cementerio cementerio = Cementerio.builder()
                    .nombre("Cementerio Municipal")
                    .direccion("Norte Km 5")
                    .poblacion("Springfield")
                    .ayuntamiento(ayto)
                    .build();
            cementerioRepository.save(cementerio);

            Zona zona = Zona.builder().nombre("Zona A").numeroFilas(5).numeroColumnas(10).cementerio(cementerio).build();
            zonaRepository.save(zona);

            Parcela nicho = Parcela.builder().fila(1).columna(1).tipo(TipoParcela.NICHO).estado(EstadoParcela.OCUPADO).capacidad(3).zona(zona).build();
            parcelaRepository.save(nicho);

            // Primero creamos la entidad Cliente
            Cliente familiaPerez = new Cliente();
            familiaPerez.setApellidos("Familia Pérez");
            familiaPerez.setDni("12345678Z");
            clienteRepository.save(familiaPerez);

            //Ahora creamos el 'User' vinculado al Cliente y al Ayuntamiento
            if (userRepository.findByEmail("cliente@cementerio.com").isEmpty()) {
                User usuarioJuan = User.builder()
                        .nombre("Juan Pérez")
                        .email("cliente@cementerio.com")
                        .password(passwordEncoder.encode("1234"))
                        .telefono("666000000")
                        .direccion("Calle Falsa 123")
                        .roles(Set.of(roleCliente))
                        .cliente(familiaPerez) // Vinculamos user a cliente
                        .ayuntamiento(ayto)    // Vinculamos user a ayuntamiento
                        .build();
                userRepository.save(usuarioJuan);
            }
            
            //La Concesión se vincula al CLIENTE no al usuario
            Concesion contrato = Concesion.builder()
                    .cliente(familiaPerez)
                    .parcela(nicho)
                    .fechaInicio(LocalDate.now().minusYears(1))
                    .fechaFin(LocalDate.now().plusYears(49))
                    .precio(1000.0)
                    .activa(true)
                    .build();
            concesionRepository.save(contrato);

            System.out.println(" DATOS CARGADOS CORRECTAMENTE");
        }
    }
}