package com.application.gestiondestock.services;

import com.application.gestiondestock.dto.LigneCommandeClientDto;
import com.application.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.application.gestiondestock.dto.LigneVenteDto;
import com.application.gestiondestock.dto.ProduitDto;

import java.util.List;

public interface ProduitService {

    ProduitDto save(ProduitDto dto);

    ProduitDto findById(Integer id);

    ProduitDto findByCodeProduit(String codeProduit);

    List<ProduitDto> findAll();

    List<LigneVenteDto> findHistoriqueVentes(Integer idProduit);

    List<LigneCommandeClientDto> findHistoriqueCommandeClient(Integer idProduit);

    List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(Integer idProduit);

    List<ProduitDto> findAllProduitByIdCategory(Integer idCategory);

    void deleteById(Integer id);

}
