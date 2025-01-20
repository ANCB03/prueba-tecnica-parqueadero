package org.pruebatecnica.parqueadero.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistorialSinVehiculoDto {
    private int idHistorial;

    private LocalDateTime fechaEntrada;

    private LocalDateTime fechaSalida;

    private BigDecimal monto;

    @Min(value = 1, message = "El campo id_parqueadero debe ser mayor que 0")
    private int id_parqueadero;
}
