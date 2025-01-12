package org.pruebatecnica.parqueadero.mappers;

import org.mapstruct.Mapper;
import org.pruebatecnica.parqueadero.dtos.ParqueaderoCompletoDto;
import org.pruebatecnica.parqueadero.entities.Parqueadero;

@Mapper(componentModel = "spring")
public interface ParqueaderoCompletoMapper {
    Parqueadero toEntity(ParqueaderoCompletoDto parqueaderoCompletoDto);

    ParqueaderoCompletoDto toDto(Parqueadero parqueadero);
}
