package com.ruben.cementerio.dto;

import lombok.Data;

@Data
public class CementerioDTO {
    private Long id;
    private String nombre;
    private String direccion;
    private String poblacion;
    private String provincia;
    private String emailContacto;
    private String nombreAyuntamiento; // Para mostrarlo sin traer todo el objeto
}