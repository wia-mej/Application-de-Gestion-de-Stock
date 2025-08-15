package com.application.gestiondestock.controller.api;

import com.application.gestiondestock.dto.EntrepriseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.application.gestiondestock.Utils.Constants.ENTREPRISE_ENDPOINT;

@Tag(name = "Entreprises", description = "API pour la gestion des entreprises")
public interface EntrepriseApi {

    @PostMapping(ENTREPRISE_ENDPOINT + "/create")
    @Operation(summary = "Enregistrer une entreprise", description = "Cette méthode permet de créer ou de modifier une entreprise")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "L'entreprise a été créée/modifiée avec succès."),
        @ApiResponse(responseCode = "400", description = "Les informations fournies sont invalides.")
    })
    EntrepriseDto save(@RequestBody EntrepriseDto dto);

    @GetMapping(ENTREPRISE_ENDPOINT + "/{idEntreprise}")
    @Operation(summary = "Rechercher une entreprise par ID", description = "Cette méthode permet de rechercher une entreprise grâce à son ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "L'entreprise a été trouvée dans la base de données."),
        @ApiResponse(responseCode = "404", description = "Aucune entreprise avec cet ID n'a été trouvée.")
    })
    EntrepriseDto findById(@PathVariable("idEntreprise") Integer id);

    @GetMapping(ENTREPRISE_ENDPOINT + "/all")
    @Operation(summary = "Lister toutes les entreprises", description = "Cette méthode permet de renvoyer la liste de toutes les entreprises enregistrées dans la base de données")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La liste des entreprises."),
        @ApiResponse(responseCode = "204", description = "Aucune entreprise trouvée.")
    })
    List<EntrepriseDto> findAll();

    @DeleteMapping(ENTREPRISE_ENDPOINT + "/delete/{idEntreprise}")
    @Operation(summary = "Supprimer une entreprise", description = "Cette méthode permet de supprimer une entreprise par son ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "L'entreprise a été supprimée avec succès."),
        @ApiResponse(responseCode = "400", description = "L'entreprise ne peut pas être supprimée car elle est liée à d'autres entités.")
    })
    void delete(@PathVariable("idEntreprise") Integer id);
}