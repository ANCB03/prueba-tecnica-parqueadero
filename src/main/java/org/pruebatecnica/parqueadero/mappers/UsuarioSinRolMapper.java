package org.pruebatecnica.parqueadero.mappers;

import org.mapstruct.Mapper;
import org.pruebatecnica.parqueadero.dtos.UsuarioSinRolDto;
import org.pruebatecnica.parqueadero.entities.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioSinRolMapper {
    Usuario toEntity(UsuarioSinRolDto usuarionSinRolDto);

    UsuarioSinRolDto toDto(Usuario usuario);
}
