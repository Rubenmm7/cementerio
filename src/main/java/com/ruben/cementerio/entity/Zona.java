package com.ruben.cementerio.entity;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "zonas")
public class Zona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    private int numeroFilas;
    private int numeroColumnas;

    @ManyToOne
    @JoinColumn(name = "cementerio_id", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private Cementerio cementerio;

    @OneToMany(mappedBy = "zona", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Parcela> parcelas;
}