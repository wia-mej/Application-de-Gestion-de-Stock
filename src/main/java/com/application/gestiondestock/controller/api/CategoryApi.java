package com.application.gestiondestock.controller.api;

import com.application.gestiondestock.dto.Categorydto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.application.gestiondestock.Utils.Constants.APP_ROOT;

@Tag(name = "Categories", description = "API pour la gestion des catégories")
public interface CategoryApi {

    @PostMapping(value = APP_ROOT + "/categories/create", 
                 consumes = MediaType.APPLICATION_JSON_VALUE, 
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Enregistrer une catégorie", 
        description = "Cette méthode permet d'enregistrer ou de modifier une catégorie."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "L'objet category a été créé / modifié"),
        @ApiResponse(responseCode = "400", description = "L'objet category n'est pas valide")
    })
    Categorydto save(@RequestBody Categorydto dto);

    @GetMapping(value = APP_ROOT + "/categories/{idCategory}", 
                produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Rechercher une catégorie par ID", 
        description = "Cette méthode permet de chercher une catégorie par son ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La catégorie a été trouvée dans la BDD"),
        @ApiResponse(responseCode = "404", description = "Aucune catégorie n'existe dans la BDD avec l'ID fourni")
    })
    Categorydto findById(@PathVariable("idCategory") Integer idCategory);

    @GetMapping(value = APP_ROOT + "/categories/filter/{codeCategory}", 
                produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Rechercher une catégorie par CODE", 
        description = "Cette méthode permet de chercher une catégorie par son CODE."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La catégorie a été trouvée dans la BDD"),
        @ApiResponse(responseCode = "404", description = "Aucune catégorie n'existe dans la BDD avec le CODE fourni")
    })
    Categorydto findByCode(@PathVariable("codeCategory") String codeCategory);

    @GetMapping(value = APP_ROOT + "/categories/all", 
                produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Renvoi de la liste des catégories", 
        description = "Cette méthode permet de chercher et renvoyer la liste des catégories présentes dans la BDD."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La liste des catégories / une liste vide")
    })
    List<Categorydto> findAll();

    @DeleteMapping(value = APP_ROOT + "/categories/delete/{idCategory}")
    @Operation(
        summary = "Supprimer une catégorie", 
        description = "Cette méthode permet de supprimer une catégorie par son ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La catégorie a été supprimée avec succès")
    })
    void delete(@PathVariable("idCategory") Integer id);
}