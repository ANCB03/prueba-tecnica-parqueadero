package org.pruebatecnica.parqueadero.implement;

import lombok.RequiredArgsConstructor;
import org.pruebatecnica.parqueadero.dtos.Top3UsuariosDto;
import org.pruebatecnica.parqueadero.dtos.UsuarioCompletoDto;
import org.pruebatecnica.parqueadero.dtos.UsuarioDto;
import org.pruebatecnica.parqueadero.dtos.UsuarioSinPasswordDto;
import org.pruebatecnica.parqueadero.entities.*;
import org.pruebatecnica.parqueadero.exceptions.NotFoundException;
import org.pruebatecnica.parqueadero.exceptions.WithReferencesException;
import org.pruebatecnica.parqueadero.mappers.UserDetailsMapper;
import org.pruebatecnica.parqueadero.mappers.UsuarioCompletoMapper;
import org.pruebatecnica.parqueadero.mappers.UsuarioMapper;
import org.pruebatecnica.parqueadero.repositories.RolRepository;
import org.pruebatecnica.parqueadero.repositories.UsuarioRepository;
import org.pruebatecnica.parqueadero.services.UsuarioService;
import org.pruebatecnica.parqueadero.util.MessageUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioImplement implements UsuarioService, UserDetailsService {

    private final UsuarioRepository repository;

    private final RolRepository rolRepository;

    private final UsuarioMapper usuarioMapper;

    private final UsuarioCompletoMapper usuarioCompletoMapper;

    private final MessageUtil messageUtil;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<UsuarioSinPasswordDto> listarUsuarios() {
        return usuarioMapper.toUsuariolist(repository.findAll());
    }

    @Override
    public void guardar(UsuarioDto usuarioDto) {
        Optional<Usuario> usuarioExistente = repository.findByDocumento(usuarioDto.getDocumento());
        rolRepository.findById(usuarioDto.getId_rol()).orElseThrow(
                () -> new NotFoundException(messageUtil.getMessage("RolNotFound",null, Locale.getDefault()))
        );
        if (!usuarioExistente.isEmpty()) {
            throw new WithReferencesException(messageUtil.getMessage("UsuarioWithDocument", null, Locale.getDefault()));
        }
        Optional<Usuario> usuarioExistenteEmail = repository.findByEmail(usuarioDto.getEmail());
        if (!usuarioExistenteEmail.isEmpty()) {
            throw new WithReferencesException(messageUtil.getMessage("UsuarioWithEmail", null, Locale.getDefault()));
        }
        Usuario usuario = usuarioMapper.toEntity(usuarioDto);
        usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
        repository.save(usuario);
    }
    @Transactional
    @Override
    public void eliminar(int id) {
        Usuario usuario = repository.findById(id).orElseThrow(
                () -> new NotFoundException(messageUtil.getMessage("UsuarioNotFound", null, Locale.getDefault()))
        );
        if (!usuario.getParqueaderos().isEmpty()) {
            throw new WithReferencesException(messageUtil.getMessage("UsuarioWithParqueaderos", null, Locale.getDefault()));
        }
        repository.deleteById(id);
    }
    @Transactional
    @Override
    public UsuarioCompletoDto encontrarUsuarioById(int id) {
        return usuarioCompletoMapper.toDto(repository.findById(id).orElseThrow(
                () -> new NotFoundException(messageUtil.getMessage("UsuarioNotFound", null, Locale.getDefault()))
        ));
    }

    @Override
    public List<Top3UsuariosDto> top3Usuarios() {
        List<Object[]> ob = repository.getTopSociosIngresosSemana();
        Top3UsuariosDto usuarioDto = new Top3UsuariosDto();
        List<Top3UsuariosDto> list = new ArrayList<>();
        for(Object[] ob1 : ob){
                usuarioDto.setNombre((String) ob1[0]);
                usuarioDto.setApellido((String) ob1[1]);
                usuarioDto.setEntradas((BigInteger) ob1[2]);
                list.add(usuarioDto);
        }
        return list;
    }

    @Transactional
    @Override
    public UsuarioDto editarUsuario(UsuarioDto usuarioDto) {
        Usuario usuario = repository.findById(usuarioDto.getIdUsuario()).orElseThrow(
                () -> new NotFoundException(messageUtil.getMessage("UsuarioNotFound",null, Locale.getDefault()))
        );

        if (usuarioDto.getId_rol() != 0){
            Rol rol = rolRepository.findById(usuarioDto.getId_rol()).orElseThrow(
                    () -> new NotFoundException(messageUtil.getMessage("RolNotFound", null, Locale.getDefault()))
            );
            usuario.setRol(rol);
        }

        if (!usuarioDto.getNombre().isEmpty())
            usuario.setNombre(usuarioDto.getNombre());


        if (!usuarioDto.getApellido().isEmpty())
            usuario.setApellido(usuarioDto.getApellido());


        if (!usuarioDto.getDocumento().isEmpty())
            usuario.setDocumento(usuarioDto.getDocumento());

        usuario.setEstado(usuarioDto.isEstado());

        if (usuarioDto.getEmail() != null)
            usuario.setEmail(usuarioDto.getEmail());

        repository.save(usuario);
        return usuarioMapper.toDto(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Usuario retrievedUser = repository.findByEmail(username).get();
        if (retrievedUser == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return UserDetailsMapper.build(retrievedUser);
    }
}
