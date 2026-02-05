package com.ruben.cementerio.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaPago;
    private Double cantidad;
    private String metodoPago; // Tarjeta, Transferencia, Efectivo
    private String referenciaTransaccion; // ID de pasarela de pago o nº operación

    // El pago pertenece a una factura
    @ManyToOne
    @JoinColumn(name = "factura_id", nullable = false)
    private Factura factura;
}