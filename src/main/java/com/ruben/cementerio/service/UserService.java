package com.ruben.cementerio.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ruben.cementerio.dto.UserRequestDTO;
import com.ruben.cementerio.dto.UserResponseDTO;
import com.ruben.cementerio.entity.Rol;
import com.ruben.cementerio.entity.User;
import com.ruben.cementerio.mapper.UserMapper;
import com.ruben.cementerio.repository.RolRepository;
import com.ruben.cementerio.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RolRepository rolRepository;

    public UserService(UserRepository userRepository, RolRepository rolRepository) {
        this.userRepository = userRepository;
        this.rolRepository = rolRepository;
    }

    public UserResponseDTO crear(UserRequestDTO dto) {

        //Buscar el rol en BD
        Rol rol = rolRepository.findByTipo(dto.getRol())
            .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        //Convertir DTO a entidad
        User user = UserMapper.toEntity(dto, rol);

        //Guardar
        User guardado = userRepository.save(user);

        //Devolvemos respuesta
        return UserMapper.toResponse(guardado);
    }

    public List<UserResponseDTO> listar() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }
}
