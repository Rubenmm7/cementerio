package com.ruben.cementerio.dto;

import com.ruben.cementerio.entity.TipoRol;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    private String nombre;
    private String email;
    private String password;
    private String telefono;
    private String direccion;
    private TipoRol rol;
}

