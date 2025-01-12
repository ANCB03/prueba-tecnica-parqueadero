package org.pruebatecnica.parqueadero.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehiculoDto {
    @Pattern(regexp = "^[A-Za-z0-9]{6}$", message = "La placa debe ser exactamente de 6 caracteres alfanuméricos, sin caracteres especiales ni la letra 'ñ'.")
    private String placa;

    private String marca;

    private String modelo;

    private String color;

    private int contador;

    private boolean estado;
}
