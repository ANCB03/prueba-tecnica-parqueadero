package org.pruebatecnica.parqueadero.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private VehiculoDto vehiculo;

    private ParqueaderoDto parqueadero;
}
