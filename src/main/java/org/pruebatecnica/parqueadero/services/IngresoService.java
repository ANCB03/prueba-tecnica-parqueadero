package org.pruebatecnica.parqueadero.services;

import org.pruebatecnica.parqueadero.dtos.IngresoDto;

import java.util.List;

public interface IngresoService {
    public List<IngresoDto> listarIngresos();

    public void guardar(IngresoDto ingresoDto);

    public void eliminar(int id);

    public IngresoDto encontrarIngresoById(int id);

    public IngresoDto editarIngreso(int id, IngresoDto ingresoDto);
}
