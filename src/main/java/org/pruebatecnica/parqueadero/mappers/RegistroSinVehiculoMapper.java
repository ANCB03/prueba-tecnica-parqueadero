package org.pruebatecnica.parqueadero.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.pruebatecnica.parqueadero.dtos.RegistroSinVehiculoDto;
import org.pruebatecnica.parqueadero.entities.Registro;

@Mapper(componentModel = "spring")
public interface RegistroSinVehiculoMapper {
    @Mapping(source = "id_parqueadero", target = "parqueadero.idParqueadero")
    Registro toEntity(RegistroSinVehiculoDto registroSinVehiculoDto);
    @Mapping(source = "parqueadero.idParqueadero", target = "id_parqueadero")
    RegistroSinVehiculoDto toDto(Registro registro);
}
