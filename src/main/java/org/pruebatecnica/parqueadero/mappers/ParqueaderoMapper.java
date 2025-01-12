package org.pruebatecnica.parqueadero.mappers;

import org.mapstruct.Mapper;
import org.pruebatecnica.parqueadero.dtos.ParqueaderoDto;
import org.pruebatecnica.parqueadero.entities.Parqueadero;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParqueaderoMapper {
    Parqueadero toEntity(ParqueaderoDto parqueaderoDto);

    ParqueaderoDto toDto(Parqueadero parqueadero);

    List<ParqueaderoDto> toParqueaderolist(List<Parqueadero> entityList);
}
