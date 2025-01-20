package org.pruebatecnica.parqueadero.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.pruebatecnica.parqueadero.dtos.UsuarioSinPasswordDto;
import org.pruebatecnica.parqueadero.entities.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioSinPasswordMapper {

    @Mapping(source = "id_rol", target = "rol.idRol")
    Usuario toEntity(UsuarioSinPasswordDto usuarioSinPasswordDto);

    @Mapping(source = "rol.idRol", target = "id_rol")
    UsuarioSinPasswordDto toDto(Usuario usuario);
}
