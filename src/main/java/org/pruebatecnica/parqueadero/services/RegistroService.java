package org.pruebatecnica.parqueadero.services;

import org.pruebatecnica.parqueadero.dtos.RegistroDto;

import java.util.List;

public interface RegistroService {
    public List<RegistroDto> listarRegistros();

    public RegistroDto guardar(RegistroDto registroDto);

    public void eliminar(int id);

    public RegistroDto encontrarRegistroById(int id);

    public RegistroDto editarRegistro(int id, RegistroDto registroDto);
}
