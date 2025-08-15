package com.application.gestiondestock.controller.api;

import com.application.gestiondestock.dto.ChangerMotDePasseUtilisateurDto;
import com.application.gestiondestock.dto.UtilisateurDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.application.gestiondestock.Utils.Constants.UTILISATEUR_ENDPOINT;

@Tag(name = "Utilisateurs", description = "API pour la gestion des utilisateurs")
public interface UtilisateurApi {

    @PostMapping(UTILISATEUR_ENDPOINT + "/create")
    @Operation(summary = "Enregistrer ou modifier un utilisateur", description = "Cette méthode permet d'enregistrer ou de modifier un utilisateur.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "L'utilisateur a été créé/modifié avec succès."),
        @ApiResponse(responseCode = "400", description = "Données invalides lors de la création ou la modification d'un utilisateur.")
    })
    UtilisateurDto save(@RequestBody UtilisateurDto dto);

    @PostMapping(UTILISATEUR_ENDPOINT + "/update/password")
    @Operation(summary = "Changer le mot de passe d'un utilisateur", description = "Cette méthode permet de modifier le mot de passe d'un utilisateur via l'objet spécifié.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mot de passe modifié avec succès."),
        @ApiResponse(responseCode = "400", description = "Impossible de modifier le mot de passe en raison de données invalides.")
    })
    UtilisateurDto changerMotDePasse(@RequestBody ChangerMotDePasseUtilisateurDto dto);

    @GetMapping(UTILISATEUR_ENDPOINT + "/{idUtilisateur}")
    @Operation(summary = "Rechercher un utilisateur par ID", description = "Cette méthode permet de récupérer un utilisateur en fonction de son ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Utilisateur trouvé avec succès."),
        @ApiResponse(responseCode = "404", description = "Aucun utilisateur trouvé pour l'ID donné.")
    })
    UtilisateurDto findById(@PathVariable("idUtilisateur") Integer id);

    @GetMapping(UTILISATEUR_ENDPOINT + "/find/{email}")
    @Operation(summary = "Rechercher un utilisateur par email", description = "Cette méthode permet de récupérer un utilisateur à partir de son email.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Utilisateur trouvé avec succès."),
        @ApiResponse(responseCode = "404", description = "Aucun utilisateur trouvé pour l'email donné.")
    })
    UtilisateurDto findByEmail(@PathVariable("email") String email);

    @GetMapping(UTILISATEUR_ENDPOINT + "/all")
    @Operation(summary = "Afficher tous les utilisateurs", description = "Cette méthode retourne la liste de tous les utilisateurs présents dans la base de données.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des utilisateurs renvoyée avec succès."),
        @ApiResponse(responseCode = "204", description = "Aucun utilisateur trouvé dans la base de données.")
    })
    List<UtilisateurDto> findAll();

    @DeleteMapping(UTILISATEUR_ENDPOINT + "/delete/{idUtilisateur}")
    @Operation(summary = "Supprimer un utilisateur par ID", description = "Cette méthode supprime un utilisateur basé sur l'ID fourni.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Utilisateur supprimé avec succès."),
        @ApiResponse(responseCode = "400", description = "Impossible de supprimer l'utilisateur en raison de contraintes liées à d'autres entités.")
    })
    void delete(@PathVariable("idUtilisateur") Integer id);
}