package org.pruebatecnica.parqueadero.mappers;

import org.mapstruct.Mapper;
import org.pruebatecnica.parqueadero.dtos.HistorialSinParqueaderoDto;
import org.pruebatecnica.parqueadero.entities.Historial;

@Mapper(componentModel = "spring")
public interface HistorialSinParqueaderoMapper {
    Historial toEntity(HistorialSinParqueaderoDto historialSinParqueaderoDto);

    HistorialSinParqueaderoDto toDto(Historial historial);
}
