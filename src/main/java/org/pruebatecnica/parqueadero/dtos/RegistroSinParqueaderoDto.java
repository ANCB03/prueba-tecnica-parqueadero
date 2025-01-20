package org.pruebatecnica.parqueadero.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistroSinParqueaderoDto {
    private int idRegistro;

    private String tipoRegistro;

    private LocalDateTime fechaRegistro;

    @NotBlank(message = "se requiere la placa del vehiculo")
    private String id_vehiculo;
}
