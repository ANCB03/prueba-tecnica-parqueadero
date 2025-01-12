package org.pruebatecnica.parqueadero.implement;

import lombok.RequiredArgsConstructor;
import org.pruebatecnica.parqueadero.dtos.HistorialDto;
import org.pruebatecnica.parqueadero.entities.Historial;
import org.pruebatecnica.parqueadero.entities.Parqueadero;
import org.pruebatecnica.parqueadero.entities.Vehiculo;
import org.pruebatecnica.parqueadero.exceptions.NotFoundException;
import org.pruebatecnica.parqueadero.mappers.HistorialMapper;
import org.pruebatecnica.parqueadero.repositories.HistorialRepository;
import org.pruebatecnica.parqueadero.repositories.ParqueaderoRepository;
import org.pruebatecnica.parqueadero.repositories.VehiculoRepository;
import org.pruebatecnica.parqueadero.services.HistorialService;
import org.pruebatecnica.parqueadero.util.MessageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class HistorialImplement implements HistorialService {

    private final HistorialRepository repository;

    private final VehiculoRepository vehiculoRepository;

    private final ParqueaderoRepository parqueaderoRepository;

    private final HistorialMapper historialMapper;

    private final MessageUtil messageUtil;

    @Override
    public List<HistorialDto> listarHistoriales() {
        return historialMapper.toHistorialList(repository.findAll());
    }

    @Override
    public void guardar(HistorialDto historialDto) {
        repository.save(historialMapper.toEntity(historialDto));
    }

    @Override
    public void eliminar(int id) {
        Historial historial = repository.findById(id).orElseThrow(
                () -> new NotFoundException(messageUtil.getMessage("HistorialNotFound", null, Locale.getDefault()))
        );
        repository.deleteById(id);
    }
    @Transactional
    @Override
    public HistorialDto encontrarHistorialById(int id) {
        return historialMapper.toDto(repository.findById(id).orElseThrow(
                () -> new NotFoundException(messageUtil.getMessage("HistorialNotFound", null, Locale.getDefault()))
        ));
    }
    @Transactional
    @Override
    public HistorialDto editarHistorial(int id, HistorialDto historialDto) {
        Historial historial = repository.findById(id).orElseThrow(
                () -> new NotFoundException(messageUtil.getMessage("HistorialNotFound",null, Locale.getDefault()))
        );

        if (historialDto.getVehiculo() != null){
            Vehiculo vehiculo = vehiculoRepository.findById(historial.getVehiculo().getPlaca()).orElseThrow(
                    () -> new NotFoundException(messageUtil.getMessage("VehiculoNotFound", null, Locale.getDefault()))
            );
            historial.setVehiculo(vehiculo);
        }

        if (historialDto.getParqueadero() != null){
            Parqueadero parqueadero = parqueaderoRepository.findById(historial.getParqueadero().getIdParqueadero()).orElseThrow(
                    () -> new NotFoundException(messageUtil.getMessage("ParqueaderoNotFound", null, Locale.getDefault()))
            );
            historial.setParqueadero(parqueadero);
        }

        if(historialDto.getFechaEntrada() != null)
            historial.setFechaEntrada(historialDto.getFechaEntrada());

        if(historialDto.getFechaSalida() != null)
            historial.setFechaSalida(historialDto.getFechaSalida());

        if(historialDto.getMonto() != null)
            historial.setMonto(historialDto.getMonto());

        repository.save(historial);
        return historialMapper.toDto(historial);
    }
}
