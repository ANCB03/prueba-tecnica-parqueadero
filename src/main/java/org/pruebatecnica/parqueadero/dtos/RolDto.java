package org.pruebatecnica.parqueadero.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RolDto {
    private int idRol;

    @NotBlank(message = "Se requiere el nombre del rol")
    private String nombre;
}
