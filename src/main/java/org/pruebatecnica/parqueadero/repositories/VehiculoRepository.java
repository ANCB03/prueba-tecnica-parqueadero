package org.pruebatecnica.parqueadero.repositories;

import org.pruebatecnica.parqueadero.entities.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculoRepository extends JpaRepository<Vehiculo, String> {
}
