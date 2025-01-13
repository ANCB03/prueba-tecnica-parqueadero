package org.pruebatecnica.parqueadero.repositories;

import org.pruebatecnica.parqueadero.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    @Query(
            value = "SELECT * FROM usuario u where u.email = :email",
            nativeQuery = true
    )
    Optional<Usuario> findByEmail(String email);

    @Query(
            value = "SELECT * FROM usuario u where u.documento = :documento",
            nativeQuery = true
    )
    Optional<Usuario> findByDocumento(String documento);

    @Query(
            value = """
            SELECT 
                u.nombre,
                u.apellido,
                COUNT(r.id_registro) as cantidad_vehiculos
            FROM usuario u
            INNER JOIN parqueadero p ON p.id_usuario = u.id_usuario
            INNER JOIN registro r ON r.id_parqueadero = p.id_parqueadero
            WHERE 
                r.fecha_registro >= DATE_SUB(CURRENT_DATE(), 
                    INTERVAL DAYOFWEEK(CURRENT_DATE())-1 DAY)
                AND r.fecha_registro < DATE_ADD(CURRENT_DATE(), INTERVAL 1 DAY)
                AND r.tipo_registro = 'SALIDA'
            GROUP BY u.id_usuario, u.nombre, u.apellido
            ORDER BY cantidad_vehiculos DESC
            LIMIT 3
        """,
            nativeQuery = true
    )
    List<Object[]> getTopSociosIngresosSemana();
}
