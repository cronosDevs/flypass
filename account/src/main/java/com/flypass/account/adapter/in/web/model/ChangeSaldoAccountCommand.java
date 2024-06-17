package com.flypass.account.adapter.in.web.model;


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
public class ChangeSaldoAccountCommand {
    @NotNull(message = "saldo can not be null")
    BigDecimal saldo;
}
