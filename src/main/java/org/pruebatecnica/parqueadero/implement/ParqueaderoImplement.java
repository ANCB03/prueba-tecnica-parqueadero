package org.pruebatecnica.parqueadero.implement;

import lombok.RequiredArgsConstructor;
import org.pruebatecnica.parqueadero.dtos.ParqueaderoCompletoDto;
import org.pruebatecnica.parqueadero.dtos.ParqueaderoDto;
import org.pruebatecnica.parqueadero.entities.Parqueadero;
import org.pruebatecnica.parqueadero.entities.Usuario;
import org.pruebatecnica.parqueadero.exceptions.NotFoundException;
import org.pruebatecnica.parqueadero.mappers.ParqueaderoCompletoMapper;
import org.pruebatecnica.parqueadero.mappers.ParqueaderoMapper;
import org.pruebatecnica.parqueadero.repositories.ParqueaderoRepository;
import org.pruebatecnica.parqueadero.repositories.UsuarioRepository;
import org.pruebatecnica.parqueadero.services.ParqueaderoService;
import org.pruebatecnica.parqueadero.util.MessageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ParqueaderoImplement implements ParqueaderoService {

    private final ParqueaderoRepository repository;

    private final UsuarioRepository usuarioRepository;

    private final ParqueaderoMapper parqueaderoMapper;

    private final ParqueaderoCompletoMapper parqueaderoCompletoMapper;

    private final MessageUtil messageUtil;

    @Override
    public List<ParqueaderoDto> listarParqueaderos() {
        return parqueaderoMapper.toParqueaderolist(repository.findAll());
    }

    @Override
    public void guardar(ParqueaderoDto parqueaderoDto) {
        repository.save(parqueaderoMapper.toEntity(parqueaderoDto));
    }

    @Override
    public void eliminar(int id) {
        Parqueadero parqueadero = repository.findById(id).orElseThrow(
                () -> new NotFoundException(messageUtil.getMessage("ParqueaderoNotFound", null, Locale.getDefault()))
        );
        repository.deleteById(id);
    }
    @Transactional
    @Override
    public ParqueaderoCompletoDto encontrarParqueaderoById(int id) {
        return parqueaderoCompletoMapper.toDto(repository.findById(id).orElseThrow(
                () -> new NotFoundException(messageUtil.getMessage("ParqueaderoNotFound", null, Locale.getDefault()))
        ));
    }
    @Transactional
    @Override
    public ParqueaderoDto editarParqueadero(int id, ParqueaderoDto parqueaderoDto) {
        Parqueadero parqueadero = repository.findById(id).orElseThrow(
                () -> new NotFoundException(messageUtil.getMessage("ParqueaderoNotFound",null, Locale.getDefault()))
        );

        if (parqueaderoDto.getSocio() != null){
            Usuario socio = usuarioRepository.findById(parqueaderoDto.getSocio().getIdUsuario()).orElseThrow(
                    () -> new NotFoundException(messageUtil.getMessage("UsuarioNotFound", null, Locale.getDefault()))
            );
            parqueadero.setSocio(socio);
        }

        if (parqueaderoDto.getNombre().isEmpty())
            parqueadero.setNombre(parqueaderoDto.getNombre());

        if (parqueaderoDto.getCapacidadMax() >= 0)
            parqueadero.setCapacidadMax(parqueaderoDto.getCapacidadMax());

        if(parqueaderoDto.getCapacidadOcup() >= 0)
            parqueadero.setCapacidadOcup(parqueaderoDto.getCapacidadOcup());

        if (parqueaderoDto.getCostoHora().compareTo(BigDecimal.ZERO) != 0)
            parqueadero.setCostoHora(parqueaderoDto.getCostoHora());

        parqueadero.setEstado(parqueaderoDto.isEstado());

        repository.save(parqueadero);
        return parqueaderoMapper.toDto(parqueadero);
    }
}
