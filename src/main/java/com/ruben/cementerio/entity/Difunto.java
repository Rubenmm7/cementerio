package com.ruben.cementerio.entity;

import java.time.LocalDate;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "difuntos")
public class Difunto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellidos;
    private String dni; // Util para registros

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "fecha_defuncion")
    private LocalDate fechaDefuncion;

    @Column(name = "fecha_enterramiento")
    private LocalDate fechaEnterramiento; // Clave para calcular si se puede exhumar (5/10 años)

    @Column(columnDefinition = "TEXT")
    private String biografia; // Para el perfil de homenaje

    private String fotoUrl;
    private String certificadoDefuncionUrl; // Documentación legal

    //Un difunto está en una parcela
    @ManyToOne
    @JoinColumn(name = "parcela_id")
    private Parcela parcela;
}