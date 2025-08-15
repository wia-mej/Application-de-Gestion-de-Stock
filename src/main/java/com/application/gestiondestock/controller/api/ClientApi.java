package com.application.gestiondestock.controller.api;

import com.application.gestiondestock.dto.ClientDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.application.gestiondestock.Utils.Constants.APP_ROOT;

@Tag(name = "Clients", description = "API pour la gestion des clients")
public interface ClientApi {

    @PostMapping(value = APP_ROOT + "/clients/create",
                 consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Enregistrer un client",
        description = "Cette méthode permet d'enregistrer ou de modifier un client."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "L'objet client a été créé / modifié"),
        @ApiResponse(responseCode = "400", description = "L'objet client n'est pas valide")
    })
    ClientDto save(@RequestBody ClientDto dto);

    @GetMapping(value = APP_ROOT + "/clients/{idClient}",
                produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Rechercher un client par ID",
        description = "Cette méthode permet de chercher un client par son ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Le client a été trouvé dans la BDD"),
        @ApiResponse(responseCode = "404", description = "Aucun client n'existe dans la BDD avec l'ID fourni")
    })
    ClientDto findById(@PathVariable("idClient") Integer id);

    @GetMapping(value = APP_ROOT + "/clients/all",
                produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Renvoi la liste des clients",
        description = "Cette méthode permet de chercher et renvoyer la liste des clients présents dans la BDD."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La liste des clients / une liste vide")
    })
    List<ClientDto> findAll();

    @DeleteMapping(value = APP_ROOT + "/clients/delete/{idClient}")
    @Operation(
        summary = "Supprimer un client",
        description = "Cette méthode permet de supprimer un client par son ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Le client a été supprimé avec succès"),
        @ApiResponse(responseCode = "400", description = "Le client ne peut pas être supprimé car il est lié à d'autres entités")
    })
    void delete(@PathVariable("idClient") Integer id);
}