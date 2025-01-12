package org.pruebatecnica.parqueadero.mappers;

import org.mapstruct.Mapper;
import org.pruebatecnica.parqueadero.dtos.IngresoSinVehiculoDto;
import org.pruebatecnica.parqueadero.entities.Ingreso;

@Mapper(componentModel = "spring")
public interface IngresoSinVehiculoMapper {
    Ingreso toEntity(IngresoSinVehiculoDto ingresoSinVehiculoDto);

    IngresoSinVehiculoDto toDto(Ingreso ingreso);
}
