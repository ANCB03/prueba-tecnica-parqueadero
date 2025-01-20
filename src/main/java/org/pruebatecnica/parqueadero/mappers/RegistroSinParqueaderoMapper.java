package org.pruebatecnica.parqueadero.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.pruebatecnica.parqueadero.dtos.RegistroSinParqueaderoDto;
import org.pruebatecnica.parqueadero.entities.Registro;

@Mapper(componentModel = "spring")
public interface RegistroSinParqueaderoMapper {
    @Mapping(source = "id_vehiculo", target = "vehiculo.placa")
    Registro toEntity(RegistroSinParqueaderoDto registroSinParqueaderoDto);
    @Mapping(source = "vehiculo.placa", target = "id_vehiculo")
    RegistroSinParqueaderoDto toDto(Registro registro);
}
