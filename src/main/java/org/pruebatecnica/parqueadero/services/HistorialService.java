package org.pruebatecnica.parqueadero.services;

import org.pruebatecnica.parqueadero.dtos.HistorialDto;
import org.pruebatecnica.parqueadero.dtos.RequestEntradaSalida;

import java.math.BigDecimal;
import java.util.List;

public interface HistorialService {
    public List<HistorialDto> listarHistoriales();

    public void guardar(HistorialDto historialDto);

    public void guardarSalida(RequestEntradaSalida requestSalida, String correo);

    public void eliminar(int id);

    public HistorialDto encontrarHistorialById(int id);

    public List<BigDecimal> gananciasPorPeridos(int idParqueadero);

    public HistorialDto editarHistorial(HistorialDto historialDto);
}
