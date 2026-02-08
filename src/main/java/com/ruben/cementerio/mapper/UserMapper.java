package com.ruben.cementerio.mapper;

import java.util.Set;

import com.ruben.cementerio.dto.UserRequestDTO;
import com.ruben.cementerio.dto.UserResponseDTO;
import com.ruben.cementerio.entity.Rol;
import com.ruben.cementerio.entity.User;

public class UserMapper {

   // RequestDTO -> Entity
    public static User toEntity(UserRequestDTO dto, Rol rol) {
       return User.builder()
                .nombre(dto.getNombre())
                .apellidos(dto.getApellidos())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .telefono(dto.getTelefono())
                .roles(Set.of(rol))
                .build();
    }

    // Entity -> ResponseDTO
     public static UserResponseDTO toResponse(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .nombre(user.getNombre())
                .apellidos(user.getApellidos())
                .email(user.getEmail())
                .role(user.getRoles().stream().findFirst().map(Rol::getTipo).orElse(null))
                .build();
    }
}