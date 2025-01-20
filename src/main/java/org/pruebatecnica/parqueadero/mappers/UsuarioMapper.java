package org.pruebatecnica.parqueadero.mappers;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.pruebatecnica.parqueadero.dtos.UsuarioDto;
import org.pruebatecnica.parqueadero.dtos.UsuarioSinPasswordDto;
import org.pruebatecnica.parqueadero.entities.Usuario;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    @Mapping(source = "id_rol", target = "rol.idRol")
    Usuario toEntity(UsuarioDto usuarioDto);
    @Mapping(source = "rol.idRol", target = "id_rol")
    UsuarioDto toDto(Usuario usuario);

    @Mapping(source = "rol.idRol", target = "id_rol")
    UsuarioSinPasswordDto toUsuarioSinPasswordDto(Usuario usuario);

    List<UsuarioSinPasswordDto> toUsuariolist(List<Usuario> entityList);
}
