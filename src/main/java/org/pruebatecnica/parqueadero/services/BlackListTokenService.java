package org.pruebatecnica.parqueadero.services;

public interface BlackListTokenService {
    public void blacklistToken(String token);
    public boolean isTokenBlacklisted(String token);

    public void cleanupExpiredTokens();
}
