package com.ruben.cementerio.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class DifuntoDTO {
    private Long id;
    private String nombre;
    private String apellidos;
    private String dni;
    private LocalDate fechaNacimiento;
    private LocalDate fechaDefuncion;
    private LocalDate fechaEnterramiento;
    private String biografia;
    private String fotoUrl;
    
    private Long parcelaId;
    private String nombreCementerio; //Para mostrar en listados
}