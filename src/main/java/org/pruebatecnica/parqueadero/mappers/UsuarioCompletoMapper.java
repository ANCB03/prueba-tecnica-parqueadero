package org.pruebatecnica.parqueadero.mappers;

import org.mapstruct.Mapper;
import org.pruebatecnica.parqueadero.dtos.UsuarioCompletoDto;
import org.pruebatecnica.parqueadero.entities.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioCompletoMapper {
    Usuario toEntity(UsuarioCompletoDto usuarioCompletoDto);

    UsuarioCompletoDto toDto(Usuario usuario);
}
