package org.pruebatecnica.parqueadero.repositories;

import org.pruebatecnica.parqueadero.entities.Parqueadero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParqueaderoRepository extends JpaRepository<Parqueadero, Integer> {
}
