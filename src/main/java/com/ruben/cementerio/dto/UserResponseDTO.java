package com.ruben.cementerio.dto;

import com.ruben.cementerio.entity.TipoRol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String email;
    private String direccion;
    private TipoRol role;
 
}
