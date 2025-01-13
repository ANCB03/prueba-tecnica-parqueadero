package org.pruebatecnica.parqueadero.repositories;

import org.pruebatecnica.parqueadero.entities.Parqueadero;
import org.pruebatecnica.parqueadero.entities.Registro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParqueaderoRepository extends JpaRepository<Parqueadero, Integer> {
    @Query(
            value = """
            SELECT 
                p.nombre as nombre_parqueadero,
                COALESCE(SUM(h.monto), 0) as ganancia_total,
                COUNT(DISTINCT h.id_vehiculo) as cantidad_vehiculos
            FROM parqueadero p
            LEFT JOIN historial h ON h.id_parqueadero = p.id_parqueadero
            WHERE 
                h.fecha_salida >= DATE_SUB(CURRENT_DATE(), 
                    INTERVAL DAYOFWEEK(CURRENT_DATE())-1 DAY)
                AND h.fecha_salida < DATE_ADD(CURRENT_DATE(), INTERVAL 1 DAY)
            GROUP BY p.id_parqueadero, p.nombre
            ORDER BY ganancia_total DESC
            LIMIT 3
        """,
            nativeQuery = true
    )
    List<Object[]> getTopParqueaderosGananciaSemana();

    @Query(
            value = "SELECT * FROM parqueadero u where u.id_usuario= :idUsuario",
            nativeQuery = true
    )
    List<Parqueadero> findParqueaderosSocio(int idUsuario);
}
