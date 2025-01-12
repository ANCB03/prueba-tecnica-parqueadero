package org.pruebatecnica.parqueadero.mappers;

import org.mapstruct.Mapper;
import org.pruebatecnica.parqueadero.dtos.HistorialDto;
import org.pruebatecnica.parqueadero.entities.Historial;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HistorialMapper {
    Historial toEntity(HistorialDto historialDto);

    HistorialDto toDto(Historial historial);
    List<HistorialDto> toHistorialList(List<Historial> entityList);
}
