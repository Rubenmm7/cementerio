package com.ruben.cementerio.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruben.cementerio.dto.UserRequestDTO;
import com.ruben.cementerio.dto.UserResponseDTO;
import com.ruben.cementerio.entity.Ayuntamiento;
import com.ruben.cementerio.entity.Rol;
import com.ruben.cementerio.entity.User;
import com.ruben.cementerio.repository.RolRepository;
import com.ruben.cementerio.repository.AyuntamientoRepository;
import com.ruben.cementerio.repository.UserRepository;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final RolRepository rolRepository;
    private final AyuntamientoRepository ayuntamientoRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper,
            RolRepository rolRepository, AyuntamientoRepository ayuntamientoRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.rolRepository = rolRepository;
        this.ayuntamientoRepository = ayuntamientoRepository;
    }

    // LISTAR: Devuelve DTOs (sin contraseña)
    public List<UserResponseDTO> listarTodos() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserResponseDTO.class))
                .collect(Collectors.toList());
    }

    // CREAR: Recibe DTO con pass, guarda y devuelve DTO limpio
    public UserResponseDTO crear(UserRequestDTO dto) {
        if (dto.getAyuntamientoId() == null) {
            throw new IllegalArgumentException("El Ayuntamiento es obligatorio para crear un usuario.");
        }

        User user = modelMapper.map(dto, User.class);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        // Asignar rol
        if (dto.getRole() != null) {
            Rol rol = rolRepository.findByTipo(dto.getRole())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + dto.getRole()));
            user.setRoles(Set.of(rol));
        }

        // Asignar ayuntamiento
        Ayuntamiento ayuntamiento = ayuntamientoRepository.findById(dto.getAyuntamientoId())
                .orElseThrow(() -> new RuntimeException("Ayuntamiento no encontrado: " + dto.getAyuntamientoId()));
        user.setAyuntamiento(ayuntamiento);

        User guardado = userRepository.save(user);
        return modelMapper.map(guardado, UserResponseDTO.class);
    }

    public Optional<UserResponseDTO> actualizar(Long id, UserRequestDTO dto) {
        return userRepository.findById(id)
                .map(userExistente -> {
                    // Actualiza campos básicos
                    userExistente.setNombre(dto.getNombre());
                    userExistente.setApellidos(dto.getApellidos());
                    userExistente.setEmail(dto.getEmail());
                    userExistente.setTelefono(dto.getTelefono());
                    userExistente.setDireccion(dto.getDireccion());
                    if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
                        userExistente.setPassword(passwordEncoder.encode(dto.getPassword()));
                    }

                    // Actualiza el rol si se proporciona
                    if (dto.getRole() != null) {
                        Rol nuevoRol = rolRepository.findByTipo(dto.getRole())
                                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + dto.getRole()));
                        userExistente.setRoles(Set.of(nuevoRol));
                    }

                    // Actualiza el ayuntamiento si se proporciona
                    if (dto.getAyuntamientoId() != null) {
                        Ayuntamiento ayuntamiento = ayuntamientoRepository.findById(dto.getAyuntamientoId())
                                .orElseThrow(() -> new RuntimeException("Ayuntamiento no encontrado: " + dto.getAyuntamientoId()));
                        userExistente.setAyuntamiento(ayuntamiento);
                    }
                    User actualizado = userRepository.save(userExistente);
                    return modelMapper.map(actualizado, UserResponseDTO.class);
                });
    }

    public void eliminar(Long id) {
        userRepository.deleteById(id);
    }
    
    // Métodos auxiliares
    public Optional<User> obtenerEntidadPorId(Long id) {
        return userRepository.findById(id);
    }
}