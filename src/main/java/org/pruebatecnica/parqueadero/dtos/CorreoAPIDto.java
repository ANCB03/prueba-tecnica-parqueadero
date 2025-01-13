package org.pruebatecnica.parqueadero.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CorreoAPIDto {
    @Email
    private String email;

    @Pattern(regexp = "^[A-Za-z0-9]{6}$", message = "La placa debe ser exactamente de 6 caracteres alfanuméricos, sin caracteres especiales ni la letra 'ñ'.")
    private String placa;

    @NotBlank(message = "Se requiere el mensaje")
    private String mensaje;

    @NotBlank(message = "Se requiere el nombre del parqueadero")
    private String parqueaderoNombre;
}
