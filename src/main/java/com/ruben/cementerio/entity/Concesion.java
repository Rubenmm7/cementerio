package com.ruben.cementerio.entity;

import java.time.LocalDate;

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
@Table(name = "concesiones")
public class Concesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Double precio; // Precio histórico
    private boolean activa;


    @ManyToOne
    @JoinColumn(name = "cliente_id") // Vinculamos con la tabla de usuarios
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "parcela_id")
    private Parcela parcela;
}