package com.ruben.cementerio.dto;

import com.ruben.cementerio.entity.parcela.EstadoParcela;
import com.ruben.cementerio.entity.parcela.TipoParcela;

import lombok.Data;

@Data
public class ParcelaDTO {
    private Long id;
    private Integer fila;
    private Integer columna;
    private TipoParcela tipo;
    private EstadoParcela estado;
    private Integer capacidad;
    
    private Long zonaId;
    private String zonaNombre;
    private Long cementerioId;
}