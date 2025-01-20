package org.pruebatecnica.parqueadero.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistorialSinParqueaderoDto {
    private int idHistorial;

    private LocalDateTime fechaEntrada;

    private LocalDateTime fechaSalida;

    private BigDecimal monto;

    @NotBlank(message = "se requiere la placa del vehiculo")
    private String id_vehiculo;
}
