package com.application.gestiondestock.controller.api;

import com.application.gestiondestock.dto.CommandeClientDto;
import com.application.gestiondestock.dto.LigneCommandeClientDto;
import com.application.gestiondestock.model.EtatCommande;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.application.gestiondestock.Utils.Constants.APP_ROOT;

@Tag(name = "Commandes Clients", description = "API pour la gestion des commandes clients")
public interface CommandeClientApi {

    @PostMapping(APP_ROOT + "/commandesclients/create")
    @Operation(summary = "Enregistrer une commande client", description = "Cette méthode permet d'enregistrer ou de modifier une commande client.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La commande client a été créée / modifiée"),
        @ApiResponse(responseCode = "400", description = "La commande client n'est pas valide")
    })
    ResponseEntity<CommandeClientDto> save(@RequestBody CommandeClientDto dto);

    @PatchMapping(APP_ROOT + "/commandesclients/update/etat/{idCommande}/{etatCommande}")
    @Operation(summary = "Modifier l'état d'une commande", description = "Cette méthode permet de modifier l'état d'une commande client.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "L'état de la commande a été modifié avec succès"),
        @ApiResponse(responseCode = "400", description = "La commande ou l'état fourni est invalide")
    })
    ResponseEntity<CommandeClientDto> updateEtatCommande(@PathVariable("idCommande") Integer idCommande, @PathVariable("etatCommande") EtatCommande etatCommande);

    @PatchMapping(APP_ROOT + "/commandesclients/update/quantite/{idCommande}/{idLigneCommande}/{quantite}")
    @Operation(summary = "Modifier la quantité d'une ligne de commande", description = "Cette méthode permet de modifier la quantité d'une ligne d'une commande client.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La quantité de la ligne commande a été modifiée avec succès"),
        @ApiResponse(responseCode = "400", description = "Les informations fournies sont invalides")
    })
    ResponseEntity<CommandeClientDto> updateQuantiteCommande(
        @PathVariable("idCommande") Integer idCommande,
        @PathVariable("idLigneCommande") Integer idLigneCommande,
        @PathVariable("quantite") BigDecimal quantite
    );

    @PatchMapping(APP_ROOT + "/commandesclients/update/client/{idCommande}/{idClient}")
    @Operation(summary = "Modifier le client associé à une commande", description = "Cette méthode permet de modifier le client associé à une commande client.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Le client de la commande a été modifié avec succès"),
        @ApiResponse(responseCode = "400", description = "Les informations fournies sont invalides")
    })
    ResponseEntity<CommandeClientDto> updateClient(@PathVariable("idCommande") Integer idCommande, @PathVariable("idClient") Integer idClient);

    @PatchMapping(APP_ROOT + "/commandesclients/update/produit/{idCommande}/{idLigneCommande}/{idProduit}")
    @Operation(summary = "Modifier l'produit associé à une ligne commande", description = "Cette méthode permet de modifier un produit associé à une ligne d'une commande client.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "L'produit de la commande a été modifié avec succès"),
        @ApiResponse(responseCode = "400", description = "Les informations fournies sont invalides")
    })
    ResponseEntity<CommandeClientDto> updateProduit(
        @PathVariable("idCommande") Integer idCommande,
        @PathVariable("idLigneCommande") Integer idLigneCommande,
        @PathVariable("idProduit") Integer idProduit
    );

    @DeleteMapping(APP_ROOT + "/commandesclients/delete/produit/{idCommande}/{idLigneCommande}")
    @Operation(summary = "Supprimer une ligne d'produit d'une commande", description = "Cette méthode permet de supprimer une ligne d'produit associée à une commande client.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La ligne de l'produit a été supprimée avec succès"),
        @ApiResponse(responseCode = "400", description = "Les informations fournies sont invalides")
    })
    ResponseEntity<CommandeClientDto> deleteProduit(@PathVariable("idCommande") Integer idCommande, @PathVariable("idLigneCommande") Integer idLigneCommande);

    @GetMapping(APP_ROOT + "/commandesclients/{idCommandeClient}")
    @Operation(summary = "Rechercher une commande client par ID", description = "Cette méthode permet de récupérer une commande client par son ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La commande client a été trouvée"),
        @ApiResponse(responseCode = "404", description = "Aucune commande trouvée avec l'ID fourni")
    })
    ResponseEntity<CommandeClientDto> findById(@PathVariable Integer idCommandeClient);

    @GetMapping(APP_ROOT + "/commandesclients/filter/{codeCommandeClient}")
    @Operation(summary = "Rechercher une commande client par CODE", description = "Cette méthode permet de récupérer une commande client par son CODE.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La commande client a été trouvée"),
        @ApiResponse(responseCode = "404", description = "Aucune commande trouvée avec le CODE fourni")
    })
    ResponseEntity<CommandeClientDto> findByCode(@PathVariable("codeCommandeClient") String code);

    @GetMapping(APP_ROOT + "/commandesclients/all")
    @Operation(summary = "Retourner la liste des commandes clients", description = "Cette méthode permet de retourner la liste de toutes les commandes clients.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La liste des commandes a été trouvée"),
        @ApiResponse(responseCode = "204", description = "Aucune commande client à retourner")
    })
    ResponseEntity<List<CommandeClientDto>> findAll();

    @GetMapping(APP_ROOT + "/commandesclients/lignesCommande/{idCommande}")
    @Operation(summary = "Retourner les lignes de commande client pour un ID commande", description = "Cette méthode permet de retourner les lignes de commande client associées à une commande donnée.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Les lignes de la commande client ont été trouvées"),
        @ApiResponse(responseCode = "404", description = "Aucune commande client trouvée avec l'ID fourni")
    })
    ResponseEntity<List<LigneCommandeClientDto>> findAllLignesCommandesClientByCommandeClientId(@PathVariable("idCommande") Integer idCommande);

    @DeleteMapping(APP_ROOT + "/commandesclients/delete/{idCommandeClient}")
    @Operation(summary = "Supprimer une commande client", description = "Cette méthode permet de supprimer une commande client donnée.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La commande client a été supprimée avec succès"),
        @ApiResponse(responseCode = "400", description = "La commande client ne peut pas être supprimée car elle est utilisée")
    })
    ResponseEntity<Void> delete(@PathVariable("idCommandeClient") Integer id);
}