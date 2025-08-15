package com.application.gestiondestock.controller.api;

import com.application.gestiondestock.dto.VenteDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.application.gestiondestock.Utils.Constants.VENTES_ENDPOINT;

@Tag(name = "Ventes", description = "API pour la gestion des ventes")
public interface VenteApi {

    @PostMapping(VENTES_ENDPOINT + "/create")
    @Operation(summary = "Créer ou modifier une vente", description = "Cette méthode permet d'enregistrer ou de modifier une vente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La vente a été créée/modifiée avec succès."),
        @ApiResponse(responseCode = "400", description = "Les données fournies sont invalides.")
    })
    VenteDto save(@RequestBody VenteDto dto);

    @GetMapping(VENTES_ENDPOINT + "/{idVente}")
    @Operation(summary = "Rechercher une vente par ID", description = "Cette méthode permet de rechercher une vente en utilisant son ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La vente a été trouvée avec succès."),
        @ApiResponse(responseCode = "404", description = "Aucune vente trouvée pour l'ID fourni.")
    })
    VenteDto findById(@PathVariable("idVente") Integer id);

    @GetMapping(VENTES_ENDPOINT + "/code/{codeVente}")
    @Operation(summary = "Rechercher une vente par Code", description = "Cette méthode permet de rechercher une vente en utilisant son code.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La vente a été trouvée avec succès."),
        @ApiResponse(responseCode = "404", description = "Aucune vente trouvée pour le code fourni.")
    })
    VenteDto findByCode(@PathVariable("codeVente") String code);

    @GetMapping(VENTES_ENDPOINT + "/all")
    @Operation(summary = "Lister toutes les ventes", description = "Cette méthode permet de récupérer toutes les ventes enregistrées.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La liste des ventes a été renvoyée avec succès."),
        @ApiResponse(responseCode = "204", description = "Aucune vente disponible dans la base de données.")
    })
    List<VenteDto> findAll();

    @DeleteMapping(VENTES_ENDPOINT + "/delete/{idVente}")
    @Operation(summary = "Supprimer une vente", description = "Cette méthode permet de supprimer une vente en fournissant son ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La vente a été supprimée avec succès."),
        @ApiResponse(responseCode = "400", description = "La vente ne peut pas être supprimée en raison de contraintes liées à d'autres entités.")
    })
    void delete(@PathVariable("idVente") Integer id);
}