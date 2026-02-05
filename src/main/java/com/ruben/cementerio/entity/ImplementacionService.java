package com.ruben.cementerio.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "implementacionService")
public class ImplementacionService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date fecha_realizacion;
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
