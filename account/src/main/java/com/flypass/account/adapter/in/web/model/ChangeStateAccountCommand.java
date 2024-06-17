package com.flypass.account.adapter.in.web.model;


import com.flypass.account.domain.enums.EstadoCuentaEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangeStateAccountCommand {
    @NotNull(message = "estado can not be null")
    EstadoCuentaEnum estado;
}
