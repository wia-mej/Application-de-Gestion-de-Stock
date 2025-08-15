package com.application.gestiondestock.auditing;

import com.application.gestiondestock.model.Utilisateur;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * Cette classe implémente `AuditorAware` pour intégrer l'ID de l'utilisateur authentifié
 * dans les audits (comme la création ou la modification d'entités).
 */
public class ApplicationAuditAware implements AuditorAware<Integer> {

    @Override
    public Optional<Integer> getCurrentAuditor() {
        // Récupérer l'authentification dans le contexte de sécurité
        Authentication authentication = SecurityContextHolder
            .getContext()
            .getAuthentication();

        // Vérifier si l'utilisateur est anonyme ou non authentifié
        if (authentication == null || !authentication.isAuthenticated() ||
            authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        // Extraire l'utilisateur authentifié
        Utilisateur utilisateurPrincipal = (Utilisateur) authentication.getPrincipal();

        // Retourner l'ID de l'utilisateur
        return Optional.ofNullable(utilisateurPrincipal.getId());
    }
}