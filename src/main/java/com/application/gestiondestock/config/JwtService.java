package com.application.gestiondestock.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${application.jwt.secret-key}")
    private String secretKey;

    @Value("${application.jwt.expiration}")
    private long jwtExpiration;

    @Value("${application.jwt.refresh-expiration}")
    private long refreshExpiration;

    /**
     * Extrait le nom d'utilisateur (subject) du token JWT.
     *
     * @param token Token JWT
     * @return Nom d'utilisateur
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrait une information spécifique (claim) du token JWT.
     *
     * @param token           Token JWT
     * @param claimsResolver  Fonction pour résoudre le claim
     * @param <T>             Type du claim
     * @return Claim extrait
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Génére un token simple pour un utilisateur donné.
     *
     * @param userDetails Détails de l'utilisateur
     * @return Token JWT
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Génère un token JWT avec des claims additionnels pour un utilisateur donné.
     *
     * @param extraClaims Claims additionnels
     * @param userDetails Détails de l'utilisateur
     * @return Token JWT
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    /**
     * Génére un token de rafraîchissement (Refresh Token) pour l'utilisateur.
     *
     * @param userDetails Détails de l'utilisateur
     * @return Refresh Token
     */
    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    /**
     * Méthode centrale pour générer un token JWT (utilisé pour Access Token et Refresh Token).
     *
     * @param extraClaims Claims additionnels
     * @param userDetails Détails de l'utilisateur
     * @param expiration  Durée d'expiration du token
     * @return Token JWT
     */
    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Vérifie si un token est valide pour un utilisateur.
     *
     * @param token       Token JWT
     * @param userDetails Détails de l'utilisateur
     * @return True si validé, sinon False
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Vérifie si le token a expiré.
     *
     * @param token Token JWT
     * @return True si le token est expiré, sinon False
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extrait la date d'expiration du token.
     *
     * @param token Token JWT
     * @return Date d'expiration
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrait l'ensemble des claims d'un token JWT.
     *
     * @param token Token JWT
     * @return Claims extraits
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Retourne la clé de signature pour les tokens JWT à partir de la clé secrète configurée.
     *
     * @return Clé de signature
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}