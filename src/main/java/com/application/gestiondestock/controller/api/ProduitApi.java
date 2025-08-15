package com.application.gestiondestock.controller.api;

import com.application.gestiondestock.dto.LigneCommandeClientDto;
import com.application.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.application.gestiondestock.dto.LigneVenteDto;
import com.application.gestiondestock.dto.ProduitDto;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.application.gestiondestock.Utils.Constants.APP_ROOT;


@Tag(name = "Produits")
public interface ProduitApi {

    @PostMapping(value = APP_ROOT + "/produits/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Enregistrer un produit", description = "Cette methode permet d'enregistrer ou modifier un produit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'objet produit créé / modifié",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProduitDto.class))),
            @ApiResponse(responseCode = "400", description = "L'objet produit n'est pas valide")
    })
    ProduitDto save(@RequestBody ProduitDto dto);

    @GetMapping(value = APP_ROOT + "/produits/{idProduit}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Rechercher un produit par ID",
            description = "Cette méthode permet de chercher un produit par son ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Le produit a été trouvé dans la BDD",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProduitDto.class))),
            @ApiResponse(responseCode = "404", description = "Aucun produit n'existe dans la BDD avec l'ID fourni")
    })

    ProduitDto findById(@PathVariable("idProduit") Integer id);

    @GetMapping(value = APP_ROOT + "/produits/filter/{codeProduit}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Rechercher un produit par CODE",
            description = "Cette méthode permet de chercher un produit par son CODE"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Le produit a été trouvé dans la BDD",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProduitDto.class))),
            @ApiResponse(responseCode = "404", description = "Aucun produit n'existe dans la BDD avec le CODE fourni")
    })

    ProduitDto findByCodeProduit(@PathVariable("codeProduit") String codeProduit);

    @GetMapping(value = APP_ROOT + "/produits/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Renvoie la liste des produits",
            description = "Cette méthode permet de chercher et renvoyer la liste des produits qui existent dans la BDD"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La liste des produits (ou une liste vide)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProduitDto.class)))
    })

    List<ProduitDto> findAll();

    @GetMapping(value = APP_ROOT + "/produits/historique/vente/{idProduit}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Historique des ventes d'un produit",
            description = "Cette méthode permet de récupérer l'historique des ventes d'un produit"
    )
    List<LigneVenteDto> findHistoriqueVentes(@PathVariable("idProduit") Integer idProduit);

    @GetMapping(value = APP_ROOT + "/produits/historique/commandeclient/{idProduit}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Historique des commandes client d'un produit",
            description = "Cette méthode permet de récupérer l'historique des commandes client d'un produit"
    )
    List<LigneCommandeClientDto> findHistoriqueCommandeClient(@PathVariable("idProduit") Integer idProduit);

    @GetMapping(value = APP_ROOT + "/produits/historique/commandefournisseur/{idProduit}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Historique des commandes fournisseur d'un produit",
            description = "Cette méthode permet de récupérer l'historique des commandes fournisseur d'un produit"
    )

    List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(@PathVariable("idProduit") Integer idProduit);

    @GetMapping(value = APP_ROOT + "/produits/filter/category/{idCategory}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Liste des produits par catégorie",
            description = "Cette méthode permet de récupérer tous les produits liés à une catégorie donnée"
    )
    List<ProduitDto> findAllProduitByIdCategory(@PathVariable("idCategory") Integer idCategory);

    @DeleteMapping(value = APP_ROOT + "/produits/delete/{idProduit}")
    @Operation(
            summary = "Supprimer un produit",
            description = "Cette méthode permet de supprimer un produit par ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Le produit a été supprimé")
    })

    void delete(@PathVariable("idProduit") Integer id);

}
