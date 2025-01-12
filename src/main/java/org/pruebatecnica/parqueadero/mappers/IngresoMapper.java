package org.pruebatecnica.parqueadero.mappers;

import org.mapstruct.Mapper;
import org.pruebatecnica.parqueadero.dtos.IngresoDto;
import org.pruebatecnica.parqueadero.entities.Ingreso;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IngresoMapper {
    Ingreso toEntity(IngresoDto ingresoDto);

    IngresoDto toDto(Ingreso ingreso);

    List<IngresoDto> toIngresolist(List<Ingreso> entityList);
}
