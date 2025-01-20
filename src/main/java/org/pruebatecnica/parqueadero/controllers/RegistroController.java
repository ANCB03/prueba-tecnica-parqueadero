package org.pruebatecnica.parqueadero.controllers;

import lombok.RequiredArgsConstructor;
import org.pruebatecnica.parqueadero.dtos.RegistroDto;
import org.pruebatecnica.parqueadero.dtos.RequestEntradaSalida;
import org.pruebatecnica.parqueadero.services.RegistroService;
import org.pruebatecnica.parqueadero.util.TokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_SOCIO')")
    @GetMapping("/")
    public ResponseEntity<?> RegistroList(){
        return new ResponseEntity<>(service.listarRegistros(), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_SOCIO')")
    @GetMapping("/top-10")
    public ResponseEntity<?> Top10Vehiculos(){
        return new ResponseEntity<>(service.encontrarTop10Vehiculos(), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_SOCIO')")
    @GetMapping("/top-10/{id}")
    public ResponseEntity<?> Top10Vehiculos(@PathVariable int id){
        return new ResponseEntity<>(service.encontrarTop10VehiculosParqueadero(id), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_SOCIO')")
    @GetMapping("/vehiculos/primera-vez/{id}")
    public ResponseEntity<?> PrimeraVez(@PathVariable int id){
        return new ResponseEntity<>(service.encontrarVehiculosPrimeraVezP(id), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_SOCIO')")
    @GetMapping({"/{id}"})
    public ResponseEntity<?> findRegistro(@PathVariable int id) {
        return new ResponseEntity<>(service.encontrarRegistroById(id), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_SOCIO')")
    @PostMapping("/")
    public ResponseEntity<?> saveRegistro(@Valid @RequestBody RegistroDto registroDto) {
        response.clear();
        service.guardar(registroDto);
        response.put("message","Registro guardado");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ROLE_SOCIO')")
    @PostMapping("/save-entrada")
    public ResponseEntity<?> saveEntrada(@Valid @RequestBody RequestEntradaSalida requestEntrada, @RequestHeader("Authorization") String bearerToken) {
        response.clear();
        String token = bearerToken.substring(7);
        RegistroDto registroDto = service.guardarEntrada(requestEntrada, TokenProvider.getUserName(token));
        response.put("id",registroDto.getIdRegistro());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRegistro(@PathVariable int id) {

        response.clear();
        service.eliminar(id);
        response.put("message","Registro eliminado");
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/")
    public ResponseEntity<?> editRegistro(@Valid @RequestBody RegistroDto registroDto) {
        response.clear();
        RegistroDto registroDto1 = service.editarRegistro(registroDto);
        response.put("message", "Registro editado");
        response.put("registro", registroDto1);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
