package com.flypass.transaccion.adapter.in.web.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateConsignmentCommand {
    @NotNull(message = "monto can not be null")
    private BigDecimal monto;
    @NotNull(message = "cuentaDestino can not be null")
    private Long cuentaDestino;
}
