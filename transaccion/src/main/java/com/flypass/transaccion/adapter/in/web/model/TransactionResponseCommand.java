package com.flypass.transaccion.adapter.in.web.model;

import com.flypass.transaccion.domain.enums.TipoTransactionEnum;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Date;

@Builder
public record TransactionResponseCommand(Long id, TipoTransactionEnum tipoTransaccion,
                                         BigDecimal monto, Date fecha, Long cuentaOrigen,
                                         Long cuentaDestino) {
}
