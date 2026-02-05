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
    private Ayuntamiento ayuntamiento; // Un ayuntamiento tiene varios cementerios

    // Un cementerio tiene muchas zonas
    @OneToMany(mappedBy = "cementerio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Zona> zonas;
}