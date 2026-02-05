package com.ruben.cementerio.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "zonas")
public class Zona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    // Dimensiones para pintar el mapa cuadriculado
    private int numeroFilas;    // Filas y columnas de cada zona
    private int numeroColumnas;

    //Zona pertenece a Cementerio
    @ManyToOne
    @JoinColumn(name = "cementerio_id", nullable = false)
    private Cementerio cementerio;

    // Una zona tiene muchas parcelas (nichos, suelos, etc.)
    @OneToMany(mappedBy = "zona", cascade = CascadeType.ALL)
    private List<Parcela> parcelas;
}