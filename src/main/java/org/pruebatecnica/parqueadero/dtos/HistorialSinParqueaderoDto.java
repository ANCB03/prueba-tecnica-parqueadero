package org.pruebatecnica.parqueadero.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistorialSinParqueaderoDto {
    private int idHistorial;

    private LocalDate fechaEntrada;

    private LocalDate fechaSalida;

    private BigDecimal monto;

    private VehiculoDto vehiculo;
}
