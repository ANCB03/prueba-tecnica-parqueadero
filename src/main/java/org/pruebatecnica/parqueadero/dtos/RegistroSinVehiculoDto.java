package org.pruebatecnica.parqueadero.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistroSinVehiculoDto {
    private int idRegistro;

    private String tipoRegistro;

    private LocalDateTime fechaRegistro;

    private ParqueaderoDto parqueadero;
}
