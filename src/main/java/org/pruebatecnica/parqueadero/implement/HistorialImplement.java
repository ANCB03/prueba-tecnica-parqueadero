package org.pruebatecnica.parqueadero.implement;

import lombok.RequiredArgsConstructor;
import org.pruebatecnica.parqueadero.dtos.HistorialDto;
import org.pruebatecnica.parqueadero.dtos.RequestEntradaSalida;
import org.pruebatecnica.parqueadero.entities.*;
import org.pruebatecnica.parqueadero.exceptions.NotFoundException;
import org.pruebatecnica.parqueadero.exceptions.PlacaException;
import org.pruebatecnica.parqueadero.exceptions.WithReferencesException;
import org.pruebatecnica.parqueadero.mappers.HistorialMapper;
import org.pruebatecnica.parqueadero.repositories.*;
import org.pruebatecnica.parqueadero.services.HistorialService;
import org.pruebatecnica.parqueadero.util.MessageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HistorialImplement implements HistorialService {

    private final HistorialRepository repository;

    private final RegistroRepository registroRepository;

    private final UsuarioRepository usuarioRepository;

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
    public void guardarSalida(RequestEntradaSalida requestSalida, String correo) {
        Optional<Registro> vehiculoConEntrada = registroRepository.findVehiculoConEntradaParqueadero(requestSalida.getPlaca(), requestSalida.getIdParqueadero());

        if (!vehiculoConEntrada.isEmpty()) {
            Vehiculo vehiculo = vehiculoRepository.findById(requestSalida.getPlaca()).orElseThrow(
                    () -> new NotFoundException(messageUtil.getMessage("VehiculoNotFound", null, Locale.getDefault()))
            );

            Parqueadero parqueadero = parqueaderoRepository.findById(requestSalida.getIdParqueadero()).orElseThrow(
                    () -> new NotFoundException(messageUtil.getMessage("ParqueaderoNotFound", null, Locale.getDefault()))
            );

            Usuario socio = usuarioRepository.findByEmail(correo).orElseThrow(
                    () -> new NotFoundException(messageUtil.getMessage("UsuarioNotFound", null, Locale.getDefault()))
            );

            if(parqueadero.getSocio().getIdUsuario() != socio.getIdUsuario()){
                throw new PlacaException(messageUtil.getMessage("socioWithoutParqueadero", null, Locale.getDefault()));
            }

            Historial historial = new Historial();
            historial.setVehiculo(vehiculo);
            historial.setParqueadero(parqueadero);
            Registro registro = vehiculoConEntrada.get();
            registro.setTipoRegistro("SALIDA");
            historial.setFechaEntrada(registro.getFechaRegistro());
            ZoneId colombiaZone = ZoneId.of("America/Bogota");
            ZonedDateTime fechaHoraColombia = ZonedDateTime.now(colombiaZone);
            LocalDateTime fechaHoraActual = fechaHoraColombia.toLocalDateTime();
            historial.setFechaSalida(fechaHoraActual);
            Duration duration = Duration.between(vehiculoConEntrada.get().getFechaRegistro(), fechaHoraActual);
            BigDecimal horasDecimales = BigDecimal.valueOf(duration.toMinutes() / 60.0);
            BigDecimal montoTotal = parqueadero.getCostoHora().multiply(horasDecimales).setScale(2, RoundingMode.HALF_UP);
            historial.setMonto(montoTotal);
            parqueadero.setCapacidadOcup(parqueadero.getCapacidadOcup()-1);
            parqueaderoRepository.save(parqueadero);
            registroRepository.save(registro);
            repository.save(historial);
        }else{
            throw new PlacaException(messageUtil.getMessage("vehiculoWithoutEntrada", null, Locale.getDefault()));
        }



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

    @Override
    public List<BigDecimal> gananciasPorPeridos(int idParqueadero) {
        List<BigDecimal> ganancias = new ArrayList<>();
        ganancias.add(repository.gananciasHoy(idParqueadero));
        ganancias.add(repository.gananciasSemana(idParqueadero));
        ganancias.add(repository.gananciasMes(idParqueadero));
        ganancias.add(repository.gananciasAnio(idParqueadero));
        return ganancias;
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
