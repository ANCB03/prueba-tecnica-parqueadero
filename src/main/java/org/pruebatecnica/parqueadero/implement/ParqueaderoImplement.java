package org.pruebatecnica.parqueadero.implement;

import lombok.RequiredArgsConstructor;
import org.pruebatecnica.parqueadero.dtos.ParqueaderoCompletoDto;
import org.pruebatecnica.parqueadero.dtos.ParqueaderoDto;
import org.pruebatecnica.parqueadero.dtos.Top3ParqueaderosDto;
import org.pruebatecnica.parqueadero.entities.Parqueadero;
import org.pruebatecnica.parqueadero.entities.Usuario;
import org.pruebatecnica.parqueadero.exceptions.NotFoundException;
import org.pruebatecnica.parqueadero.exceptions.PlacaException;
import org.pruebatecnica.parqueadero.mappers.ParqueaderoCompletoMapper;
import org.pruebatecnica.parqueadero.mappers.ParqueaderoMapper;
import org.pruebatecnica.parqueadero.repositories.ParqueaderoRepository;
import org.pruebatecnica.parqueadero.repositories.UsuarioRepository;
import org.pruebatecnica.parqueadero.services.ParqueaderoService;
import org.pruebatecnica.parqueadero.util.MessageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

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
        if(parqueaderoDto.getCapacidadMax() > parqueaderoDto.getCapacidadOcup()){
            usuarioRepository.findById(parqueaderoDto.getId_socio()).orElseThrow(
                    () -> new NotFoundException(messageUtil.getMessage("UsuarioNotFound",null, Locale.getDefault()))
            );
            repository.save(parqueaderoMapper.toEntity(parqueaderoDto));
        }else{
            throw new PlacaException(messageUtil.getMessage("CapacidadMax",null, Locale.getDefault()));
        }
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
    public ParqueaderoDto editarParqueadero(ParqueaderoDto parqueaderoDto) {
        Parqueadero parqueadero = repository.findById(parqueaderoDto.getIdParqueadero()).orElseThrow(
                () -> new NotFoundException(messageUtil.getMessage("ParqueaderoNotFound",null, Locale.getDefault()))
        );

        if (parqueaderoDto.getId_socio() != 0){
            Usuario socio = usuarioRepository.findById(parqueaderoDto.getId_socio()).orElseThrow(
                    () -> new NotFoundException(messageUtil.getMessage("UsuarioNotFound", null, Locale.getDefault()))
            );
            if(Objects.equals(socio.getRol().getNombre(), "SOCIO"))parqueadero.setSocio(socio);
        }

        if (!parqueaderoDto.getNombre().isEmpty())
            parqueadero.setNombre(parqueaderoDto.getNombre());

        if (parqueaderoDto.getCapacidadMax() >= 0){
            if(parqueaderoDto.getCapacidadMax() > parqueadero.getCapacidadOcup() && parqueaderoDto.getCapacidadMax() > parqueaderoDto.getCapacidadOcup()){
                parqueadero.setCapacidadMax(parqueaderoDto.getCapacidadMax());
            }
        }

        if(parqueaderoDto.getCapacidadOcup() >= 0){
            if(parqueaderoDto.getCapacidadOcup() <= parqueadero.getCapacidadMax() && parqueaderoDto.getCapacidadOcup() > parqueaderoDto.getCapacidadMax()){
                parqueadero.setCapacidadOcup(parqueaderoDto.getCapacidadOcup());
            }
        }

        if (parqueaderoDto.getCostoHora().compareTo(BigDecimal.ZERO) != 0)
            parqueadero.setCostoHora(parqueaderoDto.getCostoHora());

        parqueadero.setEstado(parqueaderoDto.isEstado());

        repository.save(parqueadero);
        return parqueaderoMapper.toDto(parqueadero);
    }

    @Override
    public List<Top3ParqueaderosDto> top3Parqueaderos() {
        List<Object[]> ob = repository.getTopParqueaderosGananciaSemana();
        Top3ParqueaderosDto parqueaderoDto = new Top3ParqueaderosDto();
        List<Top3ParqueaderosDto> list = new ArrayList<>();
        for(Object[] ob1 : ob){
            parqueaderoDto.setNombre((String) ob1[0]);
            parqueaderoDto.setGanancia((BigDecimal) ob1[1]);
            parqueaderoDto.setVehiculos((BigInteger) ob1[2]);
            list.add(parqueaderoDto);
        }
        return list;
    }

    @Override
    public List<ParqueaderoDto> parqueaderosSocio(int idUsuario) {
        List<ParqueaderoDto> parqueaderos = parqueaderoMapper.toParqueaderolist(repository.findParqueaderosSocio(idUsuario));
        return parqueaderos;
    }

    @Override
    public List<ParqueaderoDto> parqueaderosSocioToken(String correo) {
        Usuario usuario = usuarioRepository.findByEmail(correo).orElseThrow(
                () -> new NotFoundException(messageUtil.getMessage("UsuarioNotFound",null, Locale.getDefault()))
        );
        return parqueaderoMapper.toParqueaderolist(repository.findParqueaderosSocio(usuario.getIdUsuario()));

    }
}
