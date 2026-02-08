package com.ruben.cementerio.dto;

import lombok.Data;

@Data
public class CementerioDTO {
    private Long id;
    private String nombre;
    private String direccion;
    private String poblacion;
    private String provincia;
    private String codigoPostal;
    private String emailContacto;
    private String imagenRuta;
    private Long ayuntamientoId; 
    private String nombreAyuntamiento; 
}