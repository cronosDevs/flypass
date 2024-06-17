package com.flypass.transaccion.adapter.out.persistence.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaccion")
@Data
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "tipo_transaccion", nullable = false)
    private String tipoTransaccion;

    @DecimalMin(value = "0.00", message = "El monto no puede ser negativo")
    @Column(name = "monto", nullable = false, precision = 15, scale = 2)
    private BigDecimal monto;

    @Column(name = "fecha", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime fecha;

    @Column(name = "cuenta_origen", nullable = false, updatable = false)
    private Long cuentaOrigen;

    @Column(name = "cuenta_destino", nullable = false, updatable = false)
    private Long cuentaDestino;


}