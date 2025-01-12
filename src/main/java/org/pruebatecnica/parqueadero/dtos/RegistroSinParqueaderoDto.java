package org.pruebatecnica.parqueadero.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistroSinParqueaderoDto {
    private int idRegistro;

    private String tipoRegistro;

    private LocalDate fechaRegistro;

    private VehiculoDto vehiculo;
}
