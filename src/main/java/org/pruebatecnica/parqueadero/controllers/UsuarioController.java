package org.pruebatecnica.parqueadero.controllers;

import lombok.RequiredArgsConstructor;
import org.pruebatecnica.parqueadero.dtos.RolDto;
import org.pruebatecnica.parqueadero.dtos.UsuarioDto;
import org.pruebatecnica.parqueadero.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
@CrossOrigin
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService service;

    private Map<String,Object> response = new HashMap<>();
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<?> UsuarioList(){
        return new ResponseEntity<>(service.listarUsuarios(), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/top3-usuarios")
    public ResponseEntity<?> top3Usuarios(){
        return new ResponseEntity<>(service.top3Usuarios(), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping({"/{id}"})
    public ResponseEntity<?> findUsuario(@PathVariable int id) {
        return new ResponseEntity<>(service.encontrarUsuarioById(id), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<?> saveUsuario(@Valid @RequestBody UsuarioDto usuarioDto) {
        response.clear();
        service.guardar(usuarioDto);
        response.put("message","Usuario guardado");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable int id) {

        response.clear();
        service.eliminar(id);
        response.put("message","Usuario eliminado");
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editUsuario(@PathVariable int id,@Valid @RequestBody UsuarioDto usuarioDto) {
        response.clear();
        UsuarioDto usuarioDto1 = service.editarUsuario(id,usuarioDto);
        response.put("message", "Usuario editado");
        response.put("usuario", usuarioDto1);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
