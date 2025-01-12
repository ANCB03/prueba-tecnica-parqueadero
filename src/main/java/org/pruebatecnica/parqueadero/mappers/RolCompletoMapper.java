package org.pruebatecnica.parqueadero.mappers;

import org.mapstruct.Mapper;
import org.pruebatecnica.parqueadero.dtos.RolCompletoDto;
import org.pruebatecnica.parqueadero.entities.Rol;

@Mapper(componentModel = "spring")
public interface RolCompletoMapper {
    Rol toEntity(RolCompletoDto rolCompletoDto);

    RolCompletoDto toDto(Rol rol);
}
