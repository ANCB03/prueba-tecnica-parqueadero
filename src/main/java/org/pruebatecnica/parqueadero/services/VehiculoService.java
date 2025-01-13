package org.pruebatecnica.parqueadero.services;

import org.pruebatecnica.parqueadero.dtos.VehiculoCompletoDto;
import org.pruebatecnica.parqueadero.dtos.VehiculoDto;

import java.util.List;

public interface VehiculoService {
    public List<VehiculoDto> listarVehiculos();

    public void guardar(VehiculoDto vehiculoDto);

    public void eliminar(String placa);

    public VehiculoCompletoDto encontrarVehiculoByPlaca(String placa);

    public List<VehiculoDto> encontrarVehiculosByParqueadero(int id);

    public VehiculoDto editarVehiculo(String placa, VehiculoDto vehiculoDto);
}
