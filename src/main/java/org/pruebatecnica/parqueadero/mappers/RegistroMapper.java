package org.pruebatecnica.parqueadero.mappers;

import org.mapstruct.Mapper;
import org.pruebatecnica.parqueadero.dtos.HistorialDto;
import org.pruebatecnica.parqueadero.dtos.RegistroDto;
import org.pruebatecnica.parqueadero.entities.Historial;
import org.pruebatecnica.parqueadero.entities.Registro;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RegistroMapper {
    Registro toEntity(RegistroDto registroDto);

    RegistroDto toDto(Registro registro);

    List<RegistroDto> toRegistroList(List<Registro> entityList);
}
