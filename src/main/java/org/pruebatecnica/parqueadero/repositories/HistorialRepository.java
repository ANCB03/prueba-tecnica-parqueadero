package org.pruebatecnica.parqueadero.repositories;

import org.pruebatecnica.parqueadero.entities.Historial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistorialRepository extends JpaRepository<Historial, Integer> {
}
