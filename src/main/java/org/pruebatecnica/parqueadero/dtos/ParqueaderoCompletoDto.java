package org.pruebatecnica.parqueadero.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParqueaderoCompletoDto {
    private int idParqueadero;

    @NotBlank(message = "Se requiere el nombre del parqueadero")
    private String nombre;

    @NotNull(message = "Se requiere capacidad máxima")
    @Min(value = 1, message = "El valor debe ser mayor que 0.")
    private int capacidadMax;

    @NotNull(message = "Se requiere capacidad máxima")
    @Min(value = 0, message = "El valor debe ser mayor o igual a 0.")
    private int capacidadOcup;

    @NotNull(message = "El costo hora no puede ser vacío.")
    @DecimalMin(value = "0.00", message = "El costo hora debe ser mayor o igual a 0.00")
    private BigDecimal costoHora;

    private boolean estado;

    @Min(value = 1, message = "El campo id_rol debe ser mayor que 0")
    private int id_socio;

    private List<HistorialSinParqueaderoDto> historial = new ArrayList<>();

    private List<RegistroSinParqueaderoDto> registros = new ArrayList<>();
}
