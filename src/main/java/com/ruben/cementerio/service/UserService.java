package com.ruben.cementerio.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruben.cementerio.dto.UserRequestDTO;
import com.ruben.cementerio.dto.UserResponseDTO;
import com.ruben.cementerio.entity.User;
import com.ruben.cementerio.repository.UserRepository;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    // LISTAR: Devuelve DTOs (sin contraseña)
    public List<UserResponseDTO> listarTodos() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserResponseDTO.class))
                .collect(Collectors.toList());
    }

    // CREAR: Recibe DTO con pass, guarda y devuelve DTO limpio
    public UserResponseDTO crear(UserRequestDTO dto) {
        User user = modelMapper.map(dto, User.class);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        
        // Asignar rol por def  ecto o lógica extra aquí si fuera necesario
        
        User guardado = userRepository.save(user);
        return modelMapper.map(guardado, UserResponseDTO.class);
    }

    public void eliminar(Long id) {
        userRepository.deleteById(id);
    }
    
    // Métodos auxiliares
    public Optional<User> obtenerEntidadPorId(Long id) {
        return userRepository.findById(id);
    }
}