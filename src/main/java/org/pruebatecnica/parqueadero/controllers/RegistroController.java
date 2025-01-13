package org.pruebatecnica.parqueadero.controllers;

import lombok.RequiredArgsConstructor;
import org.pruebatecnica.parqueadero.dtos.RegistroDto;
import org.pruebatecnica.parqueadero.dtos.RequestEntradaSalida;
import org.pruebatecnica.parqueadero.services.RegistroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/registro")
@CrossOrigin
@RequiredArgsConstructor
public class RegistroController {
    private final RegistroService service;

    private Map<String,Object> response = new HashMap<>();

    @GetMapping("/all")
    public ResponseEntity<?> RegistroList(){
        return new ResponseEntity<>(service.listarRegistros(), HttpStatus.OK);
    }

    @GetMapping("/top-10")
    public ResponseEntity<?> Top10Vehiculos(){
        return new ResponseEntity<>(service.encontrarTop10Vehiculos(), HttpStatus.OK);
    }

    @GetMapping("/top-10/{id}")
    public ResponseEntity<?> Top10Vehiculos(@PathVariable int id){
        return new ResponseEntity<>(service.encontrarTop10VehiculosParqueadero(id), HttpStatus.OK);
    }

    @GetMapping("/vehiculos/primera-vez/{id}")
    public ResponseEntity<?> PrimeraVez(@PathVariable int id){
        return new ResponseEntity<>(service.encontrarVehiculosPrimeraVezP(id), HttpStatus.OK);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<?> findRegistro(@PathVariable int id) {
        return new ResponseEntity<>(service.encontrarRegistroById(id), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveRegistro(@Valid @RequestBody RegistroDto registroDto) {
        response.clear();
        service.guardar(registroDto);
        response.put("message","Registro guardado");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/save-entrada")
    public ResponseEntity<?> saveEntrada(@Valid @RequestBody RequestEntradaSalida requestEntrada) {
        response.clear();
        RegistroDto registroDto = service.guardarEntrada(requestEntrada);
        response.put("id",registroDto.getIdRegistro());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRegistro(@PathVariable int id) {

        response.clear();
        service.eliminar(id);
        response.put("message","Registro eliminado");
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editRegistro(@PathVariable int id,@Valid @RequestBody RegistroDto registroDto) {
        response.clear();
        RegistroDto registroDto1 = service.editarRegistro(id,registroDto);
        response.put("message", "Registro editado");
        response.put("registro", registroDto1);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
