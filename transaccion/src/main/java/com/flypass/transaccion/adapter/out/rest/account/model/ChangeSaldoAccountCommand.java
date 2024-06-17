package com.flypass.transaccion.adapter.out.rest.account.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangeSaldoAccountCommand {
    BigDecimal saldo;
}
