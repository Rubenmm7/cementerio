package com.ruben.cementerio.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "implementacion_service")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImplementacionService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_realizacion")
    private LocalDateTime fechaRealizacion;

    private String foto;

    @ManyToOne
    @JoinColumn(name = "facturacion_id")
    private Factura factura;

    @ManyToOne
    @JoinColumn(name = "parcela_id")
    private Parcela parcela;

    @ManyToOne
    @JoinColumn(name = "servicio_id")
    private Servicio servicio;
}