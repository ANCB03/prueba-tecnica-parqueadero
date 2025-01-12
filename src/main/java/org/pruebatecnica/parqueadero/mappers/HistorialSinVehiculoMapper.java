package org.pruebatecnica.parqueadero.mappers;

import org.mapstruct.Mapper;
import org.pruebatecnica.parqueadero.dtos.HistorialSinVehiculoDto;
import org.pruebatecnica.parqueadero.entities.Historial;

@Mapper(componentModel = "spring")
public interface HistorialSinVehiculoMapper {
    Historial toEntity(HistorialSinVehiculoDto historialSinVehiculoDto);

    HistorialSinVehiculoDto toDto(Historial historial);
}
