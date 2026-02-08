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
        // 0. Limpieza preventiva de roles inválidos si existieran
        try { rolRepository.deleteInvalidRoles(); } catch (Exception e) {}

        // 1. CREACIÓN DE ROLES (Si no existen)
        Rol roleAdmin = rolRepository.findByTipo(TipoRol.SUPERADMIN)
                .orElseGet(() -> rolRepository.save(Rol.builder().tipo(TipoRol.SUPERADMIN).build()));
        Rol roleOperador = rolRepository.findByTipo(TipoRol.OPERADOR)
                .orElseGet(() -> rolRepository.save(Rol.builder().tipo(TipoRol.OPERADOR).build()));
        Rol roleCliente = rolRepository.findByTipo(TipoRol.CLIENTE)
                .orElseGet(() -> rolRepository.save(Rol.builder().tipo(TipoRol.CLIENTE).build()));

        // 2. CREACIÓN DE SERVICIOS
        if (servicioRepository.count() == 0) {
            servicioRepository.saveAll(List.of(
                Servicio.builder().tipo("Limpieza de Lápidas").precioBase(50.0).descripcion("Limpieza completa manual y pulido").build(),
                Servicio.builder().tipo("Jardinería").precioBase(30.0).descripcion("Cambio de flores frescas y poda de arbustos").build(),
                Servicio.builder().tipo("Inhumación").precioBase(300.0).descripcion("Servicio de apertura y cierre de nicho/tumba").build(),
                Servicio.builder().tipo("Mantenimiento Anual").precioBase(120.0).descripcion("Revisión y limpieza trimestral").build()
            ));
        }

        // 3. ESTRUCTURA: AYUNTAMIENTOS -> CEMENTERIOS -> ZONAS -> PARCELAS
        if (ayuntamientoRepository.count() == 0) {
            
            // --- CASO 1: MADRID ---
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
                    .imagenRuta("assets/img/almudena.jpg") // Ruta simulada para el frontend
                    .ayuntamiento(aytoMadrid)
                    .build();
            cementerioRepository.save(almudena);

            // Zonas de la Almudena
            Zona zonaAntigua = Zona.builder().nombre("Zona Antigua - Patio 1").numeroFilas(5).numeroColumnas(10).cementerio(almudena).build();
            Zona zonaNueva = Zona.builder().nombre("Ampliación Norte").numeroFilas(8).numeroColumnas(8).cementerio(almudena).build();
            zonaRepository.saveAll(List.of(zonaAntigua, zonaNueva));

            // Parcelas (Nichos y Tumbas)
            // Llenamos la zona antigua con 5 nichos ocupados
            for(int i=1; i<=5; i++) {
                parcelaRepository.save(Parcela.builder()
                    .fila(1).columna(i)
                    .tipo(TipoParcela.NICHO)
                    .estado(EstadoParcela.OCUPADO) // Ya tienen dueño
                    .capacidad(3)
                    .zona(zonaAntigua).build());
            }
            // Llenamos la zona nueva con parcelas libres
            for(int i=1; i<=3; i++) {
                 parcelaRepository.save(Parcela.builder()
                    .fila(1).columna(i)
                    .tipo(TipoParcela.SUELO)
                    .estado(EstadoParcela.LIBRE) // Disponibles para comprar
                    .capacidad(4)
                    .zona(zonaNueva).build());
            }


            // --- CASO 2: BARCELONA ---
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
                    .imagenRuta("assets/img/montjuic.jpg")
                    .ayuntamiento(aytoBcn)
                    .build();
            cementerioRepository.save(montjuic);

            Zona zonaVistas = Zona.builder().nombre("Zona Vistas al Mar").numeroFilas(4).numeroColumnas(4).cementerio(montjuic).build();
            zonaRepository.save(zonaVistas);
            
            // Una parcela VIP en Barcelona
            parcelaRepository.save(Parcela.builder()
                    .fila(1).columna(1)
                    .tipo(TipoParcela.CRIPTA)
                    .estado(EstadoParcela.LIBRE)
                    .capacidad(10)
                    .zona(zonaVistas).build());

            // 4. USUARIOS DEL SISTEMA
            
            // ADMIN (Para que puedas entrar al panel de control)
            if (userRepository.findByEmail("admin@cementerio.com").isEmpty()) {
                User admin = User.builder()
                        .nombre("Rubén Admin")
                        .apellidos("Maderas")
                        .email("admin@cementerio.com") // USUARIO: admin@cementerio.com
                        .password(passwordEncoder.encode("admin123")) // PASSWORD: admin123
                        .telefono("600111222")
                        .direccion("Plaza Mayor, 1") // Añadir una dirección
                        .roles(Set.of(roleAdmin))
                        .ayuntamiento(aytoMadrid)
                        .build();
                userRepository.save(admin);
            }

            // OPERARIO (Para pruebas de trabajador)
            User operario = User.builder()
                    .nombre("Paco Operario")
                    .email("paco@madrid.es")
                    .password(passwordEncoder.encode("paco123"))
                    .telefono("600333444")
                    .direccion("C/ Trabajo, 5")
                    .roles(Set.of(roleOperador))
                    .ayuntamiento(aytoMadrid)
                    .build();
            userRepository.save(operario);

            // CLIENTE / FAMILIA (Quien paga y tiene los difuntos)
            Cliente familiaGomez = new Cliente();
            familiaGomez.setApellidos("Familia Navas");
            familiaGomez.setDni("12345678A");
            clienteRepository.save(familiaGomez);

            User userGomez = User.builder()
                    .nombre("Alba Navas")
                    .email("alba@familia.com") // Login del cliente
                    .password(passwordEncoder.encode("alba123"))
                    .telefono("666777888")
                    .direccion("Calle Pez, 22")
                    .roles(Set.of(roleCliente))
                    .cliente(familiaGomez) // Vinculado a la familia
                    .ayuntamiento(aytoMadrid)
                    .build();
            userRepository.save(userGomez);

            // 5. DIFUNTOS Y CONCESIONES (Datos Reales)
            
            // Recuperamos una parcela de Madrid creada arriba
            Parcela tumbaAbuelo = parcelaRepository.findAll().stream()
                    .filter(p -> p.getZona().getCementerio().getNombre().contains("Almudena"))
                    .findFirst().orElse(null);
            
            if(tumbaAbuelo != null) {
                // Creamos al difunto
                Difunto difunto1 = Difunto.builder()
                        .nombre("Antonio")
                        .apellidos("Gómez Pérez")
                        .dni("00112233X")
                        .fechaNacimiento(LocalDate.of(1930, 1, 1))
                        .fechaDefuncion(LocalDate.of(2023, 12, 1))
                        .fechaEnterramiento(LocalDate.of(2023, 12, 2))
                        .biografia("Amante del ajedrez, padre de 3 hijos y abuelo orgulloso. Descansa en paz.")
                        .parcela(tumbaAbuelo)
                        .build();
                difuntoRepository.save(difunto1);

                // Creamos la concesión (el contrato de la tumba)
                Concesion concesion = Concesion.builder()
                        .cliente(familiaGomez)
                        .parcela(tumbaAbuelo)
                        .fechaInicio(LocalDate.now().minusYears(1)) // Empezó hace un año
                        .fechaFin(LocalDate.now().plusYears(49))    // Dura 50 años
                        .precio(1500.00)
                        .activa(true)
                        .build();
                concesionRepository.save(concesion);
            }

            System.out.println("✅ DATOS CARGADOS CORRECTAMENTE: Madrid, Barcelona, Usuarios y Difuntos.");
        }
    }
}