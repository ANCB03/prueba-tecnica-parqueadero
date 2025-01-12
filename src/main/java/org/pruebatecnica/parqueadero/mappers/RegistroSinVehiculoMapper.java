package org.pruebatecnica.parqueadero.mappers;

import org.mapstruct.Mapper;
import org.pruebatecnica.parqueadero.dtos.RegistroSinVehiculoDto;
import org.pruebatecnica.parqueadero.entities.Registro;

@Mapper(componentModel = "spring")
public interface RegistroSinVehiculoMapper {
    Registro toEntity(RegistroSinVehiculoDto registroSinVehiculoDto);

    RegistroSinVehiculoDto toDto(Registro registro);
}
