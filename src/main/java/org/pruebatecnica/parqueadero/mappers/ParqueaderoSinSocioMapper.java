package org.pruebatecnica.parqueadero.mappers;

import org.mapstruct.Mapper;
import org.pruebatecnica.parqueadero.dtos.ParqueaderoSinSocioDto;
import org.pruebatecnica.parqueadero.entities.Parqueadero;

@Mapper(componentModel = "spring")
public interface ParqueaderoSinSocioMapper {
    Parqueadero toEntity(ParqueaderoSinSocioDto parqueaderoSinSocioDto);

    ParqueaderoSinSocioDto toDto(Parqueadero parqueadero);
}
