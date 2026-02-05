package com.ruben.cementerio.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ConcesionDTO {
    private Long id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Double precio;
    private boolean activa;

    private Long clienteId;
    private String clienteEmail; // Para mostrarlo rápido en tablas
    private Long parcelaId;
    private String parcelaTipo; // Para saber qué se alquiló
}