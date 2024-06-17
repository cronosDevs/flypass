package com.flypass.account.adapter.in.web.model;


import com.flypass.account.domain.enums.TipoCuentaEnum;
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
public class CreateAccountCommand {
    @NotNull(message = "tipoCuenta can not be null")
    private TipoCuentaEnum tipoCuenta;
    @NotNull(message = "saldo can not be null")
    private BigDecimal saldo;
    @NotNull(message = "exentaGMF can not be null")
    private Boolean exentaGMF;
    @NotNull(message = "clienteId can not be null")
    private Long clienteId;
}
