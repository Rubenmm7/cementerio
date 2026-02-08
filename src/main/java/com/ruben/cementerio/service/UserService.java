package com.ruben.cementerio.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruben.cementerio.dto.UserRequestDTO;
import com.ruben.cementerio.dto.UserResponseDTO;
import com.ruben.cementerio.entity.Ayuntamiento;
import com.ruben.cementerio.entity.Rol;
import com.ruben.cementerio.entity.User;
import com.ruben.cementerio.mapper.UserMapper;
import com.ruben.cementerio.repository.AyuntamientoRepository;
import com.ruben.cementerio.repository.RolRepository;
import com.ruben.cementerio.repository.UserRepository;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolRepository rolRepository;
    private final AyuntamientoRepository ayuntamientoRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
            RolRepository rolRepository, AyuntamientoRepository ayuntamientoRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.rolRepository = rolRepository;
        this.ayuntamientoRepository = ayuntamientoRepository;
    }

    // Usamos el Mapper para que el DTO lleve el nombre del ayuntamiento y el ID
    public List<UserResponseDTO> listarTodos() {
        return userRepository.findAll().stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }

    public UserResponseDTO crear(UserRequestDTO dto) {
        // Validar que venga el ayuntamiento
        if (dto.getAyuntamientoId() == null) {
            throw new IllegalArgumentException("El Ayuntamiento es obligatorio para crear un usuario.");
        }

        // Buscar el Ayuntamiento en BBDD
        Ayuntamiento ayuntamiento = ayuntamientoRepository.findById(dto.getAyuntamientoId())
                .orElseThrow(() -> new RuntimeException("Ayuntamiento no encontrado con ID: " + dto.getAyuntamientoId()));

        // Buscar el Rol en BBDD
        Rol rol = rolRepository.findByTipo(dto.getRole())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + dto.getRole()));

        // Convertir a Entidad usando Mapper
        User user = UserMapper.toEntity(dto, rol, ayuntamiento);

        //Encriptar la contraseña
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        // Guardar y devolver convertido
        User guardado = userRepository.save(user);
        return UserMapper.toResponse(guardado);
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

                    // Actualizar contraseña solo si viene informada
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
                        Ayuntamiento nuevoAyuntamiento = ayuntamientoRepository.findById(dto.getAyuntamientoId())
                                .orElseThrow(() -> new RuntimeException("Ayuntamiento no encontrado: " + dto.getAyuntamientoId()));
                        userExistente.setAyuntamiento(nuevoAyuntamiento);
                    }

                    // Guardar
                    User actualizado = userRepository.save(userExistente);
                    
                    // Devolver convertido con Mapper
                    return UserMapper.toResponse(actualizado);
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