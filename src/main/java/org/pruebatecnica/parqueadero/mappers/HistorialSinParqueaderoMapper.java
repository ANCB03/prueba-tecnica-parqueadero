package org.pruebatecnica.parqueadero.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.pruebatecnica.parqueadero.dtos.HistorialSinParqueaderoDto;
import org.pruebatecnica.parqueadero.entities.Historial;

@Mapper(componentModel = "spring")
public interface HistorialSinParqueaderoMapper {

    @Mapping(source = "id_vehiculo", target = "vehiculo.placa")
    Historial toEntity(HistorialSinParqueaderoDto historialSinParqueaderoDto);

    @Mapping(source = "vehiculo.placa", target = "id_vehiculo")
    HistorialSinParqueaderoDto toDto(Historial historial);
}
