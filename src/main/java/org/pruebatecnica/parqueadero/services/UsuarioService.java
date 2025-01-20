package org.pruebatecnica.parqueadero.services;

import org.pruebatecnica.parqueadero.dtos.Top3UsuariosDto;
import org.pruebatecnica.parqueadero.dtos.UsuarioCompletoDto;
import org.pruebatecnica.parqueadero.dtos.UsuarioDto;
import org.pruebatecnica.parqueadero.dtos.UsuarioSinPasswordDto;

import java.util.List;

public interface UsuarioService {
    public List<UsuarioSinPasswordDto> listarUsuarios();

    public void guardar(UsuarioDto usuarioDto);

    public void eliminar(int id);

    public UsuarioCompletoDto encontrarUsuarioById(int id);

    public List<Top3UsuariosDto> top3Usuarios();

    public UsuarioDto editarUsuario(UsuarioDto usuarioDto);
}
