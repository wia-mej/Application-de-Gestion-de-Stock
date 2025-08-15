package com.application.gestiondestock.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lignecommandeclient")



public class LigneCommandeClient extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "idproduit")
    private Produit produit;

    @ManyToOne
    @JoinColumn(name = "idcommandeclient")
    private CommandeClient commandeClient;


    @Column(name = "quantite")
    private BigDecimal quantite;

    @Column(name = "prixunitaire")
    private Double prixUnitaire;

    @Column(name = "identreprise")
    private Integer idEntreprise;
}
