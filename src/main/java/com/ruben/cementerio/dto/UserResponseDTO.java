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
    private Integer id;
    private String nombre;
    private String email;
    private TipoRol rol;
 
}
