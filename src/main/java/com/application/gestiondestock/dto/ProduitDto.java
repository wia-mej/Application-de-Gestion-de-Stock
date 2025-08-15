package com.application.gestiondestock.dto;

import com.application.gestiondestock.model.Produit;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data

public class ProduitDto {

    private Integer id;

    private String codeProduit;

    private String designation;

    private BigDecimal prixUnitaire;

    private BigDecimal tauxTva;

    private BigDecimal prixTTC;

    private String photo;

    private Categorydto category;

    private Integer idEntreprise;

    public static ProduitDto fromEntity(Produit produit) {
        if (produit == null) {
            return null;
        }
        return ProduitDto.builder()
                .id(produit.getId())
                .codeProduit(produit.getCodeProduit())
                .designation(produit.getDesignation())
                .photo(produit.getPhoto())
                .prixUnitaire(produit.getPrixUnitaire())
                .prixTTC(produit.getPrixTTC())
                .tauxTva(produit.getTauxTva())
                .idEntreprise(produit.getIdEntreprise())
                .category(Categorydto.fromEntity(produit.getCategory()))
                .build();
    }

    public static Produit toEntity(ProduitDto produitDto) {
        if (produitDto == null) {
            return null;
        }
        Produit produit = new Produit();
        produit.setId(produitDto.getId());
        produit.setCodeProduit(produitDto.getCodeProduit());
        produit.setDesignation(produitDto.getDesignation());
        produit.setPhoto(produitDto.getPhoto());
        produit.setPrixUnitaire(produitDto.getPrixUnitaire());
        produit.setPrixTTC(produitDto.getPrixTTC());
        produit.setTauxTva(produitDto.getTauxTva());
        produit.setIdEntreprise(produitDto.getIdEntreprise());
        produit.setCategory(Categorydto.toEntity(produitDto.getCategory()));
        return produit;



    }

}
