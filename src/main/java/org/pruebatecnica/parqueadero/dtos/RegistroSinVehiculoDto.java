package org.pruebatecnica.parqueadero.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistroSinVehiculoDto {
    private int idRegistro;

    private String tipoRegistro;

    private LocalDateTime fechaRegistro;

    @Min(value = 1, message = "El campo id_parqueadero debe ser mayor que 0")
    private int id_parqueadero;
}
