package com.ruben.cementerio.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "facturas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String numeroFactura;

    private LocalDateTime fechaEmision;
    private Double importeTotal;
    private boolean pagada;
    private String clienteNombreSnapshot;
    private String clienteDniSnapshot;
    private String clienteDireccionSnapshot;

    // Relación con el usuario que paga
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private User cliente;

    // Relación inversa con servicios prestados
    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    private List<ImplementacionService> serviciosPrestados;
    
    // Relación inversa con pagos
    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    private List<Pago> pagos;
}