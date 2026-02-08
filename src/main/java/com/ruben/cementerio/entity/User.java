package com.ruben.cementerio.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 45)
    private String nombre;

    @Column(nullable = true, length = 50)
    private String apellidos;

    @Column(nullable = false, unique = true, length = 245)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 45)
    private String telefono;

    @Column(nullable = false, length = 345)
    private String direccion;

    @ManyToMany(fetch = FetchType.EAGER) // Eager para cargar roles al login
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private Set<Rol> roles;
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = true) 
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "ayuntamiento_id", nullable = false)
    private Ayuntamiento ayuntamiento;
}