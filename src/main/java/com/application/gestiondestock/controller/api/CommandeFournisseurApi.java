package com.application.gestiondestock.controller.api;

import com.application.gestiondestock.dto.CommandeFournisseurDto;
import com.application.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.application.gestiondestock.model.EtatCommande;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.application.gestiondestock.Utils.Constants.*;

@Tag(name = "Commandes Fournisseurs", description = "API pour la gestion des commandes fournisseurs")
public interface CommandeFournisseurApi {

    @PostMapping(CREATE_COMMANDE_FOURNISSEUR_ENDPOINT)
    @Operation(summary = "Créer une commande fournisseur", description = "Cette méthode permet de créer ou modifier une commande fournisseur.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La commande fournisseur a été créée/modifiée avec succès."),
        @ApiResponse(responseCode = "400", description = "Les informations fournies sont invalides.")
    })
    CommandeFournisseurDto save(@RequestBody CommandeFournisseurDto dto);

    @PatchMapping(COMMANDE_FOURNISSEUR_ENDPOINT + "/update/etat/{idCommande}/{etatCommande}")
    @Operation(summary = "Mettre à jour l'état d'une commande fournisseur", description = "Cette méthode permet de mettre à jour l'état d'une commande fournisseur.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "L'état de la commande a été mis à jour avec succès."),
        @ApiResponse(responseCode = "400", description = "Les informations fournies sont invalides ou la commande est déjà livrée.")
    })
    CommandeFournisseurDto updateEtatCommande(
        @PathVariable("idCommande") Integer idCommande,
        @PathVariable("etatCommande") EtatCommande etatCommande
    );

    @PatchMapping(COMMANDE_FOURNISSEUR_ENDPOINT + "/update/quantite/{idCommande}/{idLigneCommande}/{quantite}")
    @Operation(summary = "Mettre à jour la quantité d'un produit dans une commande fournisseur", description = "Cette méthode permet de modifier la quantité d'un produit dans une commande fournisseur.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La quantité a été mise à jour."),
        @ApiResponse(responseCode = "400", description = "Les informations fournies sont invalides.")
    })
    CommandeFournisseurDto updateQuantiteCommande(
        @PathVariable("idCommande") Integer idCommande,
        @PathVariable("idLigneCommande") Integer idLigneCommande,
        @PathVariable("quantite") BigDecimal quantite
    );

    @PatchMapping(COMMANDE_FOURNISSEUR_ENDPOINT + "/update/fournisseur/{idCommande}/{idFournisseur}")
    @Operation(summary = "Mettre à jour le fournisseur associé à une commande", description = "Cette méthode permet de modifier le fournisseur d'une commande fournisseur.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Le fournisseur a été mis à jour avec succès."),
        @ApiResponse(responseCode = "400", description = "Les informations fournies sont invalides.")
    })
    CommandeFournisseurDto updateFournisseur(
        @PathVariable("idCommande") Integer idCommande,
        @PathVariable("idFournisseur") Integer idFournisseur
    );

    @PatchMapping(COMMANDE_FOURNISSEUR_ENDPOINT + "/update/produit/{idCommande}/{idLigneCommande}/{idProduit}")
    @Operation(summary = "Mettre à jour un produit dans une commande fournisseur", description = "Cette méthode permet de mettre à jour un produit associé à une ligne d'une commande fournisseur.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "L'produit a été mis à jour avec succès."),
        @ApiResponse(responseCode = "400", description = "Les informations fournies sont invalides.")
    })
    CommandeFournisseurDto updateProduit(
        @PathVariable("idCommande") Integer idCommande,
        @PathVariable("idLigneCommande") Integer idLigneCommande,
        @PathVariable("idProduit") Integer idProduit
    );

    @DeleteMapping(COMMANDE_FOURNISSEUR_ENDPOINT + "/delete/produit/{idCommande}/{idLigneCommande}")
    @Operation(summary = "Supprimer un produit d'une commande fournisseur", description = "Cette méthode permet de supprimer un produit d'une commande fournisseur donnée.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "L'produit a été supprimé avec succès."),
        @ApiResponse(responseCode = "400", description = "Les informations fournies sont invalides.")
    })
    CommandeFournisseurDto deleteProduit(
        @PathVariable("idCommande") Integer idCommande,
        @PathVariable("idLigneCommande") Integer idLigneCommande
    );

    @GetMapping(FIND_COMMANDE_FOURNISSEUR_BY_ID_ENDPOINT)
    @Operation(summary = "Rechercher une commande fournisseur par ID", description = "Cette méthode permet de rechercher une commande fournisseur en fonction de son ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La commande fournisseur a été trouvée."),
        @ApiResponse(responseCode = "404", description = "Aucune commande fournisseur trouvée avec cet ID.")
    })
    CommandeFournisseurDto findById(@PathVariable("idCommandeFournisseur") Integer id);

    @GetMapping(FIND_COMMANDE_FOURNISSEUR_BY_CODE_ENDPOINT)
    @Operation(summary = "Rechercher une commande fournisseur par code", description = "Cette méthode permet de rechercher une commande fournisseur en fonction de son code.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La commande fournisseur a été trouvée."),
        @ApiResponse(responseCode = "404", description = "Aucune commande fournisseur trouvée avec ce code.")
    })
    CommandeFournisseurDto findByCode(@PathVariable("codeCommandeFournisseur") String code);

    @GetMapping(FIND_ALL_COMMANDE_FOURNISSEUR_ENDPOINT)
    @Operation(summary = "Lister toutes les commandes des fournisseurs", description = "Cette méthode permet de lister toutes les commandes des fournisseurs.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La liste des commandes des fournisseurs a été trouvée."),
        @ApiResponse(responseCode = "204", description = "Aucune commande fournisseur à retourner.")
    })
    List<CommandeFournisseurDto> findAll();

    @GetMapping(COMMANDE_FOURNISSEUR_ENDPOINT + "/lignesCommande/{idCommande}")
    @Operation(summary = "Lister les lignes d'une commande fournisseur", description = "Cette méthode permet de lister toutes les lignes d'une commande fournisseur donnée.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La liste des lignes de commande fournisseur a été trouvée."),
        @ApiResponse(responseCode = "404", description = "Aucune commande fournisseur trouvée avec cet ID.")
    })
    List<LigneCommandeFournisseurDto> findAllLignesCommandesFournisseurByCommandeFournisseurId(
        @PathVariable("idCommande") Integer idCommande
    );

    @DeleteMapping(DELETE_COMMANDE_FOURNISSEUR_ENDPOINT)
    @Operation(summary = "Supprimer une commande fournisseur", description = "Cette méthode permet de supprimer une commande fournisseur donnée.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La commande fournisseur a été supprimée avec succès."),
        @ApiResponse(responseCode = "400", description = "La commande fournisseur ne peut pas être supprimée.")
    })
    void delete(@PathVariable("idCommandeFournisseur") Integer id);
}