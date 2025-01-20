package org.pruebatecnica.parqueadero.services;

import org.pruebatecnica.parqueadero.dtos.RegistroDto;
import org.pruebatecnica.parqueadero.dtos.RequestEntradaSalida;

import java.util.List;

public interface RegistroService {
    public List<RegistroDto> listarRegistros();

    public RegistroDto guardar(RegistroDto registroDto);

    public RegistroDto guardarEntrada(RequestEntradaSalida requestEntrada, String correo);

    public void eliminar(int id);

    public RegistroDto encontrarRegistroById(int id);

    public List<Object[]> encontrarTop10Vehiculos();

    public List<Object[]> encontrarTop10VehiculosParqueadero(int idParqueadero);

    public List<Object[]> encontrarVehiculosPrimeraVezP(int idParqueadero);

    public RegistroDto editarRegistro(RegistroDto registroDto);
}
