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
public class IngresoDto {
    private int idIngreso;

    private BigDecimal monto;

    private LocalDate fechaPago;

    private ParqueaderoDto parqueadero;

    private VehiculoDto vehiculo;
}
