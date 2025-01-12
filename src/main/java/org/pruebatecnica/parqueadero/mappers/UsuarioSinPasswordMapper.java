package org.pruebatecnica.parqueadero.mappers;

import org.mapstruct.Mapper;
import org.pruebatecnica.parqueadero.dtos.UsuarioSinPasswordDto;
import org.pruebatecnica.parqueadero.entities.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioSinPasswordMapper {
    Usuario toEntity(UsuarioSinPasswordDto usuarioSinPasswordDto);

    UsuarioSinPasswordDto toDto(Usuario usuario);
}
