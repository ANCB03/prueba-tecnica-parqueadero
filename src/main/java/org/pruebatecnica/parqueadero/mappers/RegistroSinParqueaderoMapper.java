package org.pruebatecnica.parqueadero.mappers;

import org.mapstruct.Mapper;
import org.pruebatecnica.parqueadero.dtos.RegistroSinParqueaderoDto;
import org.pruebatecnica.parqueadero.entities.Registro;

@Mapper(componentModel = "spring")
public interface RegistroSinParqueaderoMapper {
    Registro toEntity(RegistroSinParqueaderoDto registroSinParqueaderoDto);

    RegistroSinParqueaderoDto toDto(Registro registro);
}
