package org.pruebatecnica.parqueadero.mappers;

import org.mapstruct.Mapper;
import org.pruebatecnica.parqueadero.dtos.VehiculoCompletoDto;
import org.pruebatecnica.parqueadero.entities.Vehiculo;

@Mapper(componentModel = "spring")
public interface VehiculoCompletoMapper {
    Vehiculo toEntity(VehiculoCompletoDto vehiculoCompletoDto);

    VehiculoCompletoDto toDto(Vehiculo vehiculo);
}
