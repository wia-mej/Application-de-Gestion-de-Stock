package com.application.gestiondestock.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "produit")


public class Produit extends AbstractEntity{

    @Column(name = "codeProduit")
    private String codeProduit;

    @Column(name = "designation")
    private String designation;

    @Column(name = "prixUnitaire")
    private BigDecimal prixUnitaire;

    @Column(name = "tauxTva")
    private BigDecimal tauxTva;

    @Column(name = "prixTtc")
    private BigDecimal prixTTC;

    @Column(name = "photo")
    private String photo;

    @ManyToOne
    @JoinColumn(name = "idCategory")
    private Category category;

    @Column(name = "idEntreprise")
    private Integer idEntreprise;

    @OneToMany(mappedBy = "produit")
    private List<LigneVente> ligneVentes;

    @OneToMany(mappedBy = "produit")
    private List<LigneCommandeClient> ligneCommandeClients;

    @OneToMany(mappedBy = "produit")
    private List<LigneCommandeFournisseur> ligneCommandeFournisseurs;

    @OneToMany(mappedBy = "produit")
    private List<MvmStock> mvmStocks;
}
