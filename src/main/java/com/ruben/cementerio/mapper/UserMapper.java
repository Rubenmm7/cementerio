package com.ruben.cementerio.mapper;

import com.ruben.cementerio.dto.UserRequestDTO;
import com.ruben.cementerio.dto.UserResponseDTO;
import com.ruben.cementerio.entity.Rol;
import com.ruben.cementerio.entity.User;

public class UserMapper {

   // RequestDTO -> Entity
    public static User toEntity(UserRequestDTO dto, Rol rol) {
       return User.builder()
                .nombre(dto.getNombre())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .telefono(dto.getTelefono())
                .direccion(dto.getDireccion())
                .rol(rol)
                .build();
    }

    // Entity -> ResponseDTO
     public static UserResponseDTO toResponse(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .nombre(user.getNombre())
                .email(user.getEmail())
                .rol(user.getRol().getTipo())
                .build();
    }
}