package com.ruben.cementerio.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class FacturaDTO {
    private Long id;
    private String numeroFactura;
    private LocalDateTime fechaEmision;
    private Double importeTotal;
    private boolean pagada;
    
    // Datos históricos del cliente
    private String clienteNombre;
    private String clienteDni;
    private Long clienteIdActual;
    
    // Lista de servicios incluidos en esta factura
    private List<ImplementacionServiceDTO> servicios;
}