package org.pruebatecnica.parqueadero.implement;

import lombok.RequiredArgsConstructor;
import org.pruebatecnica.parqueadero.dtos.RegistroDto;
import org.pruebatecnica.parqueadero.entities.Parqueadero;
import org.pruebatecnica.parqueadero.entities.Registro;
import org.pruebatecnica.parqueadero.entities.Vehiculo;
import org.pruebatecnica.parqueadero.exceptions.NotFoundException;
import org.pruebatecnica.parqueadero.mappers.RegistroMapper;
import org.pruebatecnica.parqueadero.repositories.ParqueaderoRepository;
import org.pruebatecnica.parqueadero.repositories.RegistroRepository;
import org.pruebatecnica.parqueadero.repositories.VehiculoRepository;
import org.pruebatecnica.parqueadero.services.RegistroService;
import org.pruebatecnica.parqueadero.util.MessageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class RegistroImplement implements RegistroService {

    private final RegistroRepository repository;

    private final VehiculoRepository vehiculoRepository;

    private final ParqueaderoRepository parqueaderoRepository;

    private final RegistroMapper registroMapper;

    private final MessageUtil messageUtil;

    @Override
    public List<RegistroDto> listarRegistros() {
        return registroMapper.toRegistroList(repository.findAll());
    }

    @Override
    public RegistroDto guardar(RegistroDto registroDto) {
       return registroMapper.toDto(repository.save(registroMapper.toEntity(registroDto)));
    }

    @Override
    public void eliminar(int id) {
        Registro registro = repository.findById(id).orElseThrow(
                () -> new NotFoundException(messageUtil.getMessage("RegistroNotFound", null, Locale.getDefault()))
        );
        repository.deleteById(id);
    }
    @Transactional
    @Override
    public RegistroDto encontrarRegistroById(int id) {
        return registroMapper.toDto(repository.findById(id).orElseThrow(
                () -> new NotFoundException(messageUtil.getMessage("RegistroNotFound", null, Locale.getDefault()))
        ));
    }

    @Override
    public RegistroDto editarRegistro(int id, RegistroDto registroDto) {
        Registro registro = repository.findById(id).orElseThrow(
                () -> new NotFoundException(messageUtil.getMessage("RegistroNotFound",null, Locale.getDefault()))
        );

        if (registroDto.getVehiculo() != null){
            Vehiculo vehiculo = vehiculoRepository.findById(registroDto.getVehiculo().getPlaca()).orElseThrow(
                    () -> new NotFoundException(messageUtil.getMessage("VehiculoNotFound", null, Locale.getDefault()))
            );
            registro.setVehiculo(vehiculo);
        }

        if (registroDto.getParqueadero() != null){
            Parqueadero parqueadero = parqueaderoRepository.findById(registroDto.getParqueadero().getIdParqueadero()).orElseThrow(
                    () -> new NotFoundException(messageUtil.getMessage("ParqueaderoNotFound", null, Locale.getDefault()))
            );
            registro.setParqueadero(parqueadero);
        }

        if(registroDto.getTipoRegistro() != null){
            registro.setTipoRegistro(registroDto.getTipoRegistro());
        }


        repository.save(registro);
        return registroMapper.toDto(registro);
    }
}
