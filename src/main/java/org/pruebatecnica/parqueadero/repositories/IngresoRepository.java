package org.pruebatecnica.parqueadero.repositories;

import org.pruebatecnica.parqueadero.entities.Ingreso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngresoRepository extends JpaRepository<Ingreso, Integer> {
}
