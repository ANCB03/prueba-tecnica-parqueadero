package org.pruebatecnica.parqueadero.implement;

import lombok.RequiredArgsConstructor;
import org.pruebatecnica.parqueadero.dtos.RolCompletoDto;
import org.pruebatecnica.parqueadero.dtos.RolDto;
import org.pruebatecnica.parqueadero.entities.Rol;
import org.pruebatecnica.parqueadero.exceptions.WithReferencesException;
import org.pruebatecnica.parqueadero.exceptions.NotFoundException;
import org.pruebatecnica.parqueadero.mappers.RolCompletoMapper;
import org.pruebatecnica.parqueadero.mappers.RolMapper;
import org.pruebatecnica.parqueadero.repositories.RolRepository;
import org.pruebatecnica.parqueadero.services.RolService;
import org.pruebatecnica.parqueadero.util.MessageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class RolImplement implements RolService {

    private final RolRepository repository;

    private final RolMapper rolMapper;

    private final RolCompletoMapper rolCompletoMapper;

    private final MessageUtil messageUtil;
    @Override
    public List<RolDto> listarRoles() {
        return rolMapper.toRollist(repository.findAll());
    }

    @Override
    public void guardar(RolDto rolDto) {
        repository.save(rolMapper.toEntity(rolDto));
    }

    @Transactional
    @Override
    public void eliminar(int id) {
        Rol rol = repository.findById(id).orElseThrow(
                () -> new NotFoundException(messageUtil.getMessage("RolNotFound", null, Locale.getDefault()))
        );
        if (!rol.getUsuarios().isEmpty()) {
            throw new WithReferencesException(messageUtil.getMessage("RolWithUsuarios", null, Locale.getDefault()));
        }
        repository.deleteById(id);
    }
    @Transactional
    @Override
    public RolCompletoDto encontrarRolById(int id) {
        return rolCompletoMapper.toDto(repository.findById(id).orElseThrow(
                () -> new NotFoundException(messageUtil.getMessage("RolNotFound", null, Locale.getDefault()))
        ));
    }

    @Override
    public RolDto editarRol(RolDto rolDto) {
        Rol rol = repository.findById(rolDto.getIdRol()).orElseThrow(
                () -> new NotFoundException(messageUtil.getMessage("RolNotFound", null, Locale.getDefault()))
        );
        rolMapper.updateEntity(rolDto,rol);
        repository.save(rol);
        return rolMapper.toDto(rol);
    }
}
