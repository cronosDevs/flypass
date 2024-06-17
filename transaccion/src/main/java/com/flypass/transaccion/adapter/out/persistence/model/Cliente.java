package com.flypass.transaccion.adapter.out.persistence.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String tipoIdentificacion;

    @NotNull
    @Column(unique = true)
    private String numeroIdentificacion;

    @NotNull
    @Size(min = 2)
    private String nombres;

    @NotNull
    @Size(min = 2)
    private String apellidos;

    @NotNull
    @Email
    private String correoElectronico;

    @NotNull
    private LocalDate fechaNacimiento;

    @CreationTimestamp
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    private LocalDateTime fechaModificacion;

    @NotNull
    private Boolean enable = true;

}
