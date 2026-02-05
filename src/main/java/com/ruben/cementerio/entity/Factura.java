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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "facturas")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String numeroFactura;

    private LocalDateTime fechaEmision;
    private Double importeTotal;
    
    private boolean pagada;

    // --- SNAPSHOT DEL CLIENTE (Datos congelados al momento de facturar) ---
    private String clienteNombreSnapshot;
    private String clienteDniSnapshot;
    private String clienteDireccionSnapshot;
    // ----------------------------------------------------------------------

    // Relación real con el cliente para búsquedas
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private User cliente;

    // Relación con los servicios prestados (Items de la factura)
    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    private List<ImplementacionService> serviciosPrestados;
    
    // Relación con los pagos recibidos
    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    private List<Pago> pagos;
}