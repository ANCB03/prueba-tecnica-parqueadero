package org.pruebatecnica.parqueadero.repositories;

import org.pruebatecnica.parqueadero.entities.BlackListedToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface BlackListTokenRepository extends JpaRepository<BlackListedToken, Long> {
    boolean existsByToken(String token);

    void deleteByBlacklistedAtBefore(LocalDateTime date);
}
