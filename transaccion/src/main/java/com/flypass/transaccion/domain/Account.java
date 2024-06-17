package com.flypass.transaccion.domain;


import com.flypass.transaccion.domain.enums.EstadoCuentaEnum;
import com.flypass.transaccion.domain.enums.TipoCuentaEnum;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder(toBuilder = true)
public record Account(Long id, TipoCuentaEnum tipoCuenta, String numeroCuenta, EstadoCuentaEnum estado,
                      BigDecimal saldo,
                      Boolean exentaGMF, LocalDateTime fechaCreacion,
                      LocalDateTime fechaModificacion, Long clienteId) {
}
