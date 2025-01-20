package org.pruebatecnica.parqueadero.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.pruebatecnica.parqueadero.dtos.ParqueaderoCompletoDto;
import org.pruebatecnica.parqueadero.entities.Parqueadero;

@Mapper(componentModel = "spring")
public interface ParqueaderoCompletoMapper {
    @Mapping(source = "id_socio", target = "socio.idUsuario")
    Parqueadero toEntity(ParqueaderoCompletoDto parqueaderoCompletoDto);
    @Mapping(source = "socio.idUsuario", target = "id_socio")
    ParqueaderoCompletoDto toDto(Parqueadero parqueadero);
}
