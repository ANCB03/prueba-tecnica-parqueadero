package org.pruebatecnica.parqueadero.mappers;

import org.mapstruct.Mapper;
import org.pruebatecnica.parqueadero.dtos.IngresoSinParqueaderoDto;
import org.pruebatecnica.parqueadero.entities.Ingreso;

@Mapper(componentModel = "spring")
public interface IngresoSinParqueaderoMapper {
    Ingreso toEntity(IngresoSinParqueaderoDto ingresoSinParqueaderoDto);

    IngresoSinParqueaderoDto toDto(Ingreso ingreso);
}
