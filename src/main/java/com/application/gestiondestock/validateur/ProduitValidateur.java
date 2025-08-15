package com.application.gestiondestock.validateur;

import com.application.gestiondestock.dto.ProduitDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ProduitValidateur {

    public static List<String> validate(ProduitDto dto) {
        List<String> errors = new ArrayList<>();

        if (dto == null) {
            errors.add("Veuillez renseigner le code de l'article");
            errors.add("Veuillez renseigner la designation de l'article");
            errors.add("Veuillez renseigner le prix unitaire HT l'article");
            errors.add("Veuillez renseigner le taux TVA de l'article");
            errors.add("Veuillez renseigner le prix unitaire TTC de l'article");
            errors.add("Veuillez selectionner une categorie");
            return errors;
        }

        if (!StringUtils.hasLength(dto.getCodeProduit())) {
            errors.add("Veuillez renseigner le code de l'article");
        }
        if (!StringUtils.hasLength(dto.getDesignation())) {
            errors.add("Veuillez renseigner la designation de l'article");
        }
        if (dto.getPrixUnitaire() == null) {
            errors.add("Veuillez renseigner le prix unitaire HT l'article");
        }
        if (dto.getTauxTva() == null) {
            errors.add("Veuillez renseigner le taux TVA de l'article");
        }
        if (dto.getPrixTTC() == null) {
            errors.add("Veuillez renseigner le prix unitaire TTC de l'article");
        }
        if (dto.getCategory() == null || dto.getCategory().getId() == null) {
            errors.add("Veuillez selectionner une categorie");
        }
        return errors;
    }
}
