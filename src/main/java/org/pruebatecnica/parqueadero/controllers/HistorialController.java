package org.pruebatecnica.parqueadero.controllers;

import lombok.RequiredArgsConstructor;
import org.pruebatecnica.parqueadero.dtos.HistorialDto;
import org.pruebatecnica.parqueadero.dtos.RequestEntradaSalida;
import org.pruebatecnica.parqueadero.services.HistorialService;
import org.pruebatecnica.parqueadero.util.TokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/historial")
@CrossOrigin
@RequiredArgsConstructor
public class HistorialController {
    private  final HistorialService service;

    private Map<String,Object> response = new HashMap<>();
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/")
    public ResponseEntity<?> HistorialList(){
        return new ResponseEntity<>(service.listarHistoriales(), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping({"/{id}"})
    public ResponseEntity<?> findHistorial(@PathVariable int id) {
        return new ResponseEntity<>(service.encontrarHistorialById(id), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_SOCIO')")
    @GetMapping({"/ganancias/periodos/{id}"})
    public ResponseEntity<?> gananciasPeriodos(@PathVariable int id) {
        response.clear();
        List<BigDecimal> ganancias = service.gananciasPorPeridos(id);
        response.put("hoy",ganancias.get(0));
        response.put("semana",ganancias.get(1));
        response.put("mes",ganancias.get(2));
        response.put("anio",ganancias.get(3));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/")
    public ResponseEntity<?> saveHistorial(@Valid @RequestBody HistorialDto historialDto) {
        response.clear();
        service.guardar(historialDto);
        response.put("message","Historial guardado");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ROLE_SOCIO')")
    @PostMapping("/save-salida")
    public ResponseEntity<?> saveSalida(@Valid @RequestBody RequestEntradaSalida requestSalida, @RequestHeader("Authorization") String bearerToken) {
        response.clear();
        String token = bearerToken.substring(7);
        service.guardarSalida(requestSalida, TokenProvider.getUserName(token));
        response.put("mensaje","Salida registrada");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHistorial(@PathVariable int id) {

        response.clear();
        service.eliminar(id);
        response.put("message","Historial eliminado");
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/")
    public ResponseEntity<?> editHistorial(@Valid @RequestBody HistorialDto historialDto) {
        response.clear();
        HistorialDto historialDto1 = service.editarHistorial(historialDto);
        response.put("message", "Historial editado");
        response.put("historial", historialDto1);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
