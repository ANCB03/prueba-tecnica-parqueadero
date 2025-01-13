package org.pruebatecnica.parqueadero.services;

import org.pruebatecnica.parqueadero.dtos.ParqueaderoCompletoDto;
import org.pruebatecnica.parqueadero.dtos.ParqueaderoDto;
import org.pruebatecnica.parqueadero.dtos.Top3ParqueaderosDto;

import java.util.List;

public interface ParqueaderoService {
    public List<ParqueaderoDto> listarParqueaderos();

    public void guardar(ParqueaderoDto parqueaderoDto);

    public void eliminar(int id);

    public ParqueaderoCompletoDto encontrarParqueaderoById(int id);

    public ParqueaderoDto editarParqueadero(int id, ParqueaderoDto parqueaderoDto);

    public List<Top3ParqueaderosDto> top3Parqueaderos();

    public List<ParqueaderoDto> parqueaderosSocio(int idUsuario);

    public List<ParqueaderoDto> parqueaderosSocioToken(String correo);
}
