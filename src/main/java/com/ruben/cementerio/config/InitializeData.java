package com.ruben.cementerio.config;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ruben.cementerio.entity.Ayuntamiento;
import com.ruben.cementerio.entity.Cementerio;
import com.ruben.cementerio.entity.Cliente;
import com.ruben.cementerio.entity.Concesion;
import com.ruben.cementerio.entity.Difunto;
import com.ruben.cementerio.entity.Parcela;
import com.ruben.cementerio.entity.Rol;
import com.ruben.cementerio.entity.Servicio;
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
import com.ruben.cementerio.repository.ServicioRepository;
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
    private final ServicioRepository servicioRepository;
    private final PasswordEncoder passwordEncoder;

    public InitializeData(RolRepository rolRepository, UserRepository userRepository, ClienteRepository clienteRepository,
                          AyuntamientoRepository ayuntamientoRepository, CementerioRepository cementerioRepository,
                          ZonaRepository zonaRepository, ParcelaRepository parcelaRepository,
                          DifuntoRepository difuntoRepository, ConcesionRepository concesionRepository,
                          ServicioRepository servicioRepository, PasswordEncoder passwordEncoder) {
        this.rolRepository = rolRepository;
        this.userRepository = userRepository;
        this.clienteRepository = clienteRepository;
        this.ayuntamientoRepository = ayuntamientoRepository;
        this.cementerioRepository = cementerioRepository;
        this.zonaRepository = zonaRepository;
        this.parcelaRepository = parcelaRepository;
        this.difuntoRepository = difuntoRepository;
        this.concesionRepository = concesionRepository;
        this.servicioRepository = servicioRepository;
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

        // 2. Servicios disponibles
        if (servicioRepository.count() == 0) {
            servicioRepository.saveAll(List.of(
                Servicio.builder().tipo("Limpieza de Lápidas").precioBase(50.0).descripcion("Limpieza completa manual").build(),
                Servicio.builder().tipo("Jardinería").precioBase(30.0).descripcion("Cambio de flores y poda").build(),
                Servicio.builder().tipo("Inhumación").precioBase(300.0).descripcion("Servicio de enterramiento").build(),
                Servicio.builder().tipo("Exhumación").precioBase(500.0).descripcion("Retirada de restos").build()
            ));
        }

        // 3. Ayuntamientos y Cementerios Reales
        if (ayuntamientoRepository.count() == 0) {
            
            // CASO 1: Madrid
            Ayuntamiento aytoMadrid = Ayuntamiento.builder()
                    .nombre("Ayuntamiento de Madrid")
                    .direccion("Plaza de Cibeles, 1")
                    .telefono("915298210")
                    .build();
            ayuntamientoRepository.save(aytoMadrid);

            Cementerio almudena = Cementerio.builder()
                    .nombre("Cementerio de la Almudena")
                    .direccion("Av. de Daroca, 90")
                    .poblacion("Madrid")
                    .provincia("Madrid")
                    .codigoPostal("28017")
                    .emailContacto("cementerios@madrid.es")
                    .ayuntamiento(aytoMadrid)
                    .build();
            cementerioRepository.save(almudena);

            // Zonas Almudena
            Zona zonaAntigua = Zona.builder().nombre("Zona Antigua - Patio 1").numeroFilas(10).numeroColumnas(20).cementerio(almudena).build();
            Zona zonaNueva = Zona.builder().nombre("Ampliación Norte").numeroFilas(15).numeroColumnas(15).cementerio(almudena).build();
            zonaRepository.saveAll(List.of(zonaAntigua, zonaNueva));

            // Parcelas (Ejemplo rápido)
            for(int i=1; i<=5; i++) {
                parcelaRepository.save(Parcela.builder().fila(1).columna(i).tipo(TipoParcela.NICHO).estado(EstadoParcela.OCUPADO).capacidad(3).zona(zonaAntigua).build());
            }
            parcelaRepository.save(Parcela.builder().fila(2).columna(1).tipo(TipoParcela.SUELO).estado(EstadoParcela.LIBRE).capacidad(1).zona(zonaNueva).build());


            // CASO 2: Barcelona
            Ayuntamiento aytoBcn = Ayuntamiento.builder()
                    .nombre("Ajuntament de Barcelona")
                    .direccion("Plaça de Sant Jaume, 1")
                    .telefono("934027000")
                    .build();
            ayuntamientoRepository.save(aytoBcn);

            Cementerio montjuic = Cementerio.builder()
                    .nombre("Cementiri de Montjuïc")
                    .direccion("Carrer de la Mare de Déu de Port, 56")
                    .poblacion("Barcelona")
                    .provincia("Barcelona")
                    .codigoPostal("08038")
                    .emailContacto("info@cbsa.cat")
                    .ayuntamiento(aytoBcn)
                    .build();
            cementerioRepository.save(montjuic);

            // 4. Usuarios del Sistema
            
            // ADMIN GLOBAL
            if (userRepository.findByEmail("admin@aeternum.com").isEmpty()) {
                User admin = User.builder()
                        .nombre("Rubén Admin")
                        .email("admin@aeternum.com")
                        .password(passwordEncoder.encode("admin123"))
                        .telefono("600111222")
                        .direccion("Oficinas Centrales")
                        .roles(Set.of(roleAdmin))
                        .ayuntamiento(aytoMadrid) 
                        .build();
                userRepository.save(admin);
            }

            // OPERARIO MADRID
            User operario = User.builder()
                    .nombre("Paco Operario")
                    .email("paco@madrid.es")
                    .password(passwordEncoder.encode("paco123"))
                    .telefono("600333444")
                    .direccion("Calle Trabajo 5")
                    .roles(Set.of(roleOperador))
                    .ayuntamiento(aytoMadrid)
                    .build();
            userRepository.save(operario);

            // CLIENTE FAMILIA
            Cliente familiaGomez = new Cliente();
            familiaGomez.setApellidos("Familia Gómez");
            familiaGomez.setDni("12345678A");
            clienteRepository.save(familiaGomez);

            User userGomez = User.builder()
                    .nombre("María Gómez")
                    .email("maria@familia.com")
                    .password(passwordEncoder.encode("maria123"))
                    .telefono("666777888")
                    .direccion("Calle Pez 22")
                    .roles(Set.of(roleCliente))
                    .cliente(familiaGomez)
                    .ayuntamiento(aytoMadrid)
                    .build();
            userRepository.save(userGomez);

            // 5. Difuntos y Concesiones
            Parcela tumbaAbuelo = parcelaRepository.findAll().get(0); // Cogemos la primera que creamos
            
            Difunto difunto1 = Difunto.builder()
                    .nombre("Antonio")
                    .apellidos("Gómez Pérez")
                    .dni("00112233X")
                    .fechaNacimiento(LocalDate.of(1930, 1, 1))
                    .fechaDefuncion(LocalDate.of(2023, 12, 1))
                    .fechaEnterramiento(LocalDate.of(2023, 12, 2))
                    .biografia("Amante del ajedrez y su familia.")
                    .parcela(tumbaAbuelo)
                    .build();
            difuntoRepository.save(difunto1);

            Concesion concesion = Concesion.builder()
                    .cliente(familiaGomez)
                    .parcela(tumbaAbuelo)
                    .fechaInicio(LocalDate.now().minusYears(1))
                    .fechaFin(LocalDate.now().plusYears(49))
                    .precio(1500.00)
                    .activa(true)
                    .build();
            concesionRepository.save(concesion);

            System.out.println("DATOS CARGADOS: Madrid, Barcelona, Usuarios y Difuntos.");
        }
    }
}