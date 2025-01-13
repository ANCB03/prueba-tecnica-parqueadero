package org.pruebatecnica.parqueadero.implement;

import org.pruebatecnica.parqueadero.dtos.CorreoAPIDto;
import org.pruebatecnica.parqueadero.dtos.CorreoDto;
import org.pruebatecnica.parqueadero.entities.Registro;
import org.pruebatecnica.parqueadero.exceptions.PlacaException;
import org.pruebatecnica.parqueadero.repositories.RegistroRepository;
import org.pruebatecnica.parqueadero.services.CorreoService;
import org.pruebatecnica.parqueadero.util.MessageUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Locale;
import java.util.Optional;

@Service
public class CorreoImplement implements CorreoService {
    private final WebClient webClient;

    private final RegistroRepository registroRepository;

    private final MessageUtil messageUtil;

    public CorreoImplement(WebClient.Builder webClientBuilder, RegistroRepository registroRepository, MessageUtil messageUtil) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081").build();
        this.registroRepository = registroRepository;
        this.messageUtil = messageUtil;
    }

    public String enviarNotificacion(CorreoAPIDto request) {
        return webClient.post()
                .uri("/email/send")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .block();  // Bloquea hasta recibir respuesta (sincrónico)
    }

    @Override
    public CorreoAPIDto validarInformacion(CorreoDto request) {
        Optional<Registro> vehiculoConEntrada = registroRepository.findVehiculoConEntradaParqueadero(request.getPlaca(),request.getParqueaderoId());
        if (!vehiculoConEntrada.isEmpty()) {
            CorreoAPIDto correoAPIDto = new CorreoAPIDto();
            correoAPIDto.setEmail(request.getEmail());
            correoAPIDto.setPlaca(request.getPlaca());
            correoAPIDto.setMensaje(request.getMensaje());
            correoAPIDto.setParqueaderoNombre(vehiculoConEntrada.get().getParqueadero().getNombre());
            return correoAPIDto;
        }else{
            throw new PlacaException(messageUtil.getMessage("vehiculoWithoutParqueadero", null, Locale.getDefault()));
        }
    }
}
