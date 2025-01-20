package org.pruebatecnica.parqueadero.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.pruebatecnica.parqueadero.dtos.ParqueaderoDto;
import org.pruebatecnica.parqueadero.entities.Parqueadero;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParqueaderoMapper {
    @Mapping(source = "id_socio", target = "socio.idUsuario")
    Parqueadero toEntity(ParqueaderoDto parqueaderoDto);
    @Mapping(source = "socio.idUsuario", target = "id_socio")
    ParqueaderoDto toDto(Parqueadero parqueadero);

    List<ParqueaderoDto> toParqueaderolist(List<Parqueadero> entityList);
}
