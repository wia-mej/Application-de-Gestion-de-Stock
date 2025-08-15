package com.application.gestiondestock.config;

import com.application.gestiondestock.token.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        // Vérifiez si l'en-tête Authorization est présent et commence par "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        // Extraire le jeton JWT de l'en-tête Authorization
        jwt = authHeader.substring(7);

        // Rechercher le token dans la base de données
        var storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);

        // Si le token existe, le marquer comme expiré et révoqué, puis sauvegarder
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
            SecurityContextHolder.clearContext(); // Nettoyer le contexte de sécurité
        }
    }
}