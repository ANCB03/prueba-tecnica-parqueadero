package org.pruebatecnica.parqueadero.services;

import org.pruebatecnica.parqueadero.dtos.HistorialDto;

import java.util.List;

public interface HistorialService {
    public List<HistorialDto> listarHistoriales();

    public void guardar(HistorialDto historialDto);

    public void eliminar(int id);

    public HistorialDto encontrarHistorialById(int id);

    public HistorialDto editarHistorial(int id, HistorialDto historialDto);
}
