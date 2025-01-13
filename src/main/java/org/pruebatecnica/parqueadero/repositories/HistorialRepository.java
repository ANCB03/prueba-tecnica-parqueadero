package org.pruebatecnica.parqueadero.repositories;

import org.pruebatecnica.parqueadero.entities.Historial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface HistorialRepository extends JpaRepository<Historial, Integer> {
    @Query(
            value = """
            SELECT COALESCE(SUM(monto), 0)
            FROM historial 
            WHERE id_parqueadero = :idParqueadero 
            AND DATE(fecha_salida) = CURRENT_DATE()
        """,
            nativeQuery = true
    )
    BigDecimal gananciasHoy(int idParqueadero);

    @Query(
            value = """
            SELECT COALESCE(SUM(monto), 0)
            FROM historial 
            WHERE id_parqueadero = :idParqueadero 
            AND fecha_salida >= DATE_SUB(CURRENT_DATE(), 
                INTERVAL DAYOFWEEK(CURRENT_DATE())-1 DAY)
            AND fecha_salida < DATE_ADD(CURRENT_DATE(), INTERVAL 1 DAY)
        """,
            nativeQuery = true
    )
    BigDecimal gananciasSemana(int idParqueadero);

    @Query(
            value = """
            SELECT COALESCE(SUM(monto), 0)
            FROM historial 
            WHERE id_parqueadero = :idParqueadero 
            AND YEAR(fecha_salida) = YEAR(CURRENT_DATE())
            AND MONTH(fecha_salida) = MONTH(CURRENT_DATE())
        """,
            nativeQuery = true
    )
    BigDecimal gananciasMes(int idParqueadero);

    @Query(
            value = """
            SELECT COALESCE(SUM(monto), 0)
            FROM historial 
            WHERE id_parqueadero = :idParqueadero 
            AND YEAR(fecha_salida) = YEAR(CURRENT_DATE())
        """,
            nativeQuery = true
    )
    BigDecimal gananciasAnio(int idParqueadero);

}
