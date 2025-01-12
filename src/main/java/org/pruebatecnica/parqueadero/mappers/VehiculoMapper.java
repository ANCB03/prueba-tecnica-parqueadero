package org.pruebatecnica.parqueadero.mappers;

import org.mapstruct.*;
import org.pruebatecnica.parqueadero.dtos.VehiculoDto;
import org.pruebatecnica.parqueadero.entities.Vehiculo;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehiculoMapper {
    Vehiculo toEntity(VehiculoDto vehiculoDto);

    VehiculoDto toDto(Vehiculo vehiculo);

    List<VehiculoDto> toVehiculolist(List<Vehiculo> entityList);

    @Mapping(target = "placa", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(VehiculoDto vehiculoDto, @MappingTarget Vehiculo vehiculo);
}
