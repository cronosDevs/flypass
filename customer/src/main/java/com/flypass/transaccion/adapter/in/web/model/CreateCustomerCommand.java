package com.flypass.transaccion.adapter.in.web.model;

import com.flypass.transaccion.domain.enums.TipoIdentificacionEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerCommand {
    @NotNull(message = "tipoIdentificacion can not be null")
    private TipoIdentificacionEnum tipoIdentificacion;
    @NotNull(message = "numeroIdentificacion can not be null")
    @NotBlank(message = "numeroIdentificacion can not be empty")
    private String numeroIdentificacion;
    @NotNull(message = "nombres can not be null")
    @NotBlank(message = "nombres can not be empty")
    @Size(min = 2, message = "nombres can not be less than 2 characters")
    private String nombres;
    @NotNull(message = "apellido can not be null")
    @NotBlank(message = "apellido can not be empty")
    @Size(min = 2, message = "apellido can not be less than 2 characters")
    private String apellidos;
    @NotNull(message = "correoElectronico can not be null")
    @NotBlank(message = "correoElectronico can not be empty")
    @Email(message = "correoElectronico must be an email format")
    private String correoElectronico;
    @NotNull(message = "fechaNacimiento can not be null")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date fechaNacimiento;
}
