package com.application.gestiondestock.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enumération des permissions pour l'application de gestion de stock.
 * Ces permissions définissent les actions possibles pour les différents rôles (ADMIN, MANAGER, etc.).
 */
@RequiredArgsConstructor
public enum Permission {

    // Permissions pour les administrateurs
    PRODUIT_READ("produit:read"),
    PRODUIT_UPDATE("produit:update"),
    PRODUIT_CREATE("produit:create"),
    PRODUIT_DELETE("produit:delete"),

    CLIENT_READ("client:read"),
    CLIENT_UPDATE("client:update"),
    CLIENT_CREATE("client:create"),
    CLIENT_DELETE("client:delete"),

    COMMANDE_READ("commande:read"),
    COMMANDE_UPDATE("commande:update"),
    COMMANDE_CREATE("commande:create"),
    COMMANDE_DELETE("commande:delete"),

    FOURNISSEUR_READ("fournisseur:read"),
    FOURNISSEUR_UPDATE("fournisseur:update"),
    FOURNISSEUR_CREATE("fournisseur:create"),
    FOURNISSEUR_DELETE("fournisseur:delete"),

    UTILISATEUR_READ("utilisateur:read"),
    UTILISATEUR_UPDATE("utilisateur:update"),
    UTILISATEUR_CREATE("utilisateur:create"),
    UTILISATEUR_DELETE("utilisateur:delete");

    @Getter
    private final String permission;

}