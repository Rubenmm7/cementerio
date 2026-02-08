package com.ruben.cementerio.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruben.cementerio.entity.parcela.EstadoParcela;
import com.ruben.cementerio.entity.parcela.TipoParcela;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "parcelas")
public class Parcela {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer fila;
    private Integer columna;

    @Enumerated(EnumType.STRING)
    private TipoParcela tipo;

    @Enumerated(EnumType.STRING)
    private EstadoParcela estado;

    private Integer capacidad;

    @ManyToOne
    @JoinColumn(name = "zona_id")
    @JsonIgnore
    @ToString.Exclude
    private Zona zona;
}