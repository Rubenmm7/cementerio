package com.ruben.cementerio.entity;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
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
    private String dni;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "fecha_defuncion")
    private LocalDate fechaDefuncion;

    @Column(name = "fecha_enterramiento")
    private LocalDate fechaEnterramiento;

    @Column(columnDefinition = "TEXT")
    private String biografia;

    private String fotoUrl;
    private String certificadoDefuncionUrl;

    @ManyToOne
    @JoinColumn(name = "parcela_id")
    @ToString.Exclude
    private Parcela parcela;
}