package com.application.gestiondestock.controller;

import com.application.gestiondestock.controller.api.ProduitApi;
import com.application.gestiondestock.dto.LigneCommandeClientDto;
import com.application.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.application.gestiondestock.dto.LigneVenteDto;
import com.application.gestiondestock.dto.ProduitDto;
import com.application.gestiondestock.services.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProduitController implements ProduitApi {
    private ProduitService produitService;

    @Autowired
    public ProduitController(
            ProduitService produitService
    ) {
        this.produitService = produitService;
    }

    @Override
    public ProduitDto save(ProduitDto dto) {
        return produitService.save(dto);
    }

    @Override
    public ProduitDto findById(Integer id) {
        return produitService.findById(id);
    }

    @Override
    public ProduitDto findByCodeProduit(String codeProduit) {
        return produitService.findByCodeProduit(codeProduit);
    }

    @Override
    public List<ProduitDto> findAll() {
        return produitService.findAll();
    }

    @Override
    public List<LigneVenteDto> findHistoriqueVentes(Integer idProduit) {
        return produitService.findHistoriqueVentes(idProduit);
    }

    @Override
    public List<LigneCommandeClientDto> findHistoriqueCommandeClient(Integer idProduit) {
        return produitService.findHistoriqueCommandeClient(idProduit);
    }

    @Override
    public List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(Integer idProduit) {
        return produitService.findHistoriqueCommandeFournisseur(idProduit);
    }

    @Override
    public List<ProduitDto> findAllProduitByIdCategory(Integer idCategory) {
        return produitService.findAllProduitByIdCategory(idCategory);
    }

    @Override
    public void delete(Integer id) {
        produitService.deleteById(id);
    }
}
