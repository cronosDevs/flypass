package com.flypass.transaccion.adapter.in.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCustomerCommand {
    private String nombres;
    private String apellidos;
    private String correoElectronico;
}
