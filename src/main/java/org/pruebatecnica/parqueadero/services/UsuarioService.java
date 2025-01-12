package org.pruebatecnica.parqueadero.services;

import org.pruebatecnica.parqueadero.dtos.UsuarioCompletoDto;
import org.pruebatecnica.parqueadero.dtos.UsuarioDto;

import java.util.List;

public interface UsuarioService {
    public List<UsuarioDto> listarUsuarios();

    public void guardar(UsuarioDto usuarioDto);

    public void eliminar(int id);

    public UsuarioCompletoDto encontrarUsuarioById(int id);

    public UsuarioDto editarUsuario(int id, UsuarioDto usuarioDto);
}
