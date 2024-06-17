package com.flypass.transaccion.domain;

import com.flypass.transaccion.domain.enums.TipoTransactionEnum;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Date;

@Builder(toBuilder = true)
public record Transaction(Long id, TipoTransactionEnum tipoTransaccion,
                          BigDecimal monto, Date fecha, Long cuentaOrigen,
                          Long cuentaDestino) {
}
