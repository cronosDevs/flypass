package com.flypass.account.adapter.in.web.model;


import com.flypass.account.domain.enums.EstadoCuentaEnum;
import com.flypass.account.domain.enums.TipoCuentaEnum;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record AccountResponseCommand(Long id, TipoCuentaEnum tipoCuenta, String numeroCuenta, EstadoCuentaEnum estado,
                                     BigDecimal saldo,
                                     Boolean exentaGMF, LocalDateTime fechaCreacion,
                                     LocalDateTime fechaModificacion, Long clienteId) {
}
