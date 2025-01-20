package org.pruebatecnica.parqueadero.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestEntradaSalida {
    @Pattern(regexp = "^[A-Za-z0-9]{6}$", message = "La placa debe ser exactamente de 6 caracteres alfanuméricos, sin caracteres especiales ni la letra 'ñ'.")
    private String placa;

    @Min(value = 1, message = "El campo id_parqueadero debe ser mayor que 0")
    private int idParqueadero;
}
