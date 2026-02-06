package com.ruben.cementerio.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruben.cementerio.dto.UserRequestDTO;
import com.ruben.cementerio.entity.User;
import com.ruben.cementerio.repository.UserRepository;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper; // Usamos esto para convertir DTO a Entity

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public List<User> listarTodos() {
        return userRepository.findAll();
    }

    // ESTE ES EL MÉTODO QUE FALTABA Y CAUSABA EL ERROR
    public User crear(UserRequestDTO dto) {
        // 1. Convertimos DTO a Entidad User
        User user = modelMapper.map(dto, User.class);
        
        // 2. Encriptamos contraseña (IMPORTANTE)
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        
        // 3. Guardamos
        return userRepository.save(user);
    }

    public Optional<User> obtenerPorId(Long id) {
        return userRepository.findById(id);
    }
    
    public Optional<User> buscarPorEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void eliminar(Long id) {
        userRepository.deleteById(id);
    }
}