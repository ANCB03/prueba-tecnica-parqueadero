package org.pruebatecnica.parqueadero.controllers;

import lombok.RequiredArgsConstructor;
import org.pruebatecnica.parqueadero.dtos.CorreoAPIDto;
import org.pruebatecnica.parqueadero.dtos.CorreoDto;
import org.pruebatecnica.parqueadero.dtos.ParqueaderoDto;
import org.pruebatecnica.parqueadero.services.CorreoService;
import org.pruebatecnica.parqueadero.services.ParqueaderoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/all")
    public ResponseEntity<?> ParqueaderoList(){
        return new ResponseEntity<>(service.listarParqueaderos(), HttpStatus.OK);
    }

    @GetMapping("/top3")
    public ResponseEntity<?> Top3ParqueaderosSemana(){
        return new ResponseEntity<>(service.top3Parqueaderos(), HttpStatus.OK);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<?> findParqueadero(@PathVariable int id) {
        return new ResponseEntity<>(service.encontrarParqueaderoById(id), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveParqueadero(@Valid @RequestBody ParqueaderoDto parqueaderoDto) {
        response.clear();
        service.guardar(parqueaderoDto);
        response.put("message","Parqueadero guardado");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteParqueadero(@PathVariable int id) {

        response.clear();
        service.eliminar(id);
        response.put("message","Parqueadero eliminado");
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editParqueadero(@PathVariable int id,@Valid @RequestBody ParqueaderoDto parqueaderoDto) {
        response.clear();
        ParqueaderoDto parqueaderoDto1 = service.editarParqueadero(id,parqueaderoDto);
        response.put("message", "Parqueadero editado");
        response.put("parqueadero", parqueaderoDto1);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping("/notificar")
    public String notificar(@RequestBody CorreoDto request) {
        CorreoAPIDto correoAPIDto = correoService.validarInformacion(request);
        return correoService.enviarNotificacion(correoAPIDto);
    }
}
