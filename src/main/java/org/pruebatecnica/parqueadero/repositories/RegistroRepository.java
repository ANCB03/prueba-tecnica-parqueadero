package org.pruebatecnica.parqueadero.repositories;

import org.pruebatecnica.parqueadero.entities.Registro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistroRepository extends JpaRepository<Registro, Integer> {
}
