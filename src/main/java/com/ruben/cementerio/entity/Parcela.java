package com.ruben.cementerio.entity;

import com.ruben.cementerio.entity.parcela.EstadoParcela;
import com.ruben.cementerio.entity.parcela.TipoParcela;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "parcelas")
public class Parcela {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Coordenadas para pintar el mapa
    private Integer fila;
    private Integer columna;

    @Enumerated(EnumType.STRING)
    private TipoParcela tipo;

    @Enumerated(EnumType.STRING)
    private EstadoParcela estado;

    private Integer capacidad; // Cuantos difuntos caben

    @ManyToOne
    @JoinColumn(name = "zona_id")
    private Zona zona;
}