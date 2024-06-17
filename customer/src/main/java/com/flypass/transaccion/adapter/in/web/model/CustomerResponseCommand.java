package com.flypass.transaccion.adapter.in.web.model;

import com.flypass.transaccion.domain.enums.TipoIdentificacionEnum;
import lombok.Builder;

import java.util.Date;

@Builder
public record CustomerResponseCommand(Long id,
                                      TipoIdentificacionEnum tipoIdentificacion,
                                      String numeroIdentificacion,
                                      String nombres,
                                      String apellidos,
                                      String correoElectronico,
                                      Date fechaNacimiento,
                                      Date fechaModificacion,
                                      Date fechaCreacion,
                                      Boolean enable) {
}
