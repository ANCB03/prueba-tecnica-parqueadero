package org.pruebatecnica.parqueadero.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pruebatecnica.parqueadero.services.BlackListTokenService;
import org.pruebatecnica.parqueadero.util.TokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final BlackListTokenService service;

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(@RequestHeader("Authorization") String bearerToken) {
        try {
            // Extraer el token del header Bearer
            String token = bearerToken.substring(7); // Remueve "Bearer "
            log.info(token);
            // Agregar el token a la lista negra
            service.blacklistToken(token);
            log.info(TokenProvider.getUserName(token));
            Map<String, String> response = new HashMap<>();
            response.put("message", "Logout exitoso");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Error durante el logout");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
