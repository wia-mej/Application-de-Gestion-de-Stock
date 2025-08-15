package com.application.gestiondestock.controller.api;

import com.application.gestiondestock.dto.FournisseurDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.application.gestiondestock.Utils.Constants.FOURNISSEUR_ENDPOINT;

@Tag(name = "Fournisseurs", description = "API pour la gestion des fournisseurs")
public interface FournisseurApi {

    @PostMapping(FOURNISSEUR_ENDPOINT + "/create")
    @Operation(summary = "Enregistrer un fournisseur", description = "Cette méthode permet d'enregistrer ou de modifier un fournisseur.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Le fournisseur a été créé/modifié avec succès."),
        @ApiResponse(responseCode = "400", description = "Les informations fournies sont invalides.")
    })
    FournisseurDto save(@RequestBody FournisseurDto dto);

    @GetMapping(FOURNISSEUR_ENDPOINT + "/{idFournisseur}")
    @Operation(summary = "Rechercher un fournisseur par ID", description = "Cette méthode permet de rechercher un fournisseur grâce à son ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Le fournisseur a été trouvé."),
        @ApiResponse(responseCode = "404", description = "Aucun fournisseur avec cet ID n'a été trouvé.")
    })
    FournisseurDto findById(@PathVariable("idFournisseur") Integer id);

    @GetMapping(FOURNISSEUR_ENDPOINT + "/all")
    @Operation(summary = "Lister tous les fournisseurs", description = "Cette méthode permet de lister tous les fournisseurs enregistrés dans la base de données.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La liste des fournisseurs."),
        @ApiResponse(responseCode = "204", description = "Aucun fournisseur trouvé.")
    })
    List<FournisseurDto> findAll();

    @DeleteMapping(FOURNISSEUR_ENDPOINT + "/delete/{idFournisseur}")
    @Operation(summary = "Supprimer un fournisseur", description = "Cette méthode permet de supprimer un fournisseur par son ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Le fournisseur a été supprimé avec succès."),
        @ApiResponse(responseCode = "400", description = "Le fournisseur ne peut pas être supprimé car il est utilisé.")
    })
    void delete(@PathVariable("idFournisseur") Integer id);
}