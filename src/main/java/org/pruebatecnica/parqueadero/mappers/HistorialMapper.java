package org.pruebatecnica.parqueadero.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.pruebatecnica.parqueadero.dtos.HistorialDto;
import org.pruebatecnica.parqueadero.entities.Historial;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HistorialMapper {

    @Mapping(source = "id_vehiculo", target = "vehiculo.placa")
    @Mapping(source = "id_parqueadero", target = "parqueadero.idParqueadero")
    Historial toEntity(HistorialDto historialDto);

    @Mapping(source = "vehiculo.placa", target = "id_vehiculo")
    @Mapping(source = "parqueadero.idParqueadero", target = "id_parqueadero")
    HistorialDto toDto(Historial historial);
    List<HistorialDto> toHistorialList(List<Historial> entityList);
}
