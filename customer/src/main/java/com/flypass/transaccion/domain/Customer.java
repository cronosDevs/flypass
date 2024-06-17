package com.flypass.transaccion.domain;

import com.flypass.transaccion.domain.enums.TipoIdentificacionEnum;
import lombok.Builder;

import java.util.Date;

@Builder(toBuilder = true)
public record Customer(Long id,
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
