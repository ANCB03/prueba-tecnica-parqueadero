package org.pruebatecnica.parqueadero.implement;

import lombok.RequiredArgsConstructor;
import org.pruebatecnica.parqueadero.dtos.VehiculoCompletoDto;
import org.pruebatecnica.parqueadero.dtos.VehiculoDto;
import org.pruebatecnica.parqueadero.entities.Registro;
import org.pruebatecnica.parqueadero.entities.Vehiculo;
import org.pruebatecnica.parqueadero.exceptions.WithReferencesException;
import org.pruebatecnica.parqueadero.exceptions.NotFoundException;
import org.pruebatecnica.parqueadero.exceptions.PlacaException;
import org.pruebatecnica.parqueadero.mappers.VehiculoCompletoMapper;
import org.pruebatecnica.parqueadero.mappers.VehiculoMapper;
import org.pruebatecnica.parqueadero.repositories.RegistroRepository;
import org.pruebatecnica.parqueadero.repositories.VehiculoRepository;
import org.pruebatecnica.parqueadero.services.VehiculoService;
import org.pruebatecnica.parqueadero.util.MessageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class VehiculoImplement implements VehiculoService {

    private final VehiculoRepository repository;

    private final RegistroRepository registroRepository;

    private final VehiculoMapper vehiculoMapper;

    private final VehiculoCompletoMapper vehiculoCompletoMapper;

    private final MessageUtil messageUtil;

    @Override
    public List<VehiculoDto> listarVehiculos() {
        return vehiculoMapper.toVehiculolist(repository.findAll());
    }

    @Override
    public void guardar(VehiculoDto vehiculoDto) {
        validarFormatoPlaca(vehiculoDto.getPlaca());
        repository.save(vehiculoMapper.toEntity(vehiculoDto));
    }
    @Transactional
    @Override
    public void eliminar(String placa) {
        validarFormatoPlaca(placa);
        Vehiculo vehiculo = repository.findById(placa).orElseThrow(
                () -> new NotFoundException(messageUtil.getMessage("VehiculoNotFound", null, Locale.getDefault()))
        );
        if (!vehiculo.getHistoriales().isEmpty()) {
            throw new WithReferencesException(messageUtil.getMessage("VehiculoWithHistorial", null, Locale.getDefault()));
        }
        repository.deleteById(placa);
    }
    @Transactional
    @Override
    public VehiculoCompletoDto encontrarVehiculoByPlaca(String placa) {
        validarFormatoPlaca(placa);
        return vehiculoCompletoMapper.toDto(repository.findById(placa).orElseThrow(
                () -> new NotFoundException(messageUtil.getMessage("VehiculoNotFound", null, Locale.getDefault()))
        ));
    }

    @Override
    public List<VehiculoDto> encontrarVehiculosByParqueadero(int id) {
        List<Registro> registros = registroRepository.findVehiculosConEntradaParqueadero(id);
        List<VehiculoDto> vehiculos = new ArrayList<>();
        for(Registro registro: registros){
            VehiculoDto vehiculoDto = vehiculoMapper.toDto(registro.getVehiculo());
            vehiculos.add(vehiculoDto);
        }
        return  vehiculos;
    }

    @Override
    public VehiculoDto editarVehiculo(String placa, VehiculoDto vehiculoDto) {
        Vehiculo vehiculo = repository.findById(placa).orElseThrow(
                () -> new NotFoundException(messageUtil.getMessage("VehiculoNotFound", null, Locale.getDefault()))
        );
        vehiculoMapper.updateEntity(vehiculoDto,vehiculo);
        repository.save(vehiculo);
        return vehiculoMapper.toDto(vehiculo);
    }

    private void validarFormatoPlaca(String placa) {
        if (!placa.matches("^[A-Za-z0-9]{6}$")) {
            throw new PlacaException(messageUtil.getMessage("InvalidPlacaFormat", null, Locale.getDefault()));
        }
    }
}
