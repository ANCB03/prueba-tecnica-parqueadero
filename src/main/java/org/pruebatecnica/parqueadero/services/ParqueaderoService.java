package org.pruebatecnica.parqueadero.services;

import org.pruebatecnica.parqueadero.dtos.ParqueaderoCompletoDto;
import org.pruebatecnica.parqueadero.dtos.ParqueaderoDto;

import java.util.List;

public interface ParqueaderoService {
    public List<ParqueaderoDto> listarParqueaderos();

    public void guardar(ParqueaderoDto parqueaderoDto);

    public void eliminar(int id);

    public ParqueaderoCompletoDto encontrarParqueaderoById(int id);

    public ParqueaderoDto editarParqueadero(int id, ParqueaderoDto parqueaderoDto);
}
