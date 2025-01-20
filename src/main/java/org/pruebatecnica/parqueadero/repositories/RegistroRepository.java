package org.pruebatecnica.parqueadero.repositories;

import org.pruebatecnica.parqueadero.entities.Registro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface RegistroRepository extends JpaRepository<Registro, Integer> {
    @Query(
            value = "SELECT * FROM registro u where u.id_vehiculo = :placa and u.tipo_registro='ENTRADA'",
            nativeQuery = true
    )
    Optional<Registro> findVehiculoConEntrada(String placa);

    @Query(
            value = "SELECT * FROM registro u where u.id_vehiculo = :placa and u.tipo_registro='ENTRADA' and u.id_parqueadero = :idParqueadero",
            nativeQuery = true
    )
    Optional<Registro> findVehiculoConEntradaParqueadero(String placa, int idParqueadero);

    @Query(
            value = "SELECT * FROM registro u where u.tipo_registro='ENTRADA' and u.id_parqueadero = :idParqueadero",
            nativeQuery = true
    )
    List<Registro> findVehiculosConEntradaParqueadero(int idParqueadero);

    @Query(
            value = "SELECT r.id_vehiculo, COUNT(r.id_vehiculo) AS totalRegistros FROM registro r GROUP BY r.id_vehiculo ORDER BY totalRegistros DESC LIMIT 10",
            nativeQuery = true
    )
    List<Object[]> findTop10Vehiculos();

    @Query(
            value = "SELECT r.id_vehiculo, COUNT(r.id_vehiculo) AS totalRegistros FROM registro r WHERE id_parqueadero = :idParqueadero GROUP BY r.id_vehiculo ORDER BY totalRegistros DESC LIMIT 10",
            nativeQuery = true
    )
    List<Object[]> findTop10VehiculosParqueadero(int idParqueadero);

    @Query(
            value = "SELECT r.id_vehiculo FROM registro r WHERE id_parqueadero = :idParqueadero GROUP BY r.id_vehiculo HAVING COUNT(r.id_vehiculo) = 1",
            nativeQuery = true
    )
    List<Object[]> findFirstTimeParqueadero(int idParqueadero);

}
