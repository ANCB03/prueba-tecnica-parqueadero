package org.pruebatecnica.parqueadero.controllers;

import lombok.RequiredArgsConstructor;
import org.pruebatecnica.parqueadero.dtos.CorreoAPIDto;
import org.pruebatecnica.parqueadero.dtos.CorreoDto;
import org.pruebatecnica.parqueadero.dtos.ParqueaderoDto;
import org.pruebatecnica.parqueadero.services.CorreoService;
import org.pruebatecnica.parqueadero.services.ParqueaderoService;
import org.pruebatecnica.parqueadero.util.TokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/parqueadero")
@CrossOrigin
@RequiredArgsConstructor
public class ParqueaderoController {
    private final ParqueaderoService service;

    private final CorreoService correoService;

    private Map<String,Object> response = new HashMap<>();
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<?> ParqueaderoList(){
        return new ResponseEntity<>(service.listarParqueaderos(), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/top3")
    public ResponseEntity<?> Top3ParqueaderosSemana(){
        return new ResponseEntity<>(service.top3Parqueaderos(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SOCIO')")
    @GetMapping("/socio")
    public ResponseEntity<?> ParqueaderoSocio(@RequestHeader("Authorization") String bearerToken){
        String token = bearerToken.substring(7); // Remueve "Bearer "
        return new ResponseEntity<>(service.parqueaderosSocioToken(TokenProvider.getUserName(token)), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping({"/{id}"})
    public ResponseEntity<?> findParqueadero(@PathVariable int id) {
        return new ResponseEntity<>(service.encontrarParqueaderoById(id), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_SOCIO')")
    @GetMapping({"/socio/{id}"})
    public ResponseEntity<?> findParqueaderoSocio(@PathVariable int id) {
        return new ResponseEntity<>(service.parqueaderosSocio(id), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<?> saveParqueadero(@Valid @RequestBody ParqueaderoDto parqueaderoDto) {
        response.clear();
        service.guardar(parqueaderoDto);
        response.put("message","Parqueadero guardado");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteParqueadero(@PathVariable int id) {

        response.clear();
        service.eliminar(id);
        response.put("message","Parqueadero eliminado");
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editParqueadero(@PathVariable int id,@Valid @RequestBody ParqueaderoDto parqueaderoDto) {
        response.clear();
        ParqueaderoDto parqueaderoDto1 = service.editarParqueadero(id,parqueaderoDto);
        response.put("message", "Parqueadero editado");
        response.put("parqueadero", parqueaderoDto1);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/notificar")
    public String notificar(@RequestBody CorreoDto request) {
        CorreoAPIDto correoAPIDto = correoService.validarInformacion(request);
        return correoService.enviarNotificacion(correoAPIDto);
    }
}
