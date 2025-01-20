package org.pruebatecnica.parqueadero.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.pruebatecnica.parqueadero.dtos.HistorialSinVehiculoDto;
import org.pruebatecnica.parqueadero.entities.Historial;

@Mapper(componentModel = "spring")
public interface HistorialSinVehiculoMapper {
    @Mapping(source = "id_parqueadero", target = "parqueadero.idParqueadero")
    Historial toEntity(HistorialSinVehiculoDto historialSinVehiculoDto);
    @Mapping(source = "parqueadero.idParqueadero", target = "id_parqueadero")
    HistorialSinVehiculoDto toDto(Historial historial);
}
