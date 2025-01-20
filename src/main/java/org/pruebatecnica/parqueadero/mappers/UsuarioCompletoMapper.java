package org.pruebatecnica.parqueadero.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.pruebatecnica.parqueadero.dtos.UsuarioCompletoDto;
import org.pruebatecnica.parqueadero.entities.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioCompletoMapper {

    @Mapping(source = "id_rol", target = "rol.idRol")
    Usuario toEntity(UsuarioCompletoDto usuarioCompletoDto);
    @Mapping(source = "rol.idRol", target = "id_rol")
    UsuarioCompletoDto toDto(Usuario usuario);
}
