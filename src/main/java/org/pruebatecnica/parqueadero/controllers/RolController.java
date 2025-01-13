package org.pruebatecnica.parqueadero.controllers;

import lombok.RequiredArgsConstructor;
import org.pruebatecnica.parqueadero.dtos.RolDto;
import org.pruebatecnica.parqueadero.services.RolService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/rol")
@CrossOrigin
@RequiredArgsConstructor
public class RolController {
    private final RolService service;

    private Map<String,Object> response = new HashMap<>();

    @GetMapping("/all")
    public ResponseEntity<?> RolList(){
        return new ResponseEntity<>(service.listarRoles(), HttpStatus.OK);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<?> findRol(@PathVariable int id) {
        return new ResponseEntity<>(service.encontrarRolById(id), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveRol(@Valid @RequestBody RolDto rolDto) {
        response.clear();
        service.guardar(rolDto);
        response.put("message","Rol guardado");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRol(@PathVariable int id) {

        response.clear();
        service.eliminar(id);
        response.put("message","Rol eliminado");
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editParqueadero(@PathVariable int id,@Valid @RequestBody RolDto rolDto) {
        response.clear();
        RolDto rolDto1 = service.editarRol(id,rolDto);
        response.put("message", "Rol editado");
        response.put("rol", rolDto1);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
