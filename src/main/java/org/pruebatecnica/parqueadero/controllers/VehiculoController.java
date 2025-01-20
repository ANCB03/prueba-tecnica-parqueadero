package org.pruebatecnica.parqueadero.controllers;

import lombok.RequiredArgsConstructor;
import org.pruebatecnica.parqueadero.dtos.VehiculoDto;
import org.pruebatecnica.parqueadero.services.VehiculoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/vehiculo")
@CrossOrigin
@RequiredArgsConstructor
public class VehiculoController {
    private final VehiculoService service;

    private Map<String,Object> response = new HashMap<>();
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/")
    public ResponseEntity<?> VehiculoList(){
        return new ResponseEntity<>(service.listarVehiculos(), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_SOCIO')")
    @GetMapping({"/{placa}"})
    public ResponseEntity<?> findVehiculo(@PathVariable String placa) {
        return new ResponseEntity<>(service.encontrarVehiculoByPlaca(placa), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_SOCIO')")
    @GetMapping({"/parqueadero/{id}"})
    public ResponseEntity<?> findVehiculosParqueadero(@PathVariable int id) {
        return new ResponseEntity<>(service.encontrarVehiculosByParqueadero(id), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_SOCIO')")
    @PostMapping("/")
    public ResponseEntity<?> saveVehiculo(@Valid @RequestBody VehiculoDto vehiculoDto) {
        response.clear();
        service.guardar(vehiculoDto);
        response.put("message","Vehiculo guardado");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{placa}")
    public ResponseEntity<?> deleteVehiculo(@PathVariable String placa) {

        response.clear();
        service.eliminar(placa);
        response.put("message","Vehiculo eliminado");
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/")
    public ResponseEntity<?> editUsuario(@Valid @RequestBody VehiculoDto vehiculoDto) {
        response.clear();
        VehiculoDto vehiculoDto1 = service.editarVehiculo(vehiculoDto);
        response.put("message", "Vehiculo editado");
        response.put("vehiculo", vehiculoDto1);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
