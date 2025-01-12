package org.pruebatecnica.parqueadero.mappers;

import org.mapstruct.Mapper;
import org.pruebatecnica.parqueadero.dtos.UsuarioDto;
import org.pruebatecnica.parqueadero.entities.Usuario;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    Usuario toEntity(UsuarioDto usuarioDto);

    UsuarioDto toDto(Usuario usuario);

    List<UsuarioDto> toUsuariolist(List<Usuario> entityList);
}
