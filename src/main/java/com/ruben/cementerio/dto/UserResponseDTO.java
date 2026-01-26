package com.ruben.cementerio.dto;

import com.ruben.cementerio.entity.TipoRol;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDTO {
    private Long id;
    private String nombre;
    private String email;
    private TipoRol rol;
 
}
