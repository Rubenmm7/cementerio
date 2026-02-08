package com.ruben.cementerio.entity;

import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cementerios")
public class Cementerio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String direccion;
    private String poblacion;
    private String provincia;
    private String codigoPostal;
    private String emailContacto;
    private String imagenRuta;

    @ManyToOne
    @JoinColumn(name = "ayuntamiento_id", nullable = false)
    @ToString.Exclude
    private Ayuntamiento ayuntamiento;

    @OneToMany(mappedBy = "cementerio", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Zona> zonas;
}