package com.application.gestiondestock.auth;

import com.application.gestiondestock.config.JwtService;
import com.application.gestiondestock.token.Token;
import com.application.gestiondestock.token.TokenRepository;
import com.application.gestiondestock.token.TokenType;
import com.application.gestiondestock.model.Roles;
import com.application.gestiondestock.model.Utilisateur;
import com.application.gestiondestock.repository.UtilisateurRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthentificationService {

    private final UtilisateurRepository utilisateurRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Inscription d'un nouvel utilisateur.
     */
    public AuthentificationResponse register(RegisterRequest request) {
        var utilisateur = Utilisateur.builder()
                .prenom(request.getFirstname())
                .nom(request.getLastname())
                .email(request.getEmail())
                .moteDePasse(request.getPassword()) // Assurez-vous qu'il soit encodé !
                .roles(List.of(request.getRole()))
                .build();
        var utilisateurSauvegarde = utilisateurRepository.save(utilisateur);
        var jwtToken = jwtService.generateToken(utilisateur);
        var refreshToken = jwtService.generateRefreshToken(utilisateur);
        saveUserToken(utilisateurSauvegarde, jwtToken);

        return AuthentificationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * Authentification d'un utilisateur existant.
     */
    public AuthentificationResponse authenticate(AuthentificationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        var utilisateur = utilisateurRepository.findUtilisateurByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        var jwtToken = jwtService.generateToken(utilisateur);
        var refreshToken = jwtService.generateRefreshToken(utilisateur);
        revokeAllUserTokens(utilisateur);
        saveUserToken(utilisateur, jwtToken);

        return AuthentificationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * Rafraîchit le token JWT.
     */
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail != null) {
            var utilisateur = utilisateurRepository.findUtilisateurByEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

            if (jwtService.isTokenValid(refreshToken, utilisateur)) {
                var accessToken = jwtService.generateToken(utilisateur);
                revokeAllUserTokens(utilisateur);
                saveUserToken(utilisateur, accessToken);

                var authResponse = AuthentificationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    /**
     * Enregistre le token de l'utilisateur.
     */
    private void saveUserToken(Utilisateur utilisateur, String jwtToken) {
        var token = Token.builder()
                .utilisateur(utilisateur)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    /**
     * Révoque tous les tokens valides de l'utilisateur.
     */
    private void revokeAllUserTokens(Utilisateur utilisateur) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(utilisateur.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}