package org.pruebatecnica.parqueadero.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistroDto {
    private int idRegistro;

    @NotBlank(message = "se requiere tipo de registro")
    @Pattern(regexp = "^(ENTRADA|SALIDA)$", message = "El tipo de registro debe ser 'ENTRADA' o 'SALIDA'")
    private String tipoRegistro;

    private LocalDateTime fechaRegistro;

    @NotBlank(message = "se requiere la placa del vehiculo")
    private String id_vehiculo;

    @Min(value = 1, message = "El campo idid_parqueadero_rol debe ser mayor que 0")
    private int id_parqueadero;
}
