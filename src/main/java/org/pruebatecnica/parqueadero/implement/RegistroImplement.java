package org.pruebatecnica.parqueadero.implement;

import lombok.RequiredArgsConstructor;
import org.pruebatecnica.parqueadero.dtos.RegistroDto;
import org.pruebatecnica.parqueadero.dtos.RequestEntradaSalida;
import org.pruebatecnica.parqueadero.entities.Parqueadero;
import org.pruebatecnica.parqueadero.entities.Registro;
import org.pruebatecnica.parqueadero.entities.Usuario;
import org.pruebatecnica.parqueadero.entities.Vehiculo;
import org.pruebatecnica.parqueadero.exceptions.NotFoundException;
import org.pruebatecnica.parqueadero.exceptions.PlacaException;
import org.pruebatecnica.parqueadero.exceptions.WithReferencesException;
import org.pruebatecnica.parqueadero.mappers.RegistroMapper;
import org.pruebatecnica.parqueadero.repositories.ParqueaderoRepository;
import org.pruebatecnica.parqueadero.repositories.RegistroRepository;
import org.pruebatecnica.parqueadero.repositories.UsuarioRepository;
import org.pruebatecnica.parqueadero.repositories.VehiculoRepository;
import org.pruebatecnica.parqueadero.services.RegistroService;
import org.pruebatecnica.parqueadero.util.MessageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistroImplement implements RegistroService {

    private final RegistroRepository repository;

    private final UsuarioRepository usuarioRepository;

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
    public RegistroDto guardarEntrada(RequestEntradaSalida requestEntrada, String correo) {
        Optional<Registro> vehiculoConEntrada = repository.findVehiculoConEntrada(requestEntrada.getPlaca());

        if (!vehiculoConEntrada.isEmpty()) {
            throw new WithReferencesException(messageUtil.getMessage("vehiculoWithEntrada", null, Locale.getDefault()));
        }

        Vehiculo vehiculo = vehiculoRepository.findById(requestEntrada.getPlaca()).orElseThrow(
                () -> new NotFoundException(messageUtil.getMessage("VehiculoNotFound", null, Locale.getDefault()))
        );

        Parqueadero parqueadero = parqueaderoRepository.findById(requestEntrada.getIdParqueadero()).orElseThrow(
                () -> new NotFoundException(messageUtil.getMessage("ParqueaderoNotFound", null, Locale.getDefault()))
        );

        Usuario socio = usuarioRepository.findByEmail(correo).orElseThrow(
                () -> new NotFoundException(messageUtil.getMessage("UsuarioNotFound", null, Locale.getDefault()))
        );

        if(parqueadero.getSocio().getIdUsuario() == socio.getIdUsuario()){
            if(parqueadero.getCapacidadOcup() < parqueadero.getCapacidadMax()){
                Registro registro = new Registro();
                ZoneId colombiaZone = ZoneId.of("America/Bogota");
                ZonedDateTime fechaHoraColombia = ZonedDateTime.now(colombiaZone);
                LocalDateTime fechaHoraActual = fechaHoraColombia.toLocalDateTime();
                registro.setFechaRegistro(fechaHoraActual);
                registro.setTipoRegistro("ENTRADA");
                registro.setVehiculo(vehiculo);
                parqueadero.setCapacidadOcup(parqueadero.getCapacidadOcup()+1);
                registro.setParqueadero(parqueadero);
                parqueaderoRepository.save(parqueadero);
                return registroMapper.toDto(repository.save(registro));
            }else {
                throw new PlacaException(messageUtil.getMessage("parqueaderoMax", null, Locale.getDefault()));
            }
        }else{
            throw new PlacaException(messageUtil.getMessage("socioWithoutParqueadero", null, Locale.getDefault()));
        }

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
    public List<Object[]> encontrarTop10Vehiculos() {
        return repository.findTop10Vehiculos();
    }

    @Override
    public List<Object[]> encontrarTop10VehiculosParqueadero(int idParqueadero) {
        return repository.findTop10VehiculosParqueadero(idParqueadero);
    }

    @Override
    public List<Object[]> encontrarVehiculosPrimeraVezP(int idParqueadero) {
        return repository.findFirstTimeParqueadero(idParqueadero);
    }

    @Override
    public RegistroDto editarRegistro(RegistroDto registroDto) {
        Registro registro = repository.findById(registroDto.getIdRegistro()).orElseThrow(
                () -> new NotFoundException(messageUtil.getMessage("RegistroNotFound",null, Locale.getDefault()))
        );

        if (registroDto.getId_vehiculo() != null){
            Vehiculo vehiculo = vehiculoRepository.findById(registroDto.getId_vehiculo()).orElseThrow(
                    () -> new NotFoundException(messageUtil.getMessage("VehiculoNotFound", null, Locale.getDefault()))
            );
            registro.setVehiculo(vehiculo);
        }

        if (registroDto.getId_parqueadero() != 0){
            Parqueadero parqueadero = parqueaderoRepository.findById(registroDto.getId_parqueadero()).orElseThrow(
                    () -> new NotFoundException(messageUtil.getMessage("ParqueaderoNotFound", null, Locale.getDefault()))
            );
            registro.setParqueadero(parqueadero);
        }

        if(registroDto.getFechaRegistro() != null)
            registro.setFechaRegistro(registroDto.getFechaRegistro());

        if(registroDto.getTipoRegistro() != null){
            registro.setTipoRegistro(registroDto.getTipoRegistro());
        }


        repository.save(registro);
        return registroMapper.toDto(registro);
    }
}
