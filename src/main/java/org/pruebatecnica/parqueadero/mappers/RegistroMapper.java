package org.pruebatecnica.parqueadero.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.pruebatecnica.parqueadero.dtos.HistorialDto;
import org.pruebatecnica.parqueadero.dtos.RegistroDto;
import org.pruebatecnica.parqueadero.entities.Historial;
import org.pruebatecnica.parqueadero.entities.Registro;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RegistroMapper {
    @Mapping(source = "id_vehiculo", target = "vehiculo.placa")
    @Mapping(source = "id_parqueadero", target = "parqueadero.idParqueadero")
    Registro toEntity(RegistroDto registroDto);
    @Mapping(source = "vehiculo.placa", target = "id_vehiculo")
    @Mapping(source = "parqueadero.idParqueadero", target = "id_parqueadero")
    RegistroDto toDto(Registro registro);

    List<RegistroDto> toRegistroList(List<Registro> entityList);
}
