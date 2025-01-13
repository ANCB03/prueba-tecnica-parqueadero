package org.pruebatecnica.parqueadero.services;

import org.pruebatecnica.parqueadero.dtos.CorreoAPIDto;
import org.pruebatecnica.parqueadero.dtos.CorreoDto;
import org.springframework.web.reactive.function.client.WebClient;

public interface CorreoService {
    public String enviarNotificacion(CorreoAPIDto request);

    public CorreoAPIDto validarInformacion(CorreoDto request);
}
