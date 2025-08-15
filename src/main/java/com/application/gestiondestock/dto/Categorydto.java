package com.application.gestiondestock.dto;

import com.application.gestiondestock.model.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;


import java.util.List;
@Data
@Builder
public class Categorydto {

    private Integer id;

    private String code;

    private String designation;

    private Integer idEntreprise;


    @JsonIgnore
    private List<ProduitDto> produits;

    public static Categorydto fromEntity(Category category){
        if (category == null){
            return null;
            // TODO throw en exception

        }
        // Category <- CategoryDto
        return Categorydto.builder()
                .id(category.getId())
                .code(category.getCode())
                .designation(category.getDesignation())
                .idEntreprise(category.getIdEntreprise())
                .build();
    }

    public static Category toEntity(Categorydto categorydto){
        if(categorydto == null){
            return null;
        }

        Category category = new Category();
        category.setId(categorydto.getId());
        category.setCode(categorydto.getCode());
        category.setDesignation(categorydto.getDesignation());
        category.setIdEntreprise(categorydto.getIdEntreprise());

        return category;
    }

}
