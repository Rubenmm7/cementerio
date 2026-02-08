package com.ruben.cementerio.mapper;

import java.util.Set;

import com.ruben.cementerio.dto.UserRequestDTO;
import com.ruben.cementerio.dto.UserResponseDTO;
import com.ruben.cementerio.entity.Ayuntamiento;
import com.ruben.cementerio.entity.Rol;
import com.ruben.cementerio.entity.User;

public class UserMapper {

    // RequestDTO -> Entity
    public static User toEntity(UserRequestDTO dto, Rol rol, Ayuntamiento ayuntamiento) {
        return User.builder()
                .nombre(dto.getNombre())
                .apellidos(dto.getApellidos())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .telefono(dto.getTelefono())
                .direccion(dto.getDireccion())
                .roles(Set.of(rol))
                .ayuntamiento(ayuntamiento)
                .build();
    }

    // Entity -> ResponseDTO
    public static UserResponseDTO toResponse(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .nombre(user.getNombre())
                .apellidos(user.getApellidos())
                .telefono(user.getTelefono())
                .email(user.getEmail())
                .direccion(user.getDireccion())
                .ayuntamientoId(user.getAyuntamiento() != null ? user.getAyuntamiento().getId() : null)
                .nombreAyuntamiento(user.getAyuntamiento() != null ? user.getAyuntamiento().getNombre() : null)
                .role(user.getRoles().stream().findFirst().map(Rol::getTipo).orElse(null))
                .build();
    }
}