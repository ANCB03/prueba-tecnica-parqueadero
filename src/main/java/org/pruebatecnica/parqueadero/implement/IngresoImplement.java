package org.pruebatecnica.parqueadero.implement;

import lombok.RequiredArgsConstructor;
import org.pruebatecnica.parqueadero.dtos.IngresoDto;
import org.pruebatecnica.parqueadero.entities.Ingreso;
import org.pruebatecnica.parqueadero.entities.Parqueadero;
import org.pruebatecnica.parqueadero.entities.Vehiculo;
import org.pruebatecnica.parqueadero.exceptions.NotFoundException;
import org.pruebatecnica.parqueadero.mappers.IngresoMapper;
import org.pruebatecnica.parqueadero.repositories.IngresoRepository;
import org.pruebatecnica.parqueadero.repositories.ParqueaderoRepository;
import org.pruebatecnica.parqueadero.repositories.VehiculoRepository;
import org.pruebatecnica.parqueadero.services.IngresoService;
import org.pruebatecnica.parqueadero.util.MessageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class IngresoImplement implements IngresoService {

    private final IngresoRepository repository;

    private final VehiculoRepository vehiculoRepository;

    private final ParqueaderoRepository parqueaderoRepository;

    private final IngresoMapper ingresoMapper;

    private final MessageUtil messageUtil;

    @Override
    public List<IngresoDto> listarIngresos() {
        return ingresoMapper.toIngresolist(repository.findAll());
    }

    @Override
    public void guardar(IngresoDto ingresoDto) {
        repository.save(ingresoMapper.toEntity(ingresoDto));
    }

    @Override
    public void eliminar(int id) {
        Ingreso ingreso = repository.findById(id).orElseThrow(
                () -> new NotFoundException(messageUtil.getMessage("IngresoNotFound", null, Locale.getDefault()))
        );
        repository.deleteById(id);
    }
    @Transactional
    @Override
    public IngresoDto encontrarIngresoById(int id) {
        return ingresoMapper.toDto(repository.findById(id).orElseThrow(
                () -> new NotFoundException(messageUtil.getMessage("IngresoNotFound", null, Locale.getDefault()))
        ));
    }
    @Transactional
    @Override
    public IngresoDto editarIngreso(int id, IngresoDto ingresoDto) {
        Ingreso ingreso = repository.findById(id).orElseThrow(
                () -> new NotFoundException(messageUtil.getMessage("IngresoNotFound",null, Locale.getDefault()))
        );

        if (ingresoDto.getVehiculo() != null){
            Vehiculo vehiculo = vehiculoRepository.findById(ingresoDto.getVehiculo().getPlaca()).orElseThrow(
                    () -> new NotFoundException(messageUtil.getMessage("VehiculoNotFound", null, Locale.getDefault()))
            );
            ingreso.setVehiculo(vehiculo);
        }

        if (ingresoDto.getParqueadero() != null){
            Parqueadero parqueadero = parqueaderoRepository.findById(ingresoDto.getParqueadero().getIdParqueadero()).orElseThrow(
                    () -> new NotFoundException(messageUtil.getMessage("ParqueaderoNotFound", null, Locale.getDefault()))
            );
            ingreso.setParqueadero(parqueadero);
        }

        if (ingresoDto.getMonto().compareTo(BigDecimal.ZERO) != 0)
            ingreso.setMonto(ingresoDto.getMonto());

        if(ingresoDto.getFechaPago() != null)
            ingreso.setFechaPago(ingresoDto.getFechaPago());

        repository.save(ingreso);
        return ingresoMapper.toDto(ingreso);
    }
}
