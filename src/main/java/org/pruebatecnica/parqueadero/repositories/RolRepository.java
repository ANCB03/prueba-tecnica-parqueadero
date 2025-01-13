package org.pruebatecnica.parqueadero.repositories;

import org.pruebatecnica.parqueadero.entities.Rol;
import org.pruebatecnica.parqueadero.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Integer> {

    @Query(
            value = "SELECT * FROM rol u where u.nombre = :nombre",
            nativeQuery = true
    )
    Optional<Usuario> findByNombre(String nombre);
}
