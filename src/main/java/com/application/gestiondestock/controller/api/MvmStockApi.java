package com.application.gestiondestock.controller.api;

import com.application.gestiondestock.dto.MvmStockDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.application.gestiondestock.Utils.Constants.APP_ROOT;

@Tag(name = "Mouvements Stock", description = "API pour la gestion des mouvements de stock")
public interface MvmStockApi {

    @GetMapping(APP_ROOT + "/mvmStock/stockreel/{idProduit}")
    @Operation(summary = "Stock Réel", description = "Cette méthode permet de récupérer le stock réel d'un article donné.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Le stock réel de l'article."),
        @ApiResponse(responseCode = "404", description = "Aucun stock trouvé pour l'article fourni.")
    })
    BigDecimal stockReelProduit(@PathVariable("idProduit") Integer idProduit);

    @GetMapping(APP_ROOT + "/mvmStock/filter/article/{idProduit}")
    @Operation(summary = "Historique des mouvements d'un article", description = "Cette méthode permet de récupérer les mouvements de stock pour un article donné.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des mouvements de stock de l'article."),
        @ApiResponse(responseCode = "404", description = "Aucun mouvement trouvé pour l'article fourni.")
    })
    List<MvmStockDto> mvtStkProduit(@PathVariable("idProduit") Integer idProduit);

    @PostMapping(APP_ROOT + "/mvmStock/entree")
    @Operation(summary = "Entrée de stock", description = "Cette méthode permet d'enregistrer un mouvement d'entrée de stock.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "L'entrée de stock a été enregistrée."),
        @ApiResponse(responseCode = "400", description = "Les informations fournies sont invalides.")
    })
    MvmStockDto entreeStock(@RequestBody MvmStockDto dto);

    @PostMapping(APP_ROOT + "/mvmStock/sortie")
    @Operation(summary = "Sortie de stock", description = "Cette méthode permet d'enregistrer un mouvement de sortie de stock.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La sortie de stock a été enregistrée."),
        @ApiResponse(responseCode = "400", description = "Les informations fournies sont invalides.")
    })
    MvmStockDto sortieStock(@RequestBody MvmStockDto dto);

    @PostMapping(APP_ROOT + "/mvmStock/correctionpos")
    @Operation(summary = "Correction positive de stock", description = "Cette méthode permet d'enregistrer une correction positive sur le stock.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La correction positive de stock a été enregistrée."),
        @ApiResponse(responseCode = "400", description = "Les informations fournies sont invalides.")
    })
    MvmStockDto correctionStockPos(@RequestBody MvmStockDto dto);

    @PostMapping(APP_ROOT + "/mvmStock/correctionneg")
    @Operation(summary = "Correction négative de stock", description = "Cette méthode permet d'enregistrer une correction négative sur le stock.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La correction négative de stock a été enregistrée."),
        @ApiResponse(responseCode = "400", description = "Les informations fournies sont invalides.")
    })
    MvmStockDto correctionStockNeg(@RequestBody MvmStockDto dto);
}