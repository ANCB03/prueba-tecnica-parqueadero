package org.pruebatecnica.parqueadero.implement;

import lombok.RequiredArgsConstructor;
import org.pruebatecnica.parqueadero.entities.BlackListedToken;
import org.pruebatecnica.parqueadero.repositories.BlackListTokenRepository;
import org.pruebatecnica.parqueadero.services.BlackListTokenService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class BlackListTokenImplement implements BlackListTokenService {

    private final BlackListTokenRepository repository;

    @Override
    public void blacklistToken(String token) {
        BlackListedToken blackListedToken = new BlackListedToken();
        blackListedToken.setToken(token);
        ZoneId colombiaZone = ZoneId.of("America/Bogota");
        ZonedDateTime fechaHoraColombia = ZonedDateTime.now(colombiaZone);
        LocalDateTime fechaHoraActual = fechaHoraColombia.toLocalDateTime();
        blackListedToken.setBlacklistedAt(fechaHoraActual);
        repository.save(blackListedToken);
    }

    @Override
    public boolean isTokenBlacklisted(String token) {
        return  repository.existsByToken(token);
    }
    @Scheduled(cron = "0 0 0 * * *")
    @Override
    public void cleanupExpiredTokens() {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(7);
        repository.deleteByBlacklistedAtBefore(cutoffDate);
    }
}
